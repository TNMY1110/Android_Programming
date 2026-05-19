package com.cookandroid.robotinspector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RobotDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_detail);
        setTitle("로봇 상세");

        TextView tvName = findViewById(R.id.tvRobotName);
        TextView tvStatus = findViewById(R.id.tvRobotStatus);
        TextView tvBattery = findViewById(R.id.tvRobotBattery);
        EditText etMemo = findViewById(R.id.etMemo);
        Button btnSave = findViewById(R.id.btnSave);

        // TODO ③: getIntent().getStringExtra(...) / getIntExtra(...) 로 데이터 받아서 TextView 에 표시
        //   String name = getIntent().getStringExtra("robot_name");
        //   String status = getIntent().getStringExtra("robot_status");
        //   int battery = getIntent().getIntExtra("robot_battery", 0);
        //   int robotId = getIntent().getIntExtra("robot_id", -1);
        //   tvName.setText("이름: " + name); ...

        // TODO ④: btnSave.setOnClickListener — 메모 비어있지 않으면
        //   1) RobotDBHelper.insertInspection(robotId, memo);
        //   2) setResult(RESULT_OK, outIntent.putExtra("saved_robot_name", name));
        //   3) finish();
        //   비어있으면 Toast "메모를 입력하세요"
    }
}
