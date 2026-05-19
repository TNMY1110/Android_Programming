package com.cookandroid.robotinspector.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cookandroid.robotinspector.model.Robot;

import java.util.ArrayList;
import java.util.List;

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
        // TODO ①: 아래 메서드를 구현하여 첫 실행 시 assets 에서 DB 를 복사하도록 만드세요.
        copyDatabaseFromAssets();
    }

    private void copyDatabaseFromAssets() {
        // TODO ①: assets/databases/robots.db 를 context.getDatabasePath(DB_NAME) 로 복사
        //   1) File dbFile = context.getDatabasePath(DB_NAME);
        //   2) if (dbFile.exists()) return;
        //   3) dbFile.getParentFile().mkdirs();
        //   4) InputStream is = context.getAssets().open("databases/" + DB_NAME);
        //      OutputStream os = new FileOutputStream(dbFile);
        //      byte[] buf = new byte[1024]; int len;
        //      while ((len = is.read(buf)) > 0) os.write(buf, 0, len);
        //   5) android.util.Log.i("로봇DB", "DB 복사 완료") / Log.e("로봇DB", "복사 실패", e);
        android.util.Log.w("로봇DB", "copyDatabaseFromAssets() — 미구현");
    }

    public List<Robot> getAllRobots() {
        // TODO: rawQuery("SELECT * FROM robots;") + Cursor.moveToNext() + Robot 객체 매핑
        //   컬럼 순서: 0=id, 1=name, 2=status, 3=battery, 4=latitude, 5=longitude, 6=last_inspected
        return new ArrayList<>();
    }

    public void insertInspection(int robotId, String memo) {
        // TODO: SimpleDateFormat("yyyy-MM-dd HH:mm") 으로 createdAt 생성
        //   execSQL("INSERT INTO inspections VALUES (NULL, " + robotId + ", '...', '...');")
    }

    public List<Robot> getLowBatteryRobots(int threshold) {
        // TODO: rawQuery("SELECT * FROM robots WHERE battery < " + threshold + ";")
        //   name 과 battery 만 채워도 충분합니다 (Notification 본문에 표시할 용도).
        return new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // assets 에서 미리 만든 DB 를 사용하므로 비워둡니다.
        // 만약 이 메서드가 호출된다면 = assets/databases/robots.db 가 누락된 상황입니다.
        android.util.Log.w("로봇DB", "onCreate() 호출 — assets 복사가 실패했을 가능성");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 버전 업그레이드 시 처리 (이번 실습 범위 아님)
    }
}
