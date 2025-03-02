package com.example.onebitmoblie.Schedule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.onebitmoblie.R;
import com.example.onebitmoblie.login_register.LoginActivity;

public class ScheduleActivity extends Activity{
    private EditText title;
    private EditText description;
    private LinearLayout activityContainer;
    private Button addActivityBtn;
    private Button saveButton;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        title = findViewById(R.id.txt_title);
        description = findViewById(R.id.txt_description);
        activityContainer = findViewById(R.id.activity_container);

        findViewById(R.id.save_btn).setOnClickListener(v -> onSaveClick());
        findViewById(R.id.add_activity_btn).setOnClickListener(v -> onAddActivityClick());
    }

    public void onSaveClick()
    {
        String titleText = title.getText().toString().trim();
        String desText = description.getText().toString().trim();

        if(titleText.isEmpty() || desText.isEmpty())
            new LoginActivity().warningPopup(this,"title and description can not be empty!");
        else
        {
            //save schedule
        }
    }

    public void onAddActivityClick()
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardView = inflater.inflate(R.layout.activity_card,activityContainer,false);
        activityContainer.addView(cardView,activityContainer.getChildCount()-1);
    }
}

