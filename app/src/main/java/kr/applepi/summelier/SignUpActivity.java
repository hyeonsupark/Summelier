package kr.applepi.summelier;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

// 사인업하면 액티비티 종료하고 로그인 화면보내삼

public class SignUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);
    }

}
