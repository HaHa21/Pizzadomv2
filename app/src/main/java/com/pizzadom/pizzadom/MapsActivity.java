package com.pizzadom.pizzadom;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ZoomControls;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by User on 23/5/2017.
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap GMap;
    ZoomControls zoom;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapsactivity);
        zoom = (ZoomControls) findViewById(R.id.scZoom);
        zoom.setOnZoomInClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                GMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });
        zoom.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        GMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng WangsaWalkMall = new LatLng(3.198, 101.741);
        LatLng KLCC = new LatLng(3.117, 101.677);
        GMap.addMarker(new
                MarkerOptions().position(WangsaWalkMall).title("WangsaWalkMall"));
        GMap.addMarker(new MarkerOptions().position(KLCC).title("KLCC"));

        GMap.moveCamera(CameraUpdateFactory.newLatLng(WangsaWalkMall));
        GMap.moveCamera(CameraUpdateFactory.newLatLng(KLCC));

    }
}
