package kr.applepi.summelier.community;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import kr.applepi.summelier.R;
import kr.applepi.summelier.api.Api;
import kr.applepi.summelier.api.ResultListener;
import kr.applepi.summelier.util.ActivityPlus;
import kr.applepi.summelier.util.BitmapLruCache;

public class WriteActivity extends ActivityPlus{

    private EditText etText;
	private Gallery gallery;
	GalleryAdapter adapter;

	Api api;

	RequestQueue queue;
	ImageLoader imageLoader;
	BitmapLruCache imageCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_write);

	    api = Api.get(this);
        initUi();
    }

	private void uploadPhotos(
			final int postId,
			final int index,
			final ProgressDialog dialog)
	{
		if(adapter.getCount() <= index)
		{
			dialog.dismiss();
			finish();
			return;
		}

		api.uploadPhotoImage(
				new File(adapter.getItem(index)),
				postId,
				new ResultListener() {
					@Override
					public void onResult(boolean ok, JSONObject res) throws Exception {
						if(ok)
						{
							Log.d("사진 업로드 성공", "헤헤");
							uploadPhotos(postId, index + 1, dialog);
						}
						else
						{
							toast("사진 업로드 중에 에러가 났습니다.", Toast.LENGTH_LONG);
							Log.d("사진업로드안댐",
									String.format (
											"POST_ID: %d(%d번째), %s",
											postId,
											index,
											res.toString()
									)
									);
						}
					}
				}
		);
	}

	private void queryRemovePhoto(final int index)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setMessage("사진을 제거하시겠습니까?");
		alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				adapter.remove(adapter.getItem(index));
				adapter.notifyDataSetChanged();
				Log.d("사진제거댐", index + " 번째꺼");
				dialogInterface.dismiss();
			}
		});
		alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.dismiss();
			}
		});
		alert.show();
	}


    private void initUi() {

        etText = (EditText) findViewById(R.id.write_et_text);


	    queue = Volley.newRequestQueue(this);
	    imageCache = new BitmapLruCache();
	    imageLoader = new ImageLoader(queue, imageCache);

	    gallery = (Gallery)view_(R.id.WRITE_GALLERY);
	    adapter = new GalleryAdapter(this, R.layout.row_photo);
	    adapter.networkImage = false;
	    gallery.setAdapter(adapter);
	    gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    @Override
		    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
			    queryRemovePhoto(i);
		    }
	    });

        onClick(R.id.write_btn_write, new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
		        // 글 쓰기 부분
		        String text = etText.getText().toString();
		        final ProgressDialog dialog =
				        ProgressDialog.show(
						        WriteActivity.this,
						        "",
						        "게시물을 작성 중입니다...",
						        true
				        );
		        dialog.show();

		        api.writePost(text, new ResultListener() {
			        @Override
			        public void onResult(boolean ok, JSONObject res) throws Exception {
				        if (ok) {
					        if(adapter.getCount() > 0) {
						        dialog.setMessage("사진 업로드 중...");
						        uploadPhotos(res.getInt("post_id"), 0, dialog);
					        }
				        } else {
					        toast("글 작성이 이루어지지 않았습니다.", Toast.LENGTH_LONG);
					        Log.d("글작성안댐", res.toString());
				        }
			        }
		        });
	        }
        });

	    onClick(R.id.write_btn_cancel, new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    onBackPressed();
		    }
	    });

        onClick(R.id.write_btn_photo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사진 업로드 부분
	            selectPhoto();
            }
        });
    }


	private static final int CODE_SELECT_PROFILE = 101011,
			CODE_CROP_PROFILE = 102001;
	private void selectPhoto()
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

		switch (requestCode) {
			case CODE_SELECT_PROFILE: {
				if (resultCode != RESULT_OK) break;

				final Uri uri = data.getData();
				String real = getRealPathFromURI(uri);
				adapter.add(real);
				adapter.notifyDataSetChanged();

				Log.d("사진 추가됨", real);
				break;
			}
		}
	}
}
