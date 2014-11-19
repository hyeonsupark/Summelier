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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import kr.applepi.summelier.R;
import kr.applepi.summelier.api.Api;
import kr.applepi.summelier.api.ResultListener;
import kr.applepi.summelier.review.ReviewAdapter;
import kr.applepi.summelier.review.ReviewData;
import kr.applepi.summelier.util.ActivityPlus;
import kr.applepi.summelier.util.BitmapLruCache;
import kr.applepi.summelier.util.RoundedNetworkImageView;

public class BoardActivity extends ActivityPlus {

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
	private Api api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_board);

	    api = Api.get(this);
        initUi();
	    loadComments();
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
        commentAdapter = new ReviewAdapter(BoardActivity.this, false);
        lvComments.setAdapter(commentAdapter);
	    btnComment.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    addCommentFromEditText();
		    }
	    });

    }


	private void loadComments()
	{
		int postId = getIntent().getIntExtra("post_id", 0);
		if(postId == 0)
		{
			toast("게시물의 ID값이 없습니다.", Toast.LENGTH_LONG);
			return;
		}

		api.getPostComments(postId, new ResultListener() {
			@Override
			public void onResult(boolean ok, JSONObject res) throws Exception {
				if(!ok)
				{
					toast("댓글을 전송할 수 없습니다. ", Toast.LENGTH_LONG);
					Log.d("게시물댓글수신안됨", res.toString());
					return;
				}


				JSONArray comments = res.getJSONArray("comments");
				for(int i = 0; i < comments.length(); ++i)
					addCommentFromJSON(comments.getJSONObject(i));

			}
		});
	}

	private void addCommentFromJSON(JSONObject obj)
			throws JSONException
	{
		ReviewData data = new ReviewData();
		data.rating = 0;
		data.id = obj.getInt("id");
		data.authorId = obj.getInt("member_id");
		data.text = obj.getString("content");
		data.timestamp = obj.getString("createdTime");
		data.name = obj.getString("writer_name");
		data.profileUrl = obj.getString("writer_profile_url");

		commentAdapter.add(data);
		commentAdapter.notifyDataSetChanged();
	}



	private void addCommentFromEditText()
	{
		int postId = getIntent().getIntExtra("post_id", 0);
		if(postId == 0)
		{
			toast("댓글을 작성할 수 없습니다.", Toast.LENGTH_LONG);
			return;
		}


		final String content = etComment.getText().toString();
		if(content.length() == 0) return;

		api.addPostComment(postId, content, new ResultListener() {
			@Override
			public void onResult(boolean ok, JSONObject res) throws Exception {

				if(!ok)
				{
					toast("댓글을 전송할 수 없습니다.", Toast.LENGTH_LONG);
					return;
				}

				Date date = new Date();
				DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());

				ReviewData data = new ReviewData();
				data.profileUrl = api.profileUrl;
				data.name = api.name;
				data.text = content;
				data.timestamp =
						android.text.format.DateFormat.getDateFormat(
								getApplicationContext()
						).format(date);
				data.id = res.getInt("id");
				data.rating = 0;

				commentAdapter.add(data);
				commentAdapter.notifyDataSetChanged();
				etComment.setText("");
			}
		});

	}
}
