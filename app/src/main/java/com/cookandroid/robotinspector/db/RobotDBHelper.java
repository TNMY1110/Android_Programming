package com.cookandroid.robotinspector.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cookandroid.robotinspector.model.Robot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Project12_2의 SQLiteOpenHelper 패턴을 기반으로 합니다.
 * 단, onCreate 에서 INSERT 를 반복하는 대신 assets/databases/robots.db 를
 * 내부 저장소로 복사하여 사용합니다 (DB Browser for SQLite 로 미리 채워둔 파일).
 */
public class RobotDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "robots.db";
    private static final int DB_VERSION = 1;
    private final Context context;

    public RobotDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        // TODO ①: 아래 메서드를 구현하여 첫 실행 시 assets 에서 DB를 복사하도록 만드세요.
        copyDatabaseFromAssets();
    }

    private void copyDatabaseFromAssets() {
        // TODO ①: assets/databases/robots.db 를 context.getDatabasePath(DB_NAME) 로 복사
        File dbFile = context.getDatabasePath(DB_NAME);
        if (dbFile.exists()) return;

        dbFile.getParentFile().mkdirs();

        try (InputStream is = context.getAssets().open("databases/" + DB_NAME);
             OutputStream os = new FileOutputStream(dbFile)) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = is.read(buf)) > 0) {
                os.write(buf, 0, len);
            }
            android.util.Log.i("로봇DB", "DB 복사 완료");
        }
        catch (IOException e) {
            android.util.Log.e("로봇DB", "DB 복사 실패", e);
        }
        android.util.Log.w("로봇DB", "copyDatabaseFromAssets()");
    }

    public List<Robot> getAllRobots() {
        // TODO: rawQuery("SELECT * FROM robots;") + Cursor.moveToNext() + Robot 객체 매핑
        //   컬럼 순서: 0=id, 1=name, 2=status, 3=battery, 4=latitude, 5=longitude, 6=last_inspected
        List<Robot> list = new ArrayList<>();
        SQLiteDatabase sqlDB = this.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM robots;", null);
        android.util.Log.i("로봇DB", "SELECT * FROM robots — 행 수=" + cursor.getCount());

        while (cursor.moveToNext()) {
            Robot robot = new Robot();
            robot.setId(cursor.getInt(0));
            robot.setName(cursor.getString(1));
            robot.setStatus(cursor.getString(2));
            robot.setBattery(cursor.getInt(3));
            robot.setLatitude(cursor.getDouble(4));
            robot.setLongitude(cursor.getDouble(5));
            list.add(robot);
            android.util.Log.d("로봇DB", "로드: " + robot.getName() + " (" + robot.getStatus() + ")");
        }
        cursor.close();
        sqlDB.close();
        return list;
    }

    public void insertInspection(int robotId, String memo) {
        // TODO: SimpleDateFormat("yyyy-MM-dd HH:mm") 으로 createdAt 생성
        //   execSQL("INSERT INTO inspections VALUES (NULL, " + robotId + ", '...', '...');")
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String createdAt = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date());

        String sql = "INSERT INTO inspections VALUES (NULL, "
                + robotId + ", '"
                + memo + "', '"
                + createdAt + "');";

        android.util.Log.d("로봇DB", "INSERT 실행: " + sql);
        sqlDB.execSQL(sql);
        android.util.Log.i("로봇DB", "점검 메모 저장 완료 (robotId=" + robotId + ")");
        sqlDB.close();
    }

    public List<Robot> getLowBatteryRobots(int threshold) {
        // TODO: rawQuery("SELECT * FROM robots WHERE battery < " + threshold + ";")
        //   name 과 battery 만 채워도 충분합니다 (Notification 본문에 표시할 용도).
        List<Robot> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM robots WHERE battery < " + threshold + ";", null
        );
        while (c.moveToNext()) {
            Robot r = new Robot();
            r.setName(c.getString(1));
            r.setBattery(c.getInt(3));
            list.add(r);
        }
        c.close();
        return list;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // assets 에서 미리 만든 DB 를 사용하므로 비워둡니다.
        // 만약 이 메서드가 호출된다면 = assets/databases/robots.db 가 누락된 상황입니다.
        android.util.Log.w("로봇DB", "onCreate()");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 버전 업그레이드 시 처리 (이번 실습 범위 아님)
    }
}
