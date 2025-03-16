package com.example.onebitmoblie.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onebitmoblie.Adapter.ScheduleAdapter;
import com.example.onebitmoblie.DAO.ScheduleDAO;
import com.example.onebitmoblie.Data.ViewModels.ScheduleModels;
import com.example.onebitmoblie.R;
import com.example.onebitmoblie.common.SessionManager;
import com.example.onebitmoblie.profile.ProfileActivity;
import com.example.onebitmoblie.schedule.ScheduleActivity;
import com.example.onebitmoblie.settings.SettingActivity;
import com.example.onebitmoblie.Notification.NotificationActivity ;

import org.jetbrains.annotations.Async;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity {
    Button btnHome, btnTracking, btnNotification ,btnProfile ;
    ImageButton btnFAQ;
    private List<ScheduleModels> scheduleList;
    private ScheduleDAO scheduleDAO;
    private SessionManager sessionManager;
    private RecyclerView recyclerView;
    private ScheduleAdapter scheduleAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        recyclerView = findViewById(R.id.recyclerView);
        scheduleList = new ArrayList<>();
        scheduleAdapter = new ScheduleAdapter(scheduleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(scheduleAdapter);

        sessionManager = new SessionManager(this);
        scheduleDAO = new ScheduleDAO();
        loadSchedules();



        btnHome = findViewById(R.id.btnHome);
        btnTracking = findViewById(R.id.btnTracking);
        btnNotification = findViewById(R.id.btnNotification);
        btnProfile = findViewById(R.id.btnProfile);
        btnFAQ = findViewById(R.id.btnFAQ);
//        btnHome.setOnClickListener(view -> startActivity(new Intent(this, HomeActivity.class)));
        btnProfile.setOnClickListener(view -> startActivity(new Intent(this, ProfileActivity.class)));
        btnNotification.setOnClickListener(view -> startActivity(new Intent(this, NotificationActivity.class)));
        btnFAQ.setOnClickListener(view -> startActivity(new Intent(this, ScheduleActivity.class)));
    }

    public void openSettings(android.view.View view){
        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    private void loadSchedules() {
        String userId = sessionManager.getKeyId();
        scheduleDAO.getSchedulesByUserId(userId, schedules -> {
            scheduleList.clear();
            scheduleList.addAll(schedules);
            runOnUiThread(() -> {
                scheduleAdapter.notifyDataSetChanged();
                Log.d("SCHEDULE_LOAD", "Loaded " + schedules.size() + " schedules");
            });
        });
    }
}
