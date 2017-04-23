package com.example.vmm408.calendarmar_29.service;

import android.app.Notification;
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
            System.out.println("alert !!!");
//            startForeground(111, createNotification());
            createNotification();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerReceiver(broadcastReceiver, new IntentFilter("wakeup"));
        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 112, new Intent(this, MainActivity.class), Intent.FILL_IN_ACTION);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);

        notification.setSmallIcon(R.drawable.ic_sms_black_24dp);
        notification.setContentTitle("My title");
        notification.setContentText("My description");
        notification.setAutoCancel(true);
        notification.setContentIntent(pendingIntent);
        notification.addAction(new NotificationCompat.Action(0, "OKi", pendingIntent));

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(111, notification.build());
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
