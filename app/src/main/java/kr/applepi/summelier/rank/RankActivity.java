package kr.applepi.summelier.rank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kr.applepi.summelier.R;
import kr.applepi.summelier.api.Api;
import kr.applepi.summelier.api.ResultListener;
import kr.applepi.summelier.api.Well;
import kr.applepi.summelier.review.ReviewActivity;
import kr.applepi.summelier.util.ActivityPlus;

public class RankActivity extends ActivityPlus {

	ListView listRank;
	RankAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

	    initUI();
	    loadRanking();
    }


	private void initUI()
	{
		listRank = (ListView)view_(R.id.RANK_LIST);
		adapter = new RankAdapter(this);
		listRank.setAdapter(adapter);

		listRank.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent it = new Intent(RankActivity.this, ReviewActivity.class);
				Well data = (Well)adapterView.getAdapter().getItem(i);
				it.putExtra("placeId", data.id);
				startActivity(it);
			}
		});

		onClick(R.id.RANK_BUTTON_BACK, new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	}


	private void loadRanking()
	{
		Api api = Api.get(this);

		api.getWellReviewRanking(new ResultListener() {
			@Override
			public void onResult(boolean ok, JSONObject res) throws Exception {
				if(!ok)
				{
					toast("랭킹이 얻어와지지 않네요.", Toast.LENGTH_LONG);
					Log.d("랭킹안댐", res.toString());
					return;
				}

				JSONArray arr = res.getJSONArray("rank");
				for(int i = 0; i < arr.length(); ++i)
				{
					addWellReviewRank(arr.getJSONObject(i));
				}
				adapter.notifyDataSetChanged();
			}
		});
	}

	private void addWellReviewRank(JSONObject rank) throws JSONException
	{
		Well well = new Well();
		well.id = rank.getInt("id");
		well.rating = (float)rank.getDouble("rating");
		well.numReviews = rank.getInt("ratingCount");
		well.name = rank.getString("name");
		well.status = rank.getString("status");
		well.content = rank.getString("content");
		well.address = rank.getString("address");

		adapter.add(well);
	}
}
