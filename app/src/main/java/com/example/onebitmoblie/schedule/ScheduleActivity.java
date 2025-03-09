package com.example.onebitmoblie.schedule;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.onebitmoblie.Data.DatabaseEntities.Scheduling;
import com.example.onebitmoblie.Data.SchedulingStatus;
import com.example.onebitmoblie.R;
import com.example.onebitmoblie.common.PopupHelper;
import com.example.onebitmoblie.common.SessionManager;
import com.example.onebitmoblie.homepage.HomeActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class ScheduleActivity extends Activity {
    private EditText title, description;
    private LinearLayout activityContainer;
    private Button saveButton;
    private ImageButton addActivityBtn;
    private TextView dateText;
    private Calendar calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        title = findViewById(R.id.txt_title);
        description = findViewById(R.id.txt_description);
        activityContainer = findViewById(R.id.activity_container);
        addActivityBtn = findViewById(R.id.add_activity_btn);
        saveButton = findViewById(R.id.save_btn);
        dateText = findViewById(R.id.schedule_date_txt);
        calendar = Calendar.getInstance();

        addActivityBtn.setOnClickListener(v -> onAddActivityClick());
        saveButton.setOnClickListener(v -> saveBtnClick());
        findViewById(R.id.schedule_date).setOnClickListener(v -> showDatePicker());
    }

    public void onAddActivityClick() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardView = inflater.inflate(R.layout.activity_card, activityContainer, false);

        View deleteBtn = cardView.findViewById(R.id.remove_card);
        deleteBtn.setOnClickListener(v -> activityContainer.removeView(cardView));

        activityContainer.addView(cardView, activityContainer.getChildCount() - 1);
    }
    private void saveBtnClick() {
        String scheduleID = UUID.randomUUID().toString();
        String today = Calendar.getInstance().toString();
        String userID = new SessionManager(this).getKeyId();

        String fromDate = calendar.toString();
        String toDate = calendar.toString();
        String titleTxt = title.getText().toString();
        String descriptionTxt = description.getText().toString();

        //validation
        if (titleTxt.isEmpty() || descriptionTxt.isEmpty()) {
            PopupHelper.shopPopup(this, "Please don't let title or description empty!"
                    , Color.RED, Color.WHITE);
            return;
        }
    }
    private void showDatePicker()
    {
        Calendar today = Calendar.getInstance();

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    //updateDateText();
                },
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
        );

        // Restrict selection to today or later
        datePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());

        datePickerDialog.show();
    }
}
