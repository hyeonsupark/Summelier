package kr.applepi.summelier;

import android.app.AlertDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapActivity extends FragmentActivity implements
        GoogleMap.OnMyLocationChangeListener, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnInfoWindowClickListener, View.OnClickListener {

    private GoogleMap map;

    private Button btnRank, btnCommunity, btnSetting, btnRate;
    private TextView tvTitle, tvArticle;

    private RatingBar ratingBar;

    private AlertDialog dialog;

    private boolean firstCameraMovingFlag = true;

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
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.map_btn_rank:
                break;
            case R.id.map_btn_community:
                break;
            case R.id.map_btn_setting:
                break;
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        LayoutInflater inflater = LayoutInflater.from(MapActivity.this);
        View v = inflater.inflate(R.layout.dialog_marker, null);

        tvArticle = (TextView) v.findViewById(R.id.TV_ARTICLE);
        tvTitle = (TextView) v.findViewById(R.id.TV_TITLE);

        btnRate = (Button) v.findViewById(R.id.BTN_RATE);

        ratingBar = (RatingBar) v.findViewById(R.id.RATE_AVERAGE);
        ratingBar.setRating(4.3F);
        ratingBar.setIsIndicator(true);

        AlertDialog.Builder alert = new AlertDialog.Builder(
                MapActivity.this);
        tvTitle.setText(marker.getTitle());
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
