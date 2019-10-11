package com.example.smsinbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<SMSData> smsList;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Added this line for testing git on Android Project

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiperefresh);

        smsList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);


        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        final MyRecyclerView adapter = new MyRecyclerView(smsList);
        recyclerView.setAdapter(adapter);

        if (ContextCompat.checkSelfPermission(getBaseContext(),
                "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            refreshSmsInbox();
//            RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
//            recyclerView.setLayoutManager(manager);
//            MyRecyclerView adapter = new MyRecyclerView(smsList);
//            recyclerView.setAdapter(adapter);

        } else {
            // Todo : Then Set Permission
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        viewHolder.getAdapterPosition();
                Toast.makeText(MainActivity.this, "Swipe is Broken", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshSmsInbox();
                        swipeRefreshLayout.setRefreshing(false);

                    }
                },2000);


            }
        });
    }

    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        int indexId = smsInboxCursor.getColumnIndex("_id");

        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        do {

            SMSData smsData = new SMSData(smsInboxCursor.getString(indexAddress), smsInboxCursor.getString(indexBody),smsInboxCursor.getInt(indexId));
            smsList.add(smsData);

        } while (smsInboxCursor.moveToNext());
    }
    private void removeItem(int id) {



        ContentResolver contentResolver = getContentResolver();
        Cursor c = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
       c.moveToFirst();

        int idd = c.getInt(0);
        getContentResolver().delete(
                Uri.parse("content://sms/" + id), null, null);

    }
    public boolean deleteSms(String smsId) {
        boolean isSmsDeleted = false;
        try {
            getContentResolver().delete(
                    Uri.parse("content://sms/" + smsId), null, null);
            isSmsDeleted = true;

        } catch (Exception ex) {
            isSmsDeleted = false;
        }
        return isSmsDeleted;
    }

}