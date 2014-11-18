package kr.applepi.summelier.community;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import kr.applepi.summelier.R;
import kr.applepi.summelier.review.ReviewAdapter;
import kr.applepi.summelier.review.ReviewData;
import kr.applepi.summelier.util.BitmapLruCache;
import kr.applepi.summelier.util.RoundedNetworkImageView;

public class BoardActivity extends Activity {

    private RequestQueue queue;
    private BitmapLruCache imageCache;
    private ImageLoader imageLoader;

    private RoundedNetworkImageView ivProfile;

    private Button btnComment;

    private TextView tvName, tvTimestamp, tvText;

    private EditText etComment;

    private ListView lvComments;
    private ArrayList<ReviewData> commentLists;
    private ReviewAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_board);

        initUi();
    }

    private void initUi() {
        Intent intent = getIntent();

        String profile = intent.getStringExtra("profile");
        String name = intent.getStringExtra("name");
        String timestamp = intent.getStringExtra("timestamp");
        String text = intent.getStringExtra("text");

        Log.i("text", "text : " + text);

        queue = Volley.newRequestQueue(BoardActivity.this);
        imageCache = new BitmapLruCache();
        imageLoader = new ImageLoader(queue, imageCache);

        ivProfile = (RoundedNetworkImageView) findViewById(R.id.board_iv_profile);

        btnComment = (Button) findViewById(R.id.board_btn_comment);

        tvName = (TextView) findViewById(R.id.board_tv_name);
        tvTimestamp = (TextView) findViewById(R.id.board_tv_timestamp);
        tvText = (TextView) findViewById(R.id.board_tv_text);

        ivProfile.setImageUrl(profile, imageLoader);
        tvName.setText(name);
        tvTimestamp.setText(timestamp);
        tvText.setText(text);

        etComment = (EditText) findViewById(R.id.board_et_comment);

        lvComments = (ListView) findViewById(R.id.board_lv_comments);
        commentLists = new ArrayList<ReviewData>();
        commentAdapter = new ReviewAdapter(BoardActivity.this);
        lvComments.setAdapter(commentAdapter);

	    /*
        commentLists.add(new ReviewData("http://applepi.kr/~summelier/images/empty.png", "박현수", "ㅁㄴㅇㄹ", "1분 전"));
        commentLists.add(new ReviewData("http://applepi.kr/~summelier/images/empty.png", "김희규", "규미가 너무 좋앙", "0분 전"));
        commentAdapter.notifyDataSetChanged();/
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etComment.getText().toString();
                commentAdapter.add(new ReviewData("http://applepi.kr/~summelier/images/empty.png", "김희규", text, "하앜"));
            }
        });
		*/


    }
}
