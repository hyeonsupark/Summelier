package kr.applepi.summelier.review;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

import kr.applepi.summelier.R;
import kr.applepi.summelier.util.BitmapLruCache;
import kr.applepi.summelier.util.RoundedNetworkImageView;

import static kr.applepi.summelier.R.id.REVIEW_PROFILE_NAME;
import static kr.applepi.summelier.R.id.REVIEW_PROFILE_PHOTO;
import static kr.applepi.summelier.R.id.REVIEW_PROFILE_RATING;
import static kr.applepi.summelier.R.id.REVIEW_PROFILE_TEXT;

public class ReviewAdapter extends ArrayAdapter<ReviewData> {
    RequestQueue queue;
    ImageLoader loader;

    public ReviewAdapter(Context context) {
        super(context, R.layout.row_review);
        queue = Volley.newRequestQueue(context);
        loader = new ImageLoader(queue, new BitmapLruCache());
    }

    class BitmapCache implements ImageCache {

        @Override
        public Bitmap getBitmap(String arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void putBitmap(String arg0, Bitmap arg1) {
            // TODO Auto-generated method stub

        }

    }

    View convertView;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inf.inflate(R.layout.row_review, parent, false);
        }

        ReviewData data = getItem(position);
        this.convertView = convertView;
        RoundedNetworkImageView image = (RoundedNetworkImageView) fv(REVIEW_PROFILE_PHOTO);
        TextView name = (TextView) fv(REVIEW_PROFILE_NAME);
        RatingBar rating = (RatingBar) fv(REVIEW_PROFILE_RATING);
        TextView text = (TextView) fv(REVIEW_PROFILE_TEXT);

        image.setImageUrl(data.profileUrl, loader);
        rating.setRating(data.rating);
        rating.setIsIndicator(true);
        name.setText(data.name);
        text.setText(data.text);

        return convertView;
    }

    private View fv(int id) {
        return convertView.findViewById(id);
    }
}