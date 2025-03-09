package com.example.onebitmoblie.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.onebitmoblie.R;
import com.example.onebitmoblie.profile.ProfileActivity;

public class SettingActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void goBack(android.view.View view){
       onBackPressed();
    }
    public void comeProfile(android.view.View view){
        Intent intent = new Intent(SettingActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
