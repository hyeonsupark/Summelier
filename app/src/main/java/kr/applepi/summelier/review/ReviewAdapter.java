package kr.applepi.summelier.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import kr.applepi.summelier.R;
import kr.applepi.summelier.util.BitmapLruCache;
import kr.applepi.summelier.util.RoundedNetworkImageView;

public class ReviewAdapter extends ArrayAdapter<ReviewData> {
    private RequestQueue queue;
    private ImageLoader loader;

    private TextView tvName, tvText, tvTimestamp;
    private RoundedNetworkImageView ivProfile;
    private RatingBar rating;

    private LayoutInflater inflater;

    private int resource;

    public ReviewAdapter(Context context) {
        super(context, R.layout.row_review, new ArrayList<ReviewData>());
        queue = Volley.newRequestQueue(context);
        loader = new ImageLoader(queue, new BitmapLruCache());

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.resource = R.layout.row_review;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ReviewData data = getItem(position);

        if (convertView == null) {

            convertView = inflater.inflate(resource, parent, false);
        }

        tvName = (TextView) convertView.findViewById(R.id.REVIEW_PROFILE_NAME);
        tvText = (TextView) convertView.findViewById(R.id.REVIEW_PROFILE_TEXT);
        tvTimestamp = (TextView) convertView.findViewById(R.id.REVIEW_PROFILE_TIME);
        ivProfile = (RoundedNetworkImageView) convertView.findViewById(R.id.review_iv_profile);
        rating = (RatingBar) convertView.findViewById(R.id.REVIEW_PROFILE_RATING);


        ivProfile.setImageUrl(data.profileUrl, loader);


        tvName.setText(data.name);
        tvText.setText(data.text);
        rating.setRating(data.rating);
	    tvTimestamp.setText(data.timestamp);
        rating.setIsIndicator(true);

        return convertView;
    }
}
