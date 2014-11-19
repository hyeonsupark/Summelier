package kr.applepi.summelier;

import android.content.Intent;
import android.os.Bundle;
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
