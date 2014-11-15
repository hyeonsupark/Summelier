package kr.applepi.summelier.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import kr.applepi.summelier.MainActivity;
import kr.applepi.summelier.R;
import kr.applepi.summelier.api.Api;
import kr.applepi.summelier.api.ResultListener;
import kr.applepi.summelier.util.ActivityPlus;



public class SplashActivity extends ActivityPlus {

	Api api;
	EditText etId, etPassword;
    Button btnSignUp, btnSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        initUi();

	    view_(R.id.splash_layout).setVisibility(View.INVISIBLE);
	    api = Api.get(this);
	    api.loginCheck(new ResultListener() {
		    @Override
		    public void onResult(boolean ok, JSONObject res) throws Exception {
                Log.d("시발 로그인체크", res.toString());
			    if(!ok) {
				    view_(R.id.splash_layout).setVisibility(View.VISIBLE);
			    }
			    else {
                    api.name = res.getString("name");
                    api.profileUrl = res.getString("profile_url");
				    gotoMain();
			    }
		    }
	    });
    }

	private void initUi() {
        etId = (EditText) findViewById(R.id.splash_et_id);
        etPassword = (EditText) findViewById(R.id.splash_et_password);
        btnSignUp = (Button) findViewById(R.id.splash_btn_sign_up);
        btnSignIn = (Button) findViewById(R.id.splash_btn_sign_in);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(SplashActivity.this, SignUpActivity.class);
                startActivity(mIntent);

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
	            trySignIn();
            }
        });


    }


	private void trySignIn() {
		Log.d("로그인 시도", "렛츠고");

		api.loginBySummelier(
				etId.getText().toString(),
				etPassword.getText().toString(),
				new ResultListener() {
					@Override
					public void onResult(boolean ok, JSONObject res) throws Exception {
						Log.d("로그인 결과", res.toString());
						if (ok) {
                            api.name = res.getString("name");
                            api.profileUrl = res.getString("profile_url");
							gotoMain();
						} else {
							toast("로그인이 안되네요. " + res.getString("message"), Toast.LENGTH_LONG);
						}
					}
				}
		);
	}


	private void gotoMain() {
		Intent mIntent = new Intent(SplashActivity.this, MainActivity.class);
		startActivity(mIntent);
		finish();
	}
}
