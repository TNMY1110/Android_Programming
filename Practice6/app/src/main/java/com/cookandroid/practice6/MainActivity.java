package com.cookandroid.practice6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    DatePicker dp;
    EditText edtDiary;
    Button btnWrite;
    String currentDate;
    SQLiteDatabase sqlDB;
    myDBHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 일기장");

        dp = (DatePicker) findViewById(R.id.datePicker1);
        edtDiary = (EditText) findViewById(R.id.edtDiary);
        btnWrite = (Button) findViewById(R.id.btnWrite);

        myHelper = new myDBHelper(this);

        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

        currentDate = cYear + "_" + (cMonth + 1) + "_" + cDay;
        readDiary(currentDate);
        btnWrite.setEnabled(true);

        dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                currentDate = year + "_" + (monthOfYear + 1) + "_" + dayOfMonth;
                readDiary(currentDate);
                btnWrite.setEnabled(true);
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edtDiary.getText().toString();
                sqlDB = myHelper.getWritableDatabase();

                Cursor cursor = sqlDB.rawQuery(
                        "SELECT * FROM myDiary WHERE diaryDate = '" + currentDate + "';", null);

                if (cursor.moveToFirst()) {
                    sqlDB.execSQL("UPDATE myDiary SET content = '" + content
                            + "' WHERE diaryDate = '" + currentDate + "';");
                    Toast.makeText(getApplicationContext(), currentDate + " 수정됨", Toast.LENGTH_SHORT).show();
                } else {
                    sqlDB.execSQL("INSERT INTO myDiary VALUES ('" + currentDate
                            + "', '" + content + "');");
                    Toast.makeText(getApplicationContext(), currentDate + " 저장됨", Toast.LENGTH_SHORT).show();
                }

                cursor.close();
                sqlDB.close();
                btnWrite.setText("수정하기");
            }
        });
    }

    void readDiary(String date) {
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery(
                "SELECT content FROM myDiary WHERE diaryDate = '" + date + "';", null);

        if (cursor.moveToFirst()) {
            edtDiary.setText(cursor.getString(0));
            edtDiary.setHint("");
            btnWrite.setText("수정하기");
        } else {
            edtDiary.setText("");
            edtDiary.setHint("일기 없음");
            btnWrite.setText("새로 저장");
        }

        cursor.close();
        sqlDB.close();
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE myDiary (" +
                    "diaryDate CHAR(10) PRIMARY KEY, " +
                    "content VARCHAR(500));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS myDiary;");
            onCreate(db);
        }
    }
}