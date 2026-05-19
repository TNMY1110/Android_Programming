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

        String name = getIntent().getStringExtra("robot_name");
        String status = getIntent().getStringExtra("robot_status");
        int battery = getIntent().getIntExtra("robot_battery", 0);
        android.util.Log.i("로봇상세", "수신: name=" + name + " status=" + status + " battery=" + battery);

        TextView tvName = findViewById(R.id.tvRobotName);
        TextView tvStatus = findViewById(R.id.tvRobotStatus);
        TextView tvBattery = findViewById(R.id.tvRobotBattery);
        EditText etMemo = findViewById(R.id.etMemo);
        Button btnSave = findViewById(R.id.btnSave);

        tvName.setText("이름: " + name);
        tvStatus.setText("상태: " + status);
        tvBattery.setText("배터리: " + battery + "%");

        // TODO ④: btnSave.setOnClickListener — 메모 비어있지 않으면
        //   1) RobotDBHelper.insertInspection(robotId, memo);
        //   2) setResult(RESULT_OK, outIntent.putExtra("saved_robot_name", name));
        //   3) finish();
        //   비어있으면 Toast "메모를 입력하세요"
    }
}
