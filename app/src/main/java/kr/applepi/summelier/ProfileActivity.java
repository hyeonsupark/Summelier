package kr.applepi.summelier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import kr.applepi.summelier.api.Api;
import kr.applepi.summelier.api.ResultListener;
import kr.applepi.summelier.auth.SplashActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_profile);

        initUi();
    }

    private void initUi() {
        queue = Volley.newRequestQueue(ProfileActivity.this);
        imageCache = new BitmapLruCache();
        imageLoader = new ImageLoader(queue, imageCache);

        ivProfile = (RoundedNetworkImageView) findViewById(R.id.IV_PROFILE_PHOTO);
        tvName = (TextView) findViewById(R.id.TV_NAME);

        final Api api = Api.get(this);

        ivProfile.setImageUrl(api.profileUrl, imageLoader);
        tvName.setText(api.name);


        onClick(R.id.profile_logout, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.logout(new ResultListener() {
                    @Override
                    public void onResult(boolean ok, JSONObject res) throws Exception {
                        startActivity(new Intent(ProfileActivity.this, SplashActivity.class));
                        finish();
                        toast("로그아웃 되었습니다.", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }


}
