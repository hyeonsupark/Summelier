package kr.applepi.summelier.community;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import kr.applepi.summelier.R;
import kr.applepi.summelier.util.BitmapLruCache;
import kr.applepi.summelier.util.RoundedNetworkImageView;

/**
 * Created by Hyunsu on 2014-11-15.
 */
public class BoardAdapter extends ArrayAdapter<BoardData>{

    LayoutInflater inflater;
    int resource;

    RequestQueue queue;
    ImageLoader imageLoader;
    BitmapLruCache imageCache;

	Gallery gallery;
    NetworkImageView ivProfile;
    TextView tvName;
    TextView tvTimestamp;
    TextView tvCommentsCount;
    TextView tvText;


    public BoardAdapter(Context context, int resource, ArrayList<BoardData> objects) {
        super(context, resource, objects);

	    queue = Volley.newRequestQueue(context);
	    imageCache = new BitmapLruCache();
	    imageLoader = new ImageLoader(queue, imageCache);

        this.resource = resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BoardData data = getItem(position);
        if(convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
        }

	    GalleryAdapter adapter = new GalleryAdapter(getContext(), imageLoader);

		gallery = (Gallery)convertView.findViewById(R.id.BOARD_GALLERY);
	    gallery.setAdapter(adapter);
		gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Uri uri = Uri.parse((String)adapterView.getAdapter().getItem(i));
				Intent it = new Intent(Intent.ACTION_VIEW);
				it.setDataAndType(uri, "image/*");
				getContext().startActivity(it);
			}
		});

	    for(String url : data.photos)
	        adapter.add(url);
	    adapter.notifyDataSetChanged();

        ivProfile = (RoundedNetworkImageView) convertView.findViewById(R.id.boards_iv_profile);
        tvName = (TextView) convertView.findViewById(R.id.boards_tv_name);
        tvTimestamp = (TextView) convertView.findViewById(R.id.boards_tv_timestamp);
        tvCommentsCount = (TextView) convertView.findViewById(R.id.boards_tv_comment_count);
        tvText = (TextView) convertView.findViewById(R.id.boards_tv_comment);

        ivProfile.setImageUrl(data.getProfile(), imageLoader);
        tvName.setText(data.getName());
        tvTimestamp.setText(data.getTimestamp());
        tvCommentsCount.setText("댓글 " + data.getCommentsCount() + "개");
        tvText.setText(data.getText());

        return convertView;

    }
}
