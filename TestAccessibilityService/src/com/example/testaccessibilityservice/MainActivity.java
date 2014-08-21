
package com.example.testaccessibilityservice;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.achep.acdisplay.notifications.NotificationData;
import com.achep.acdisplay.notifications.NotificationUtils;

import de.greenrobot.event.EventBus;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, RemoteViewsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements OnItemClickListener {
        private ListView mListView;

        private NotificationAdapter mAdapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mListView = (ListView) rootView.findViewById(R.id.notification_list);
            mListView.setOnItemClickListener(this);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mAdapter = new NotificationAdapter(getActivity(), R.layout.item_notification);
            mListView.setAdapter(mAdapter);
            EventBus.getDefault().register(this);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            EventBus.getDefault().unregister(this);
        }

        @Override
        public void onResume() {
            super.onResume();
            EventBus.getDefault().register(this);
        }

        public void onEvent(NotificationDataEvent event) {
            mAdapter.add(event.data);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            NotificationData item = mAdapter.getItem(position);
            try {
                item.pendingIntent.send();
            } catch (CanceledException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        private static class NotificationAdapter extends ArrayAdapter<NotificationData> {

            public NotificationAdapter(Context context, int resource, int textViewResourceId,
                    List<NotificationData> objects) {
                super(context, resource, textViewResourceId, objects);
                // TODO Auto-generated constructor stub
            }

            public NotificationAdapter(Context context, int resource, int textViewResourceId,
                    NotificationData[] objects) {
                super(context, resource, textViewResourceId, objects);
                // TODO Auto-generated constructor stub
            }

            public NotificationAdapter(Context context, int resource, int textViewResourceId) {
                super(context, resource, textViewResourceId);
                // TODO Auto-generated constructor stub
            }

            public NotificationAdapter(Context context, int resource, List<NotificationData> objects) {
                super(context, resource, objects);
                // TODO Auto-generated constructor stub
            }

            public NotificationAdapter(Context context, int resource, NotificationData[] objects) {
                super(context, resource, objects);
                // TODO Auto-generated constructor stub
            }

            public NotificationAdapter(Context context, int resource) {
                super(context, resource);
                // TODO Auto-generated constructor stub
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.item_notification, parent, false);
                }
                ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
                TextView title = (TextView) convertView.findViewById(R.id.title);
                TextView message = (TextView) convertView.findViewById(R.id.message);

                NotificationData data = getItem(position);
                if (data.largeIcon != null) {
                    icon.setImageBitmap(data.largeIcon);
                } else {
                    icon.setImageDrawable(NotificationUtils.getDrawable(getContext(),
                            data.packageName.toString(), data.iconRes));
                }
                title.setText(data.titleText);
                message.setText(data.messageText);
                return convertView;
            }

        }
    }

}
