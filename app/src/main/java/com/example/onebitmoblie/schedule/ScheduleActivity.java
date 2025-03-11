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
import com.example.onebitmoblie.login_register.RegisterActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

public class ScheduleActivity extends Activity {
    private LinearLayout activityContainer;
    private TextView dateText;
    private Calendar calendar;

    private EditText title;
    private EditText description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        activityContainer = findViewById(R.id.activity_container);

        ImageButton addActivityBtn = findViewById(R.id.add_activity_btn);

        addActivityBtn.setOnClickListener(v -> addNewActivity());

        Button saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener( v -> saveBtnClick());

        title = findViewById(R.id.txt_title);
        description = findViewById(R.id.txt_description);

        //init today calendar
        calendar = Calendar.getInstance();
        dateText = findViewById(R.id.schedule_date_txt);

        findViewById(R.id.schedule_date).setOnClickListener(v -> showDatePicker());

        saveSelectedDate();
    }

    private void showDatePicker()
    {
        Calendar today = Calendar.getInstance();

        // Create a DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    updateDateText();
                },
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
        );

        // Restrict selection to today or later
        datePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());

        datePickerDialog.show();
    }

    private void updateDateText()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateText.setText(sdf.format(calendar.getTime()));
    }

    private void addNewActivity()
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardView = inflater.inflate(R.layout.activity_card, activityContainer, false);

        View deleteBtn = cardView.findViewById(R.id.remove_card);
        deleteBtn.setOnClickListener(v -> activityContainer.removeView(cardView));

        activityContainer.addView(cardView, activityContainer.getChildCount() - 1);
    }

    private void saveSelectedDate()
    {
        dateText.setText(calendar.getTime().toString());
    }

    private void saveBtnClick()
    {
        String scheduleID = UUID.randomUUID().toString();
        String today = Calendar.getInstance().toString();
        String userID = new SessionManager(this).getKeyId();

        String fromDate = calendar.toString();
        String toDate = calendar.toString();
        String titleTxt = title.getText().toString();
        String descriptionTxt = description.getText().toString();

        //validation
        if(titleTxt.isEmpty() || descriptionTxt.isEmpty())
        {
            PopupHelper.shopPopup(this, "Please don't let title or description empty!"
                    , Color.RED, Color.WHITE);
            return;
        }

        Scheduling s = new Scheduling(scheduleID, false, today, null, null,
                titleTxt, userID, fromDate, toDate, descriptionTxt, SchedulingStatus.IN_PROGRESS);

        saveScheduleFB(s);
    }
    private void saveScheduleFB(Scheduling schedule)
    {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("scheduling");
        dbRef.child(schedule.getId()).setValue(schedule)
                .addOnSuccessListener(aVoid -> {
                    goHome();
                    finish();
                })
                .addOnFailureListener(e -> {
                    PopupHelper.shopPopup(this," Save schedule fail\nError: " + e.getMessage(), Color.WHITE, Color.RED);
                });
    }
    private void goHome()
    {
        startActivity(new Intent(this, HomeActivity.class));
    }
}
