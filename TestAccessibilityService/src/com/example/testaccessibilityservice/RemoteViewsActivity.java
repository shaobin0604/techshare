package com.example.testaccessibilityservice;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.achep.acdisplay.notifications.NotificationData;

import de.greenrobot.event.EventBus;

public class RemoteViewsActivity extends Activity {
    private static final String TAG = RemoteViewsActivity.class.getSimpleName();
    private LinearLayout mContainer;
    private EventBus mEventBus;
    private LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_views);
        mContainer = (LinearLayout) findViewById(R.id.container);
        mLayoutInflater = (LayoutInflater) LayoutInflater.from(this);
        
        mEventBus = EventBus.getDefault();
    }
    
    
    public void onEvent(NotificationDataEvent event) {
        final View contentView = extractContentView(event.data);
        if (contentView != null) {
            mContainer.addView(contentView);
        }
    }
    
    private View extractContentView(NotificationData notificationData) {
        Notification notification = notificationData.notification;
        final RemoteViews contentView = notification.bigContentView != null ? notification.bigContentView : notification.contentView;
        View view = mLayoutInflater.inflate(contentView.getLayoutId(), null);
        if (view == null) {
            return null;
        }

        Context contextNotify = createContext(this, notificationData.packageName.toString());
        try {
            contentView.reapply(contextNotify, view);
        } catch (Exception e) {
            return null;
        }
        return view;
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        if (mEventBus.isRegistered(this)) {
            mEventBus.unregister(this);
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (!mEventBus.isRegistered(this)) {
            mEventBus.register(this);
        }
    }
    
    private static Context createContext(Context context, String packageName) {
        try {
            return context.createPackageContext(packageName, Context.CONTEXT_RESTRICTED);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, "Failed to create notification\'s context");
            return null;
        }
    }
}
