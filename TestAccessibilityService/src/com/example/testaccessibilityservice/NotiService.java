
package com.example.testaccessibilityservice;

import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.os.Parcelable;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.achep.acdisplay.notifications.NotificationData;
import com.achep.acdisplay.notifications.parser.Extractor;

import de.greenrobot.event.EventBus;

public class NotiService extends AccessibilityService {

    private static final String TAG = "NotiService";

    private Extractor mExtractor;

    @Override
    public void onCreate() {
        super.onCreate();
        mExtractor = new Extractor();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        CharSequence className = event.getClassName();
        CharSequence packageName = event.getPackageName();
        long eventTime = event.getEventTime();
        List<CharSequence> text = event.getText();
        Parcelable parcelableData = event.getParcelableData();
        if (parcelableData instanceof Notification) {
            Notification notification = (Notification) parcelableData;
            Log.d(TAG, "onAccessibilityEvent, eventType: " + eventType + ", packageName: "
                    + packageName + ", className: " + className + ", text: " + text
                    + ", eventTime: " + eventTime + ", notification: " + notification);
            NotificationData data = new NotificationData();
            mExtractor.loadTexts(this, packageName.toString(), notification, data);
            data.iconRes = notification.icon;
            data.packageName = packageName;
            data.when = notification.when;
            data.pendingIntent = notification.contentIntent;
            Log.d(TAG, "onAccessibilityEvent, data: " + data);
            // try {
            // data.pendingIntent.send();
            // } catch (CanceledException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
            EventBus.getDefault().post(new NotificationDataEvent(data));
        }
    }

    @Override
    public void onInterrupt() {
        // TODO Auto-generated method stub

    }

}
