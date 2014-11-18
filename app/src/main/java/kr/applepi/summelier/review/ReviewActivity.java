package kr.applepi.summelier.review;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kr.applepi.summelier.api.Api;
import kr.applepi.summelier.api.ResultListener;
import kr.applepi.summelier.util.ActivityPlus;
import kr.applepi.summelier.R;

public class ReviewActivity extends ActivityPlus {

    private String placeTitle;
    private TextView tvTitle;
    private Button btnSend;
    private EditText etReview;
	private ListView listReview;
	private RatingBar rbRating;

	ReviewAdapter adapter;

	Api api;
	int wellId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_review);

	    initUi();
	    loadReviews();
    }

    private void initUi() {
        Intent intent = getIntent();
        placeTitle = intent.getStringExtra("placeTitle");

        tvTitle = (TextView) findViewById(R.id.REVIEW_TITLE);
        tvTitle.setText(placeTitle);

        etReview = (EditText) findViewById(R.id.ET_REVIEW);
	    listReview = (ListView) findViewById(R.id.LV_REVIEW);
	    rbRating = (RatingBar)view_(R.id.RATING_REVIEW);

		onClick(R.id.BTN_REVIEW_CONFIRM, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
	            addCommentFromEditText();
            }
        });
    }

	private void loadReviews()
	{
		api = Api.get(this);
		adapter = new ReviewAdapter(this);

		listReview.setAdapter(adapter);

		// TODO: 바꿔야댐
		wellId = 2;
		/*
		int wellId = getIntent().getIntExtra("well_id", -1);
		if(wellId == -1)
		{
			toast("확인할 약수터가 전달되지 않았습니다.", Toast.LENGTH_LONG);
			finish();
			return;
		}
		*/

		api.getComments(
				wellId,
				new ResultListener() {
					@Override
					public void onResult(boolean ok, JSONObject res) throws Exception {
						if (ok) {
							JSONArray comments = res.getJSONArray("comments");

							for (int i = 0; i < comments.length(); ++i)
								addCommentFromJSON(comments.getJSONObject(i));

							adapter.notifyDataSetChanged();
						}
					}
				}
		);
	}


	private void addCommentFromJSON(JSONObject comment) throws JSONException
	{
		final ReviewData data = new ReviewData();
		data.id = comment.getInt("id");
		data.rating = (float)comment.getDouble("rate");
		data.timestamp = comment.getString("datetime");
		data.text = comment.getString("content");

		int authorId = comment.getInt("member_id");

		api.getProfileOf(authorId, new ResultListener() {
			@Override
			public void onResult(boolean ok, JSONObject res) throws Exception {
				if(ok)
				{
					data.name = res.getString("name");
					data.profileUrl = res.getString("profile_url");
					Log.d("프로필 수신",
							String.format(
									"이름: %s, 프사 URL: %s",
									data.name,
									data.profileUrl
							)
					);
				}
				else
				{
					data.name = getString(R.string.unknown);
					data.profileUrl = getString(R.string.default_profile_url);
				}

				addComment(data);
			}
		});
	}

	private void addComment(ReviewData data)
	{
		Log.d("댓글",
				String.format(
						"%s(%s, %.1f점): %s",
						data.name,
						data.timestamp,
						data.rating,
						data.text
				));
		adapter.add(data);
	}

	private void addCommentFromEditText()
	{
		final String text = etReview.getText().toString();
		final float rating = rbRating.getRating();

		api.addComment(text, rating, wellId, new ResultListener() {
			@Override
			public void onResult(boolean ok, JSONObject res) throws Exception {
				if(!ok)
				{
					toast("댓글을 작성하지 못했습니다.", Toast.LENGTH_LONG);
				}
				else
				{
					Time time = new Time();
					time.setToNow();

					etReview.setText("");
					addComment(new ReviewData(
							api.profileUrl,
							api.name,
							text,
							time.format("yyyy-MM-dd HH:mm"),
							rating
					));
					adapter.notifyDataSetChanged();
				}
			}
		});
	}
}
