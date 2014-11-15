package kr.applepi.summelier.community;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import kr.applepi.summelier.R;
import kr.applepi.summelier.util.ActivityPlus;

public class WriteActivity extends ActivityPlus{

    private EditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_write);
        initUi();
    }

    private void initUi() {

        etText = (EditText) findViewById(R.id.write_et_text);

        onClick(R.id.write_btn_write, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 글 쓰기 부분
                String text = etText.getText().toString();
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
