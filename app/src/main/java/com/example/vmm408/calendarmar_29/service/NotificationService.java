package com.example.vmm408.calendarmar_29.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.vmm408.calendarmar_29.MainActivity;
import com.example.vmm408.calendarmar_29.R;

public class NotificationService extends Service {
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            createNotification(intent.getStringExtra("description"));
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(broadcastReceiver, new IntentFilter("wakeUp"));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    private void createNotification(String description) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_sms_black_24dp)
                .setContentTitle("Event")
                .setContentText(description)
                .setAutoCancel(true)
                .setContentIntent(initPendingIntent())
                .addAction(new NotificationCompat.Action(0, "OK", initPendingIntent()));
        initNotificationManager(notification);
    }

    private PendingIntent initPendingIntent() {
        return PendingIntent.getActivity(this, 112,
                new Intent(this, MainActivity.class), Intent.FILL_IN_ACTION);
    }

    private void initNotificationManager(NotificationCompat.Builder notification) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(111, notification.build());
    }
}
