package com.example.testaccessibilityservice;

import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

public class NotiService extends AccessibilityService {

    private static final String TAG = "NotiService";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        CharSequence className = event.getClassName();
        CharSequence packageName = event.getPackageName();
        long eventTime = event.getEventTime();
        List<CharSequence> text = event.getText();
        Notification notification = (Notification) event.getParcelableData();
        
        Log.d(TAG, "eventType: " + eventType + ", packageName: " + packageName +
                ", className: " + className + ", text: " + text +
                ", notification: " + notification);
    }

    @Override
    public void onInterrupt() {
        // TODO Auto-generated method stub

    }

}
