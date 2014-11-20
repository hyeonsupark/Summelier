package kr.applepi.summelier;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import kr.applepi.summelier.R;
import kr.applepi.summelier.util.ActivityPlus;

public class RegionActivity extends ActivityPlus {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);

	    initUi();
    }

	private void initUi()
	{
		String []locations = MapActivity.LOCATIONS;
		int ids[] = {
				R.id.REGION_1,
				R.id.REGION_2,
				R.id.REGION_3,
				R.id.REGION_4,
				R.id.REGION_5,

				R.id.REGION_6,
				R.id.REGION_7,
				R.id.REGION_8,
				R.id.REGION_9,
				R.id.REGION_10,

				R.id.REGION_11,
				R.id.REGION_12,
				R.id.REGION_13,
				R.id.REGION_14,
				R.id.REGION_15,

				R.id.REGION_16,
				R.id.REGION_17,
		};

		for(int i = 1; i <= locations.length; ++i)
		{
			text_(ids[i - 1], locations[i - 1]);
			final int where = i;
			onClick(ids[i - 1], new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					startMap(where);
				}
			});
		}
	}

	private void startMap(int where)
	{
		Intent it = new Intent(this, MapActivity.class);
		it.putExtra("where", new int[]{where});
		startActivity(it);
	}


	private int getId(String name, String type)
	{
		return Resources.getSystem().getIdentifier(name, type,  "kr.applepi.summelier");
	}

}
