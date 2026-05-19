package com.cookandroid.robotinspector.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Project14_1의 MusicService 라이프사이클을 따르되,
 * MediaPlayer 대신 시스템 알림(Notification) 팝업을 띄웁니다.
 */
public class AlertService extends Service {
    private static final String CHANNEL_ID = "robot_alert_channel";
    private static final int NOTI_ID = 1001;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        android.util.Log.i("로봇알림", "onCreate()");
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        // TODO ⑥: Android 8.0(API 26) 이상에서는 NotificationChannel 을 만들어야 알림이 보입니다.
        //   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        //       NotificationChannel ch = new NotificationChannel(
        //           CHANNEL_ID, "로봇 점검 알림", NotificationManager.IMPORTANCE_HIGH);
        //       getSystemService(NotificationManager.class).createNotificationChannel(ch);
        //   }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        android.util.Log.i("로봇알림", "onStartCommand()");

        String title = intent != null ? intent.getStringExtra("title") : null;
        String message = intent != null ? intent.getStringExtra("message") : null;
        if (title == null) title = "로봇 점검 필요";
        if (message == null) message = "배터리가 부족한 로봇이 있습니다";

        // TODO ⑥: NotificationCompat.Builder 로 알림 생성 후 NotificationManager.notify(NOTI_ID, ...) 호출
        //   - setSmallIcon(android.R.drawable.ic_dialog_alert)
        //   - setContentTitle(title) / setContentText(message)
        //   - setStyle(new NotificationCompat.BigTextStyle().bigText(message))
        //   - 알림 탭 시 MainActivity 로 이동: PendingIntent.getActivity(...)
        //   - setAutoCancel(true) / setPriority(PRIORITY_HIGH)

        stopSelf();  // 1회성 알림 (Project14_1과 달리 사운드 무한반복 X)
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        android.util.Log.i("로봇알림", "onDestroy()");
    }
}
