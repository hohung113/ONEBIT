package com.example.onebitmoblie.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onebitmoblie.R;
import com.example.onebitmoblie.Schedule.ScheduleActivity;
import com.example.onebitmoblie.profile.ProfileActivity;

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
        btnFAQ.setOnClickListener(view -> startActivity(new Intent(this, ScheduleActivity.class)));


    }


}
