package com.example.onebitmoblie.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import com.example.onebitmoblie.R;
import com.example.onebitmoblie.profile.ProfileActivity;
import com.example.onebitmoblie.schedule.ScheduleActivity;
import com.example.onebitmoblie.settings.SettingActivity;
import com.example.onebitmoblie.Notification.NotificationActivity ;

import org.jetbrains.annotations.Async;

public class HomeActivity extends Activity {
    Button btnHome, btnTracking, btnNotification ,btnProfile ;
    ImageButton btnFAQ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

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
}
