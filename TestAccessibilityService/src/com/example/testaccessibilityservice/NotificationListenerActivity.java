
package com.example.testaccessibilityservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NotificationListenerActivity extends Activity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_listener);
        Button btnGetNotification = (Button) findViewById(R.id.btn_get_notification);
        btnGetNotification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(this, NotificationListener.class);
        intent.setAction(NotificationListener.ACTION_GET_NOTIFICATION);
        startService(intent);
    }
    
    
}
