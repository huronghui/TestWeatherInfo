package com.example.hrh.testweatherinfo.UtilTest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.hrh.testweatherinfo.activity.AsyncTaskImageLoaderActivity;
import com.example.hrh.testweatherinfo.activity.MainActivity;
import com.example.hrh.testweatherinfo.R;

/**
 * Created by hrh on 2015/9/20.
 */
public class MyNotification {

    private Notification notification = new Notification();
    private Context context;

    public MyNotification(Context context) {
        this.context = context;
    }

    public void initNotification() {
        String service = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(service);
        notification.icon = R.drawable.notification_template_icon_bg;
        notification.tickerText = "Notification Test";
        notification.when = System.currentTimeMillis();
        Intent intent = new Intent(context, AsyncTaskImageLoaderActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        notification.setLatestEventInfo(context, "my title", "my content", pi);
        notificationManager.notify(1, notification);
    }
    public void showNotification() {
       initNotification();
    }
}
