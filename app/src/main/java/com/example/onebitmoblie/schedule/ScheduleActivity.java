package com.example.onebitmoblie.schedule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.onebitmoblie.R;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ScheduleActivity extends Activity {
    private LinearLayout activityContainer;
    private TextView dateText;
    private Calendar calendar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        activityContainer = findViewById(R.id.activity_container);

        ImageButton addActivityBtn = findViewById(R.id.add_activity_btn);

        addActivityBtn.setOnClickListener(v -> addNewActivity());

        findViewById(R.id.schedule_date).setOnClickListener(v -> showDatePicker());
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

    private void saveSchedule()
    {

    }
}
