package com.example.testaccessibilityservice;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Arrays;

public class NotificationListener extends NotificationListenerService {
    
    public static final String ACTION_GET_NOTIFICATION = "action.GET_NOTIFICATION";
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(ACTION_GET_NOTIFICATION)) {
            StatusBarNotification[] activeNotifications = getActiveNotifications();
            for (StatusBarNotification sbn : activeNotifications) {
                if (sbn.getPackageName().equals("com.android.mms")) {
                    Notification n = sbn.getNotification();
                    Toast.makeText(this, "tickerText: " + n.tickerText, Toast.LENGTH_SHORT).show();
                    Bundle extras = n.extras;
                    if (extras != null) {
                        
                        String title = extras.getString(Notification.EXTRA_TITLE);
                        Toast.makeText(this, "title: " + title, Toast.LENGTH_SHORT).show();
                        
                        String titleBig = extras.getString(Notification.EXTRA_TITLE_BIG);
                        Toast.makeText(this, "titleBig: " + titleBig, Toast.LENGTH_SHORT).show();

                        String text = extras.getString(Notification.EXTRA_TEXT);
                        Toast.makeText(this, "text: " + text, Toast.LENGTH_SHORT).show();
                        
                        String[] textLines = extras.getStringArray(Notification.EXTRA_TEXT_LINES);
                        Toast.makeText(this, "textLines: " + Arrays.toString(textLines), Toast.LENGTH_SHORT).show();
                        
                        String subText = extras.getString(Notification.EXTRA_SUB_TEXT);
                        Toast.makeText(this, "subText: " + subText, Toast.LENGTH_SHORT).show();
                        
                        String summaryText = extras.getString(Notification.EXTRA_SUMMARY_TEXT);
                        Toast.makeText(this, "summaryText: " + summaryText, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // TODO Auto-generated method stub
        
    }

}
