package kr.applepi.summelier.auth;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import org.json.JSONObject;

import kr.applepi.summelier.util.ActivityPlus;
import kr.applepi.summelier.R;
import kr.applepi.summelier.api.Api;
import kr.applepi.summelier.api.ResultListener;

// 사인업하면 액티비티 종료하고 로그인 화면보내삼

public class SignUpActivity extends ActivityPlus {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);


		onClick(R.id.sign_up, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trySignUp();
            }
        });
    }


	private void trySignUp(){
		String id = edit_(R.id.sign_up_et_id).toString(),
				name = edit_(R.id.sign_up_et_name).toString(),
				pw1 = edit_(R.id.sign_up_et_password1).toString(),
				pw2 = edit_(R.id.sign_up_et_password2).toString();

		if(!pw1.equals(pw2))
		{
			toast("두 비밀번호가 맞지 않아요.", Toast.LENGTH_LONG);
			return;
		}

		if(!validateText(pw1)) {
			toast("이상한 비밀번호에요", Toast.LENGTH_LONG);
			return;
		}

		if(!validateText(id)) {
			toast("이상한 아이디에요", Toast.LENGTH_LONG);
			return;
		}

		Api api = Api.get(this);
		api.registerBySummelier(id, pw1, name, new ResultListener()
		{
			@Override
			public void onResult(boolean ok, JSONObject res) throws Exception
			{
				Log.d("로그인 결과", res.toString());

				if (ok)
				{
					alert("",
						"회원가입이 완료되었습니다. 로그인해 주세요.",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								finish();
							}
						});

				} else {
					toast("로그인이 안되네요. " + res.getString("message"), Toast.LENGTH_LONG);
				}
			}
		});
	}

	private boolean validateText(String text)
	{
		return !text.contains(" ") && text.length() >= 4;
	}


}
