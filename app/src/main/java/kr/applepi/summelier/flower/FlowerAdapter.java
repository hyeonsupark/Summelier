package kr.applepi.summelier.flower;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import kr.applepi.summelier.R;
import kr.applepi.summelier.review.ReviewData;
import kr.applepi.summelier.util.BitmapLruCache;
import kr.applepi.summelier.util.RoundedNetworkImageView;

/**
 * Created by KimHeeKue on 2014-11-19.
 */
public class FlowerAdapter extends ArrayAdapter<Flower> {

	LayoutInflater inflater;
	int resource;


	public FlowerAdapter(Context context) {
		super(context, R.layout.row_flower, new ArrayList<Flower>());
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		this.resource = R.layout.row_flower;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Flower data = getItem(position);

		if (convertView == null) {

			convertView = inflater.inflate(resource, parent, false);
		}

		TextView tvName = (TextView) convertView.findViewById(R.id.FLOWER_NAME);
		TextView tvGainedDate = (TextView) convertView.findViewById(R.id.FLOWER_DATE_GAINED);
		ImageView ivIcon = (ImageView) convertView.findViewById(R.id.FLOWER_IMAGE);


		tvName.setText(data.name);
		tvGainedDate.setText(data.gainedDate);
		ivIcon.setImageResource(getResourceIdFromFlowerType(data.type));

		return convertView;
	}

	private int getResourceIdFromFlowerType(int flowerType)
	{
		switch(flowerType)
		{
			case 1:
				return R.drawable.flower1;
			case 2:
				return R.drawable.flower2;
			case 3:
				return R.drawable.flower3;
			case 4:
				return R.drawable.flower4;
			case 5:
				return R.drawable.flower5;
			case 6:
				return R.drawable.flower6;
			default:
				return 0;
		}
	}
}
