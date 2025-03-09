package com.example.onebitmoblie.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onebitmoblie.R;
import com.example.onebitmoblie.databaseconfig.DbHelper;

import java.util.Calendar;

public class AddActivity extends Activity {

    private EditText titleInput;
    private TextView priorityLabel;
    private LinearLayout priorityDropdown;
    private TextView priorityUrgent;
    private TextView priorityImportant;
    private TextView priorityNormal;
    private TextView priorityLow;
    private TextView startTime;
    private TextView endTime;
    private Button saveButton;
    private ImageView attachmentPreview;

    private static final int PICK_IMAGE_REQUEST = 1;
    private String selectedPriority = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleInput = findViewById(R.id.txt_title);
        priorityLabel = findViewById(R.id.tv_priority_label);
        priorityDropdown = findViewById(R.id.dropdown_menu);
        priorityUrgent = findViewById(R.id.priority_urgent);
        priorityImportant = findViewById(R.id.priority_important);
        priorityNormal = findViewById(R.id.priority_normal);
        priorityLow = findViewById(R.id.priority_low);
        startTime = findViewById(R.id.txt_start_time);
        endTime = findViewById(R.id.txt_end_time);
        saveButton = findViewById(R.id.btn_save);
        attachmentPreview = findViewById(R.id.img_attachment_preview);

        setupPriorityDropdown();
        saveButton.setOnClickListener(v -> onSaveClick());
        startTime.setOnClickListener(v -> onTimeSelect(startTime));
        endTime.setOnClickListener(v -> onTimeSelect(endTime));
        attachmentPreview.setOnClickListener(v -> openImagePicker());
    }

    private void setupPriorityDropdown() {
        priorityLabel.setOnClickListener(v -> {
            if (priorityDropdown.getVisibility() == View.GONE) {
                priorityDropdown.setVisibility(View.VISIBLE);
            } else {
                priorityDropdown.setVisibility(View.GONE);
            }
        });

        View.OnClickListener priorityClickListener = v -> {
            selectedPriority = ((TextView) v).getText().toString();
            priorityLabel.setText(selectedPriority);
            priorityDropdown.setVisibility(View.GONE);

            switch (selectedPriority) {
                case "Urgent":
                    priorityLabel.setTextColor(Color.RED);
                    break;
                case "Important":
                    priorityLabel.setTextColor(Color.parseColor("#FFA500"));
                    break;
                case "Normal":
                    priorityLabel.setTextColor(Color.CYAN);
                    break;
                case "Low Priority":
                    priorityLabel.setTextColor(Color.BLUE);
                    break;
            }
        };

        priorityUrgent.setOnClickListener(priorityClickListener);
        priorityImportant.setOnClickListener(priorityClickListener);
        priorityNormal.setOnClickListener(priorityClickListener);
        priorityLow.setOnClickListener(priorityClickListener);
    }

    private void onSaveClick() {
        String title = titleInput.getText().toString().trim();
        String start = startTime.getText().toString();
        String end = endTime.getText().toString();

        if (TextUtils.isEmpty(title)) {
            showAlert("Title cannot be empty.");
            return;
        }

        if (selectedPriority == null) {
            showAlert("Please select a priority.");
            return;
        }

        if (TextUtils.isEmpty(start) || TextUtils.isEmpty(end)) {
            showAlert("Start and End times must be selected.");
            return;
        }

        if (!isEndTimeValid(start, end)) {
            showAlert("End time cannot be earlier than Start time.");
            return;
        }

        int priorityValue;
        switch (selectedPriority) {
            case "Low":
                priorityValue = 0;
                break;
            case "Normal":
                priorityValue = 1;
                break;
            case "High":
                priorityValue = 2;
                break;
            default:
                priorityValue = 1; // Giá trị mặc định
        }

        try {
            DbHelper dbHelper = new DbHelper(this, null);
            long activityId = dbHelper.insertData(
                "Activities",
                new String[]{"title", "piority", "imageUrl", "type", "isDeleted"},
                new String[]{title, String.valueOf(priorityValue), "image_placeholder", "0", "0"}
            );


            if (activityId != -1) {
                dbHelper.insertData(
                        "ActivityTrackLogs",
                        new String[]{"schedulingId", "activityID", "startTime", "endTime", "status", "isDeleted"},
                        new String[]{"1", String.valueOf(activityId), start, end, "0", "0"}
                );

                Toast.makeText(this, "Activity saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                showAlert("Error saving activity.");
            }

            // Reset fields
            titleInput.setText("");
            priorityLabel.setText("Select priority level");
            priorityLabel.setTextColor(Color.GRAY);
            selectedPriority = null;
            startTime.setText("Select Start Time");
            endTime.setText("Select End Time");

        } catch (Exception e) {
            Toast.makeText(this, "Error saving data: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private boolean isEndTimeValid(String start, String end) {
        try {
            String[] startParts = start.split(":");
            String[] endParts = end.split(":");

            int startHour = Integer.parseInt(startParts[0]);
            int startMinute = Integer.parseInt(startParts[1]);

            int endHour = Integer.parseInt(endParts[0]);
            int endMinute = Integer.parseInt(endParts[1]);

            if (endHour < startHour || (endHour == startHour && endMinute < startMinute)) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    private void onTimeSelect(TextView timeField) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(this,
                (view, selectedHour, selectedMinute) -> {
                    String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                    timeField.setText(formattedTime);
                }, hour, minute, true);

        timePicker.show();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            attachmentPreview.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Validation Error")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}
