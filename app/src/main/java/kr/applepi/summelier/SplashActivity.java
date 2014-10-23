package kr.applepi.summelier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;


public class SplashActivity extends Activity {

    EditText etId, etPassword;
    Button btnSignUp, btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        initUi();
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
                Intent mIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mIntent);
                finish();
            }
        });


    }


}
