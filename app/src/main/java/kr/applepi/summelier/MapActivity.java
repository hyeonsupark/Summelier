package kr.applepi.summelier;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapActivity extends FragmentActivity implements
        GoogleMap.OnMyLocationChangeListener, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnInfoWindowClickListener, View.OnClickListener {

    private GoogleMap map;

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

        LatLng latLng = new LatLng(37, 128);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));

        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationChangeListener(this);
        map.setOnInfoWindowClickListener(this);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationChange(Location location) {

    }
}
