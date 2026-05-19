package com.cookandroid.robotinspector;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.cookandroid.robotinspector.db.RobotDBHelper;
import com.cookandroid.robotinspector.model.Robot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // TODO ②: detailLauncher 를 멤버 필드로 선언 (Project10_3 스타일)
    //   registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
    //       result -> {
    //           if (result.getResultCode() == RESULT_OK && result.getData() != null) {
    //               String savedName = result.getData().getStringExtra("saved_robot_name");
    //               // Toast 로 "{savedName} 저장 완료" 표시
    //           }
    //       });
    private ActivityResultLauncher<Intent> detailLauncher;

    private BroadcastReceiver batteryReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("로봇 점검 일지");

        // TODO ⑦: Android 13(API 33) 이상이라면 POST_NOTIFICATIONS 권한 요청
        //   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
        //       && checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
        //          != PackageManager.PERMISSION_GRANTED) {
        //       requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
        //   }

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
        //   intent.putExtra("robot_id" / "robot_name" / "robot_status" / "robot_battery", ...)

        // TODO ⑤: btnShowMap.setOnClickListener — startActivity(new Intent(this, MapActivity.class))

        // TODO ⑦: btnAlertTest.setOnClickListener — AlertService 시작 + extras("title", "message")
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
