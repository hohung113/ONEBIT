package com.example.onebitmoblie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;

public class LoginSuccessAcitivity extends Activity {
    TextView ten_txt;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginsuccess);

        ten_txt = findViewById(R.id.ten);

        String keyvalue = getIntent().getStringExtra("user");
        ten_txt.setText("Welcome "+ keyvalue);
    }
}
