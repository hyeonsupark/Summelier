package kr.applepi.summelier.review;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kr.applepi.summelier.util.ActivityPlus;
import kr.applepi.summelier.R;

public class ReviewActivity extends ActivityPlus {

    private String placeTitle;

    private TextView tvTitle;

    private Button btnSend;

    private EditText etReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_review);
    }

    private void initUi() {
        Intent intent = getIntent();
        placeTitle = intent.getStringExtra("placeTitle");

        tvTitle = (TextView) findViewById(R.id.REVIEW_TITLE);
        tvTitle.setText(placeTitle);

        etReview = (EditText) findViewById(R.id.ET_REVIEW);

        onClick(R.id.BTN_REVIEW_CONFIRM, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
