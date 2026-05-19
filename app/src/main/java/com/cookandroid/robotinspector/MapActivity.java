package com.cookandroid.robotinspector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.cookandroid.robotinspector.model.Robot;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * CookMap의 MapFragment + GroundOverlayOptions + 이전/다음 버튼 패턴을 따릅니다.
 * 차이점: CSV 대신 RobotDBHelper.getAllRobots() 로 데이터를 가져옵니다.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap gMap;
    MapFragment mapFrag;
    List<Robot> robotList;
    int robotCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setTitle("로봇 위치 지도");

        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        Button btnPrev = findViewById(R.id.btnPrev);
        Button btnNext = findViewById(R.id.btnNext);

        // TODO ⑤: RobotDBHelper 로 로봇 목록 로드 후 robotList 에 저장
        //   RobotDBHelper dbHelper = new RobotDBHelper(this);
        //   robotList = dbHelper.getAllRobots();

        // TODO ⑤: btnNext / btnPrev 클릭 시 robotList 를 순회하며
        //   1) LatLng point = new LatLng(robot.getLatitude(), robot.getLongitude());
        //   2) gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13));
        //   3) GroundOverlayOptions placeMark = new GroundOverlayOptions()
        //        .image(BitmapDescriptorFactory.fromResource(R.drawable.robot))
        //        .position(point, 500f, 500f);
        //      gMap.addGroundOverlay(placeMark);
        //   4) Toast 로 robot.getName() + status 표시
    }

    @Override
    public void onMapReady(GoogleMap map) {
        android.util.Log.i("로봇지도", "onMapReady()");
        gMap = map;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(37.5665, 126.9780), 13));
        gMap.getUiSettings().setZoomControlsEnabled(true);
    }
}
