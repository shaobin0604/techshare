
package com.example.android.windowanimations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SubActivity2 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2);
        final Button translateButtonThemeStyle = (Button) findViewById(R.id.translateButtonThemeStyle);
        translateButtonThemeStyle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent subActivity = new Intent(SubActivity2.this, SubActivity3.class);
                startActivity(subActivity);
            }
        });
    }
}
