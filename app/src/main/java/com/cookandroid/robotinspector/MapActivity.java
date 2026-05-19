package com.cookandroid.robotinspector;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.cookandroid.robotinspector.db.RobotDBHelper;
import com.cookandroid.robotinspector.model.Robot;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * CookMap의 MapFragment + GroundOverlayOptions + 이전/다음 버튼 패턴을 따릅니다.
 * 차이점: CSV 대신 RobotDBHelper.getAllRobots() 로 데이터를 가져옵니다.
 */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap gMap;
    MapFragment mapFrag;
    GroundOverlayOptions placeMark;
    Button btnPrev, btnNext;
    List<Robot> robotList;
    int robotCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setTitle("로봇 위치 지도");

        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);

        // TODO ⑤: RobotDBHelper 로 로봇 목록 로드 후 robotList 에 저장
        RobotDBHelper dbHelper = new RobotDBHelper(this);
        robotList = dbHelper.getAllRobots();
        android.util.Log.i("로봇지도", "지도용 로봇 수: " + robotList.size());

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (robotList == null || robotList.isEmpty()) return;

                robotCount++;
                if (robotCount > robotList.size() - 1)
                    robotCount = 0;
                Robot robot = robotList.get(robotCount);

                android.util.Log.d("로봇지도", "다음 → " + robot.getName()
                        + " (" + robot.getLatitude() + ", " + robot.getLongitude() + ")");
                showRobotOnMap(robot);
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (robotList == null || robotList.isEmpty()) return;

                robotCount--;
                if (robotCount < 0)
                    robotCount = robotList.size() - 1;
                Robot robot = robotList.get(robotCount);

                android.util.Log.d("로봇지도", "이전 → " + robot.getName());
                showRobotOnMap(robot);
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap map) {
        android.util.Log.i("로봇지도", "onMapReady()");
        gMap = map;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(37.5665, 126.9780), 13));
        gMap.getUiSettings().setZoomControlsEnabled(true);

        if (robotList != null && !robotList.isEmpty()) {
            showRobotOnMap(robotList.get(0));
        }
    }

    private void showRobotOnMap(Robot robot) {
        LatLng point = new LatLng(robot.getLatitude(), robot.getLongitude());
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13));
        placeMark = new GroundOverlayOptions()
                .image(getBitmapDescriptor(R.drawable.robot))
                .position(point, 500f, 500f);
        gMap.addGroundOverlay(placeMark);
        Toast.makeText(getApplicationContext(),
                robot.getName() + " | " + robot.getStatus(),
                Toast.LENGTH_LONG).show();
    }

    private BitmapDescriptor getBitmapDescriptor(int resId) {
        Drawable drawable = ContextCompat.getDrawable(this, resId);
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}