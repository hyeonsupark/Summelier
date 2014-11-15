package kr.applepi.summelier.community;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import kr.applepi.summelier.R;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_board);

        initUi();
    }

    private void initUi() {
        queue = Volley.newRequestQueue(BoardActivity.this);
        imageCache = new BitmapLruCache();
        imageLoader = new ImageLoader(queue, imageCache);

        ivProfile = (RoundedNetworkImageView) findViewById(R.id.board_iv_profile);

        btnComment = (Button) findViewById(R.id.board_btn_comment);

        tvName = (TextView) findViewById(R.id.board_tv_name);
        tvTimestamp = (TextView) findViewById(R.id.board_tv_timestamp);
        tvText = (TextView) findViewById(R.id.board_tv_text);

        etComment = (EditText) findViewById(R.id.board_et_comment);

        lvComments = (ListView) findViewById(R.id.board_lv_comments);

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etComment.getText().toString();
            }
        });


    }
}
