package com.cookandroid.robotinspector.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.cookandroid.robotinspector.MainActivity;
import com.cookandroid.robotinspector.R;


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
        // Android 8.0(API 26) 이상은 채널을 만들어야 알림이 보임
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "로봇 점검 알림",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("로봇 배터리 부족 등 점검 필요 시 표시");
            NotificationManager nm = getSystemService(NotificationManager.class);
            nm.createNotificationChannel(channel);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");

        if (title == null) title = "로봇 점검 필요";
        if (message == null) message = "배터리가 부족한 로봇이 있습니다";

        android.util.Log.i("로봇알림", "onStartCommand() — " + title + " / " + message);

        // 알림 탭 시 MainActivity로 이동
        Intent tapIntent = new Intent(this, MainActivity.class);
        tapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(
                this, 0, tapIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pi);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(NOTI_ID, builder.build());
        stopSelf();  // 알림 한 번 띄우고 서비스 자동 종료
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        android.util.Log.i("로봇알림", "onDestroy()");
    }
}
