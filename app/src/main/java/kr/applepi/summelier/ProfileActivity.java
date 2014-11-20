package kr.applepi.summelier;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import kr.applepi.summelier.api.Api;
import kr.applepi.summelier.api.ResultListener;
import kr.applepi.summelier.auth.SplashActivity;
import kr.applepi.summelier.flower.Flower;
import kr.applepi.summelier.flower.FlowerAdapter;
import kr.applepi.summelier.util.ActivityPlus;
import kr.applepi.summelier.util.BitmapLruCache;
import kr.applepi.summelier.util.RoundedNetworkImageView;

public class ProfileActivity extends ActivityPlus {

    private String profileUrl = "http://applepi.kr/~summelier/images/empty.png";

    private RequestQueue queue;
    private ImageLoader imageLoader;
    private BitmapLruCache imageCache;

    private RoundedNetworkImageView ivProfile;
    private TextView tvName;
	private ListView listFlower;

	Api api;
	FlowerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_profile);

        initUi();
	    loadFlowers();
    }

    private void initUi() {
        queue = Volley.newRequestQueue(ProfileActivity.this);
        imageCache = new BitmapLruCache();
        imageLoader = new ImageLoader(queue, imageCache);

        ivProfile = (RoundedNetworkImageView) findViewById(R.id.IV_PROFILE_PHOTO);
        tvName = (TextView) findViewById(R.id.TV_NAME);
		listFlower = (ListView) view_(R.id.PROFILE_LIST_FLOWER);

	    adapter = new FlowerAdapter(this);
	    listFlower.setAdapter(adapter);



        api = Api.get(this);

        ivProfile.setImageUrl(api.profileUrl, imageLoader);
	    ivProfile.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    selectNewProfile();
		    }
	    });
        tvName.setText(api.name);


        onClick(R.id.profile_logout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.logout(new ResultListener() {
                    @Override
                    public void onResult(boolean ok, JSONObject res) throws Exception {
	                    Intent intent = new Intent(ProfileActivity.this, SplashActivity.class);
	                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        toast("로그아웃 되었습니다.", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

	private static final int CODE_SELECT_PROFILE = 101011,
					CODE_CROP_PROFILE = 102001;
	private void selectNewProfile()
	{
		Intent it = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(it, CODE_SELECT_PROFILE);
	}
	private String getRealPathFromURI(Uri contentURI) {
		String result;
		Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
		if (cursor == null) { // Source is Dropbox or other similar local file path
			result = contentURI.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			result = cursor.getString(idx);
			cursor.close();
		}
		return result;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch(requestCode)
		{
			case CODE_SELECT_PROFILE:
			{
				if(resultCode != RESULT_OK) break;

				final Uri uri = data.getData();

				/*
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setClassName("com.android.camera", "com.android.camera.CropImage");

				intent.setData(uri);
				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", "1");
				intent.putExtra("aspectX", "1");
				intent.putExtra("outputX", "96");
				intent.putExtra("outputY", "96");
				intent.putExtra("noFaceDetection", true);
				intent.putExtra("return-data", true);
				startActivityForResult(intent, CODE_CROP_PROFILE);
				*/
				api.uploadProfileImage(
					new File(getRealPathFromURI(uri)),
					new ResultListener() {
					@Override
					public void onResult(boolean ok, JSONObject res) throws Exception {
						if(ok)
						{
							ivProfile.setImageURI(uri);
						}
						else
						{
							toast("프로필 사진 변경이 되지 않았습니다.", Toast.LENGTH_LONG);
							Log.d("프로필변경에러", res.toString());
						}
					}
				});
				break;
			}

			case CODE_CROP_PROFILE:
			{
				if(resultCode != RESULT_OK) break;

				Bundle extras = data.getExtras();
				if(extras != null)
				{
					final Bitmap photo = extras.getParcelable("data");
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					photo.compress(Bitmap.CompressFormat.JPEG, 80, output);

					ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
					api.uploadProfileImage(input, new ResultListener() {
						@Override
						public void onResult(boolean ok, JSONObject res) throws Exception {
							if(ok)
							{
								ivProfile.setImageBitmap(photo);
							}
							else
							{
								toast("프로필 사진 변경이 되지 않았습니다.", Toast.LENGTH_LONG);
								Log.d("프로필변경에러", res.toString());
							}
						}
					});
				}
				break;
			}
		}
	}

	private void loadFlowers()
	{
		api.getMyProfile(new ResultListener() {
			@Override
			public void onResult(boolean ok, JSONObject res) throws Exception {
				if(!ok)
				{
					toast("소지하신 야생화들을 찾지 못했습니다.", Toast.LENGTH_LONG);
					return;
				}
				JSONArray flowerArr = res.getJSONArray("flowers");
				for(int i = 0; i < flowerArr.length(); ++i)
					addFlower(flowerArr.getJSONObject(i));
				adapter.notifyDataSetChanged();


				getInfo(res);
			}
		});
	}

	private void getInfo(JSONObject res) throws JSONException
	{
		int numReview = res.getInt("num_review"),
				numPost = res.getInt("num_post"),
				numPostComment = res.getInt("num_post_comment");

		text_f(R.id.TV_INFORMATION,
				"평가\t%d개\n게시물\t%d개\n댓글\t%d개",
				numReview,
				numPost,
				numPostComment
				);
	}


	private void addFlower(JSONObject obj) throws JSONException
	{
		Flower flower = new Flower();
		flower.type = obj.getInt("type");
		flower.gainedDate = obj.getString("gained_date");
		flower.name = getFlowerNameByType(flower.type);
		adapter.add(flower);
	}

	private String getFlowerNameByType(int type)
	{
		switch(type)
		{
			case 1:
				return "야생화1";
			case 2:
				return "야생화2";
			case 3:
				return "야생화3";
			case 4:
				return "야생화4";
			case 5:
				return "야생화5";
			case 6:
				return "야생화6";
			default:
				return "알 수 없는 꽃";
		}
	}


}
