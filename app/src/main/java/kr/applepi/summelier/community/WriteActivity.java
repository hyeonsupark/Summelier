package kr.applepi.summelier.community;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import kr.applepi.summelier.R;
import kr.applepi.summelier.api.Api;
import kr.applepi.summelier.api.ResultListener;
import kr.applepi.summelier.util.ActivityPlus;

public class WriteActivity extends ActivityPlus{

    private EditText etText;
	Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_write);

	    api = Api.get(this);
        initUi();
    }

    private void initUi() {

        etText = (EditText) findViewById(R.id.write_et_text);

        onClick(R.id.write_btn_write, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 글 쓰기 부분
                String text = etText.getText().toString();
	            api.writePost(text, new ResultListener() {
		            @Override
		            public void onResult(boolean ok, JSONObject res) throws Exception {
			            if(ok)
			            {
				            finish();
			            }
			            else
			            {
				            toast("글 작성이 이루어지지 않았습니다, 죄송합니다.", Toast.LENGTH_LONG);
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
            }
        });
    }

}
