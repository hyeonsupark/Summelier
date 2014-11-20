package kr.applepi.summelier.rank;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import kr.applepi.summelier.R;
import kr.applepi.summelier.api.Well;
import kr.applepi.summelier.flower.Flower;
import kr.applepi.summelier.review.ReviewActivity;

/**
 * Created by KimHeeKue on 2014-11-20.
 */
public class RankAdapter extends ArrayAdapter<Well> {

	LayoutInflater inflater;
	int layoutId;

	public RankAdapter(Context context)
	{
		super(context, R.layout.row_rank, new ArrayList<Well>());
		layoutId = R.layout.row_rank;

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final Well data = getItem(position);

		if (convertView == null) {
			convertView = inflater.inflate(layoutId, parent, false);
		}

		TextView tvName = (TextView)convertView.findViewById(R.id.ROW_RANK_NAME),
				tvStatus = (TextView)convertView.findViewById(R.id.ROW_RANK_STATUS),
				tvAddress = (TextView)convertView.findViewById(R.id.ROW_RANK_ADDRESS),
				tvReviews = (TextView)convertView.findViewById(R.id.ROW_RANK_REVIEWS),
				tvAverage = (TextView)convertView.findViewById(R.id.ROW_RANK_AVERAGE),
				tvRankNumber = (TextView)convertView.findViewById(R.id.ROW_RANK_NUMBER);
		RatingBar ratingBar = (RatingBar)convertView.findViewById(R.id.ROW_RANK_RATING);

		tvRankNumber.setText((position + 1) + "위");
		tvName.setText(data.name);
		tvStatus.setText(data.status);
		tvAddress.setText(data.address);
		tvReviews.setText("평가 "+data.numReviews + "개");
		tvAverage.setText(String.format("%.1f", data.rating));
		ratingBar.setRating(data.rating);

		return convertView;
	}


}
