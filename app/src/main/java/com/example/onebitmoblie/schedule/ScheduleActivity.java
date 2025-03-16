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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.onebitmoblie.Activity.AddActivity;
import com.example.onebitmoblie.Data.DatabaseEntities.ActivityTrackLogs;
import com.example.onebitmoblie.Data.DatabaseEntities.Scheduling;
import com.example.onebitmoblie.Data.SchedulingStatus;
import com.example.onebitmoblie.R;
import com.example.onebitmoblie.common.PopupHelper;
import com.example.onebitmoblie.common.SessionManager;
import com.example.onebitmoblie.homepage.HomeActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Consumer;

public class ScheduleActivity extends Activity {
    private LinearLayout activityContainer;
    private TextView dateText;
    private Calendar calendar;
    private EditText title;
    private EditText description;

    private static final byte REQUEST_CODE_CREATE_ACTIVITY = 1;
    private static final byte REQUEST_CODE_UPDATE_ACTIVITY = 2;

    private UUID scheduleUUID = UUID.randomUUID();
    private LinearLayout activityLayout;
    private List<ActivityDTOs> activities = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_CODE_CREATE_ACTIVITY && resultCode == RESULT_OK && data != null)
        {
            ActivityDTOs activityCardModel = data.getParcelableExtra("activity_data");
            if(activityCardModel != null)
            {
                activities.add(activityCardModel);

                int currentIdx = activities.indexOf(activityCardModel);
                addNewActivityCard(activityCardModel,currentIdx);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        generateUniqueUUID(uniqueUUID -> {
            scheduleUUID = uniqueUUID; // Use the unique UUID
        });

        activityContainer = findViewById(R.id.activity_container);

        ImageButton addActivityBtn = findViewById(R.id.add_activity_btn);

        addActivityBtn.setOnClickListener(v -> onAddActivity());

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

    private void addNewActivityCard(ActivityDTOs info, int arrIndex)
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardView = inflater.inflate(R.layout.activity_card, activityContainer, false);

        View deleteBtn = cardView.findViewById(R.id.remove_card);
        TextView title = cardView.findViewById(R.id.activity_title);
        TextView time = cardView.findViewById(R.id.activity_time);

        title.setText(info.getTitle());
        String timeTxt = info.getStartTime() + " - " + info.getEndTime();
        time.setText(timeTxt);

        deleteBtn.setOnClickListener(v -> removeActivity(cardView, arrIndex));
        activityContainer.addView(cardView, activityContainer.getChildCount() - 1);
    }
    private void removeActivity(View cardView, int index)
    {
        //get the activity info will going to be remove
        ActivityDTOs ac = activities.get(index);
        //remove it from fb in activities
        var dbRef = FirebaseDatabase.getInstance().getReference("activities");
        dbRef.child(ac.getActivityID().toString()).removeValue()
                .addOnSuccessListener( v -> {
                    //remove activityCard info from list
                    activities.remove(index);
                    //remove layout view
                    activityContainer.removeView(cardView);
                })
                .addOnFailureListener(v ->{
                    PopupHelper.shopPopup(this, "Remove activity failed!"
                            , Color.RED, Color.WHITE);
                });
    }

    private void saveSelectedDate()
    {
        dateText.setText(calendar.getTime().toString());
    }

    private void saveBtnClick()
    {
        String scheduleID = scheduleUUID.toString();
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
                .addOnFailureListener(e -> {
                    PopupHelper.shopPopup(this," Save schedule fail\nError: " + e.getMessage(), Color.WHITE, Color.RED);
                });

        //save activityTrackLog
        dbRef = FirebaseDatabase.getInstance().getReference("activityTrackLog");
        for (var item: activities) {
            var log = mapping(item);
            dbRef.child(log.getId()).setValue(log)
                    .addOnFailureListener(e ->{
                        PopupHelper.shopPopup(this," Save activity log fail\nError: " + e.getMessage(), Color.WHITE, Color.RED);
                    });
        }

        goHome();
        finish();
    }
    private ActivityTrackLogs mapping(ActivityDTOs ac)
    {
        if(ac == null) return null;
        return new ActivityTrackLogs(UUID.randomUUID().toString(),false, java.util.Calendar.getInstance().toString(),null,null
        ,ac.getActivityID().toString(), ac.getScheduleID().toString(), ac.getStartTime(), ac.getEndTime(), true);
    }
    private void generateUniqueUUID(Consumer<UUID> callback) {
        UUID newUUID = UUID.randomUUID(); // Generate a new UUID

        checkExistUUID(newUUID.toString()).addOnSuccessListener(exists -> {
            if (exists) {
                // If UUID exists, try again recursively
                generateUniqueUUID(callback);
            } else {
                // If UUID is unique, return it
                callback.accept(newUUID);
            }
        });
    }

    private Task<Boolean> checkExistUUID(String scheduleUUID)
    {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("scheduling");
        TaskCompletionSource<Boolean> taskSource = new TaskCompletionSource<>();
        dbRef.orderByKey().equalTo(scheduleUUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskSource.setResult(dataSnapshot.exists()); // Set true if exists, false otherwise
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                taskSource.setResult(false); // Return false if Firebase fails
            }
        });
        return taskSource.getTask();
    }
    private void goHome()
    {
        startActivity(new Intent(this, HomeActivity.class));
    }

    private void onAddActivity()
    {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("SCHEDULE_UUID",scheduleUUID);
        startActivityForResult(intent, REQUEST_CODE_CREATE_ACTIVITY);
    }
    private void onUpdateActivity()
    {
        Intent intent = new Intent(this, AddActivity.class);
        //transfer activity data to it?
        startActivityForResult(intent, REQUEST_CODE_UPDATE_ACTIVITY);
    }
}
