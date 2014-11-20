package kr.applepi.summelier.community;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.File;
import java.util.ArrayList;

import kr.applepi.summelier.R;

/**
 * Created by KimHeeKue on 2014-11-20.
 */
public class GalleryAdapter extends ArrayAdapter<String> {

	LayoutInflater inflater;
	int resource;
	public boolean networkImage = true;

	ImageLoader loader;


	public GalleryAdapter(Context context, ImageLoader loader)
	{
		super(context, R.layout.row_image, new ArrayList<String>());

		this.resource = R.layout.row_image;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.loader = loader;
	}
	public GalleryAdapter(Context context, int resource)
	{
		super(context,resource, new ArrayList<String>());

		this.resource = resource;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.loader = loader;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String url = getItem(position);
		if(convertView == null) {
			convertView = inflater.inflate(resource, parent, false);
		}


		if(networkImage)
		{
			NetworkImageView imageView =
					(NetworkImageView)convertView.findViewById(R.id.BOARD_GALLERY_IMAGE);
			imageView.setImageUrl(url, loader);
		}
		else
		{
			ImageView imageView =
					(ImageView)convertView.findViewById(R.id.BOARD_GALLERY_IMAGE);
			imageView.setImageURI(Uri.fromFile(new File(url)));
		}
		return convertView;
	}
}
