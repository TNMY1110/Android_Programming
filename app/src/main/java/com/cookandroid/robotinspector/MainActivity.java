package com.cookandroid.robotinspector;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.Manifest;

import com.cookandroid.robotinspector.db.RobotDBHelper;
import com.cookandroid.robotinspector.model.Robot;
import com.cookandroid.robotinspector.service.AlertService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // TODO ②: detailLauncher 를 멤버 필드로 선언 (Project10_3 스타일)
    private ActivityResultLauncher<Intent> detailLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        // 결과 처리 콜백 — 상세 화면에서 메모를 저장하고 돌아왔을 때 실행
                        android.util.Log.i("로봇목록", "detailLauncher 콜백 resultCode=" + result.getResultCode());
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            String savedName = result.getData().getStringExtra("saved_robot_name");
                            android.util.Log.d("로봇목록", "저장 완료된 로봇: " + savedName);
                            Toast.makeText(getApplicationContext(),
                                    savedName + " 점검 기록 저장 완료", Toast.LENGTH_SHORT).show();
                            // 필요 시 목록 새로고침
                            // List<Robot> refreshed = dbHelper.getAllRobots();
                            // adapter.notifyDataSetChanged();
                        } else {
                            android.util.Log.w("로봇목록", "메모 저장 없이 복귀 (취소 또는 실패)");
                        }
                    });
    private BroadcastReceiver batteryReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("로봇 점검 일지");

        // TODO ⑦: Android 13(API 33) 이상이라면 POST_NOTIFICATIONS 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }

        ListView listView = findViewById(R.id.listViewRobots);
        Button btnShowMap = findViewById(R.id.btnShowMap);
        Button btnAlertTest = findViewById(R.id.btnAlertTest);

        // TODO ①: RobotDBHelper 로 로봇 목록 로드 + ArrayAdapter 로 ListView 바인딩
        RobotDBHelper dbHelper = new RobotDBHelper(this);
        List<Robot> robotList = dbHelper.getAllRobots();
        android.util.Log.i("로봇목록", "DB에서 로드한 로봇 수: " + robotList.size());
        // 표시용 문자열 목록 만들기 (예: "로봇-A1 | ONLINE | 배터리 85%")
        List<String> displayList = new ArrayList<>();
        for (Robot r : robotList) {
            displayList.add(r.getName() + " | " + r.getStatus() + " | 배터리 " + r.getBattery() + "%");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(adapter);
        android.util.Log.i("로봇목록", "ListView 어댑터 바인딩 완료");

        // TODO ②: listView.setOnItemClickListener(...) → detailLauncher.launch(intent);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Robot selected = robotList.get(position);
            android.util.Log.i("로봇목록", "항목 클릭 position=" + position + " name=" + selected.getName());
            Intent intent = new Intent(MainActivity.this, RobotDetailActivity.class);
            intent.putExtra("robot_id", selected.getId());
            intent.putExtra("robot_name", selected.getName());
            intent.putExtra("robot_status", selected.getStatus());
            intent.putExtra("robot_battery", selected.getBattery());
            // [현대적 방법 2] startActivity / startActivityForResult 대신 launcher.launch 사용
            detailLauncher.launch(intent);
        });


        // TODO ⑤: btnShowMap.setOnClickListener — startActivity(new Intent(this, MapActivity.class))
        btnShowMap.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MapActivity.class));
        });

        // TODO ⑦: btnAlertTest.setOnClickListener — AlertService 시작 + extras("title", "message")
        btnAlertTest.setOnClickListener(v -> {
            Intent svc = new Intent(this, AlertService.class);
            svc.putExtra("title", "테스트 알림");
            svc.putExtra("message", "AlertService가 정상 동작합니다");
            startService(svc);
            android.util.Log.i("로봇알림", "startService() 호출 (테스트 버튼)");
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO ⑧: BroadcastReceiver 동적 등록 (IntentFilter: Intent.ACTION_BATTERY_LOW)
        //   onReceive 에서:
        //     - RobotDBHelper.getLowBatteryRobots(20) 으로 부족 로봇 목록 조회
        //     - 메시지 조립 후 AlertService 에 Intent extras("title", "message") 전달
        //     - context.startService(svc);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // TODO ⑧: unregisterReceiver(batteryReceiver) — null 체크 후 해제
    }
}
