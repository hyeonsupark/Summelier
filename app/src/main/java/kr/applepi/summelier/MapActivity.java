package kr.applepi.summelier;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import kr.applepi.summelier.api.Api;
import kr.applepi.summelier.api.ResultListener;
import kr.applepi.summelier.community.BoardsActivity;
import kr.applepi.summelier.review.ReviewActivity;

public class MapActivity extends FragmentActivity implements
        GoogleMap.OnMyLocationChangeListener, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnInfoWindowClickListener, View.OnClickListener {

    private GoogleMap map;
	private String locations[] = {
			"서울특별시",
			"인천광역시",
			"대전광역시",
			"대구광역시",
			"울산광역시",
			"부산광역시",
			"광주광역시",
			"세종특별자치시",
			"경기도",
			"강원도",
			"충청북도",
			"충청남도",
			"전라북도",
			"전라남도",
			"경상북도",
			"경상남도",
			"제주특별자치도",

	};

    private Button btnRank, btnCommunity, btnSetting, btnRate;
    private TextView tvTitle, tvArticle;

    private RatingBar ratingBar;

    private AlertDialog dialog;

    private boolean firstCameraMovingFlag = true;

    private String placeTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_map);

        initUi();
    }

    private void initUi() {
        map = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        btnRank = (Button) findViewById(R.id.map_btn_rank);
        btnCommunity = (Button) findViewById(R.id.map_btn_community);
        btnSetting = (Button) findViewById(R.id.map_btn_setting);

        btnRank.setOnClickListener(this);
        btnCommunity.setOnClickListener(this);
        btnSetting.setOnClickListener(this);

        LatLng latLng = new LatLng(37, 128);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));

        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationChangeListener(this);
        map.setOnInfoWindowClickListener(this);

    }

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		loadMarkers();
	}

	void loadMarkers()
	{

		Api api = Api.get(this);
		ResultListener lis = new ResultListener() {
		@Override
		public void onResult(boolean ok, JSONObject res) throws Exception {
			if(ok)
			{
				JSONArray array = res.getJSONArray("wells");
				for(int i = 0; i < array.length(); ++i)
				{
					addWell(array.getJSONObject(i));
				}
			}
			else
			{
				Toast.makeText(
						MapActivity.this,
						"약수터 정보가 읽혀지지 않습니다.",
						Toast.LENGTH_LONG
				).show();
			}
		}};

		Intent it = getIntent();

		int []where = null;
		if(it.hasExtra("where"))
		{
			where = getIntent().getIntArrayExtra("where");

			api.getWells(lis, where);
			Log.d("크하하", "약수터 정보를 내놔라!");
		}
		else
		{
			api.getAllWells(lis);
			Log.d("크하하", "모-든 약수터 정보를 내놔라!");
		}

	}

	void addWell(JSONObject well) throws Exception
	{
		addMarker (
				well.getDouble("latitude"),
				well.getDouble("longitude"),
				locations[well.getInt("location")],
				well.getString("name"),
				well.getString("status")
		);
	}

    void addMarker() {
        addMarker(37.657843, 127.083503, "서울특별시", "천정샘", "2014 1분기 적합");
        addMarker(37.59422,126.9006, "서울특별시", "만수", "2014 1분기 고갈");
        addMarker(37.578708, 126.946781, "서울특별시", "봉화천", "2014 1분기 적합");
        addMarker(37.571858, 126.946718, "서울특별시", "장수천", "2014 1분기 총대장군균");
        addMarker(37.510352, 126.856179, "서울특별시", "다락골", "2014 1분기 총대장군균, 일반세균");
        addMarker(37.580022, 126.815661, "서울특별시", "꿩고개", "2014 1분기 적합");
        addMarker(37.446061, 126.914551, "서울특별시", "옹달샘", "2014 1분기 총대장군균, 일반세균");
        addMarker(37.474841, 127.010986, "서울특별시", "우면정", "2014 1분기 적합");
        addMarker(37.436211, 127.021158, "경기도", "쉬어가는 숲", "2014 1분기 적합");
        addMarker(37.327933, 128.784695, "강원도", "화암약수", "2014 1분기 적합");
        addMarker(37.282368, 126.547783, "경기도", "구봉도약수터", "2014 1분기 총대장균");
        addMarker(35.106426, 126.781404, "전라남도", "평산리정", "2014 1분기 적합");
        addMarker(35.8210123,127.8193784, "경상남도", "산산우물", "2014 1분기 적합");
        addMarker(35.372939, 127.910702, "충청남도", "충추발군 약수터", "2014 1분기 적합");
        addMarker(36.305842, 129.091733, "경상북도", "주왕산 제1 약수터", "2014 1분기 적합");
        addMarker(36.3650257,129.2425644, "경상북도", "봉산 저수지 약수터", "2014 1분기 적합");
        addMarker(35.204107, 128.528356, "경상남도", "무학산 기도원 약수터", "2014 1분기 적합");
        addMarker(36.382708, 127.445788, "대구광역시", "계족산약수터", "2014 1분기 적합");
    }

    boolean firstMoved = false;
    private void addMarker(double lat, double lon, String legion, String title, String snippet)
    {
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(title).snippet(snippet));

        if(!firstMoved) {
            firstMoved = true;
            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lon)));
            map.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.map_btn_rank:
                break;
            case R.id.map_btn_community:
                startActivity(new Intent(MapActivity.this, BoardsActivity.class));

                break;
            case R.id.map_btn_setting:
                break;
            case R.id.BTN_RATE:
                Intent mIntent = new Intent(MapActivity.this, ReviewActivity.class);
                mIntent.putExtra("placeTitle", placeTitle);
                startActivity(mIntent);

                break;

        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        placeTitle = marker.getTitle();
        LayoutInflater inflater = LayoutInflater.from(MapActivity.this);
        View v = inflater.inflate(R.layout.dialog_marker, null);

        tvArticle = (TextView) v.findViewById(R.id.TV_ARTICLE);
        tvTitle = (TextView) v.findViewById(R.id.TV_TITLE);

        btnRate = (Button) v.findViewById(R.id.BTN_RATE);
        btnRate.setOnClickListener(this);

        ratingBar = (RatingBar) v.findViewById(R.id.RATE_AVERAGE);
        ratingBar.setRating(4.3F);
        ratingBar.setIsIndicator(true);

        AlertDialog.Builder alert = new AlertDialog.Builder(
                MapActivity.this);
        tvTitle.setText(placeTitle);
        tvArticle.setText(marker.getSnippet());
        alert.setView(v);

        dialog = alert.create();
        dialog.show();
    }

    @Override
    public boolean onMyLocationButtonClick() {

        return false;
    }

    @Override
    public void onMyLocationChange(Location location) {
        double latitude = location.getLatitude();
        double longtitude = location.getLongitude();

        LatLng latLng = new LatLng(latitude, longtitude);
        if (firstCameraMovingFlag) {
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera(CameraUpdateFactory.zoomTo(13));
            firstCameraMovingFlag = false;
        }
    }
}
