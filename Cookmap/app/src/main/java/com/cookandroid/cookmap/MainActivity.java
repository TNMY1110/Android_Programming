package com.cookandroid.cookmap;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap gMap;
    MapFragment mapFrag;
    GroundOverlayOptions videoMark;
    ArrayList<GroundOverlay> CCTVList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("연습문제 7");

        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        gMap = map;
        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.568256, 126.897240), 13));
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point){
                videoMark = new GroundOverlayOptions().image
                        (BitmapDescriptorFactory.fromResource(R.drawable.presence_video_busy)).
                        position(point, 400f, 400f);

                GroundOverlay overlay = gMap.addGroundOverlay(videoMark);
                CCTVList.add(overlay);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0,1,0,"위성 지도");
        menu.add(0,2,0,"일반 지도");
        menu.add(0,3,0,"바로전 CCTV 지우기");
        menu.add(0,4,0,"모든 CCTV 지우기");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 1:
                gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case 2:
                gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case 3:
                if(!CCTVList.isEmpty()) {
                    GroundOverlay last = CCTVList.get(CCTVList.size()-1);
                    last.remove();
                    CCTVList.remove(CCTVList.size() - 1);
                }
                return true;
            case 4:
                for (GroundOverlay overlay : CCTVList) {
                    overlay.remove();
                }
                CCTVList.clear();
                return true;
        }
        return false;
    }
}