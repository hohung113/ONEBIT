package com.example.onebitmoblie.Notification;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onebitmoblie.Adapter.NotificationAdapter;
import com.example.onebitmoblie.Data.NotificationType;
import com.example.onebitmoblie.Data.ViewModels.NotificationVM;
import com.example.onebitmoblie.R;
import com.example.onebitmoblie.homepage.HomeActivity;
import com.example.onebitmoblie.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView rcvNotification;
    private List<NotificationVM> notificationVMS;
    private NotificationAdapter notificationAdapter;
    Button btnHome, btnTracking ,btnProfile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //get button back
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (toolbar.getNavigationIcon() != null) {
            int whiteColor = ContextCompat.getColor(this, R.color.white);
            toolbar.getNavigationIcon().setTint(whiteColor);
        }

        //set list recycle view
        rcvNotification = findViewById(R.id.recyclerView);
        notificationVMS = new ArrayList<>();

        notificationVMS.add(new NotificationVM("Meeting Reminder", "You have a meeting at 10 AM",
                NotificationType.SCHEDULES, "2025-03-01 08:00", "2025-03-01 08:05", true));

        notificationVMS.add(new NotificationVM("System Update", "A new system update is available",
                NotificationType.SYSTEM, "2025-03-01 09:30", "2025-03-01 09:35", false));

        notificationVMS.add(new NotificationVM("Event Invitation", "You are invited to the annual company party",
                NotificationType.SCHEDULES, "2025-03-02 14:00", "2025-03-02 14:05", false));

        notificationVMS.add(new NotificationVM("Security Alert", "Unusual login detected from a new device",
                NotificationType.SYSTEM, "2025-03-02 18:45", "2025-03-02 18:50", true));

        notificationVMS.add(new NotificationVM("Schedule Change", "Your appointment has been rescheduled to 3 PM",
                NotificationType.SCHEDULES, "2025-03-03 10:15", "2025-03-03 10:20", true));


        notificationAdapter = new NotificationAdapter(notificationVMS);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvNotification.setLayoutManager(linearLayoutManager);
        rcvNotification.setAdapter(notificationAdapter);

        //navigation
        btnHome = findViewById(R.id.btnHome);
        btnTracking = findViewById(R.id.btnTracking);
        btnProfile = findViewById(R.id.btnProfile);
        btnHome.setOnClickListener(view -> startActivity(new Intent(this, HomeActivity.class)));
        btnProfile.setOnClickListener(view -> startActivity(new Intent(this, ProfileActivity.class)));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // id mac dinh cua button back
        if (item.getItemId() == android.R.id.home){
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}