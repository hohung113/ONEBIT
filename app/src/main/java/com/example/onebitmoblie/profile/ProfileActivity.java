package com.example.onebitmoblie.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.onebitmoblie.R;

import com.example.onebitmoblie.common.SessionManager;
import com.example.onebitmoblie.databaseconfig.DbHelper;
import com.example.onebitmoblie.homepage.HomeActivity;
import com.example.onebitmoblie.login_register.LoginActivity;

import java.io.IOException;
import java.util.List;

public class ProfileActivity extends Activity {
    Button btnHome, btnTracking, btnNotification, btnProfile;
    ImageButton btnFAQ;
    Button btnEditName, btnEditDob, btnCurrentJob, btnEditGender, btnAddImage;
    private DbHelper dbHelper;
    private SessionManager _sessionManager;
    private TextView txtUsername, txtDob, txtCurrentJob, txtGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profiledetail);
        try {
            dbHelper = new DbHelper(this, null);
            _sessionManager = new SessionManager(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // check userId existed
        String userId = _sessionManager.getKeyId();
        if (userId == null) {
            Toast.makeText(this, "User not found. Please log in again.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        txtUsername = findViewById(R.id.txtUsername);
        txtDob = findViewById(R.id.txtDob);
        txtCurrentJob = findViewById(R.id.txtCurrentJob);
        txtGender = findViewById(R.id.txtEmail);

        loadUserProfile(userId);
        
        btnHome = findViewById(R.id.btnHome);
        btnTracking = findViewById(R.id.btnTracking);
        btnNotification = findViewById(R.id.btnNotification);
        btnProfile = findViewById(R.id.btnProfile);
        btnFAQ = findViewById(R.id.btnFAQ);
        btnHome.setOnClickListener(view -> startActivity(new Intent(this, HomeActivity.class)));

        btnEditName = findViewById(R.id.btnEditName);
        btnEditDob = findViewById(R.id.btnEditDob);
        btnCurrentJob = findViewById(R.id.btnCurrentJob);
        btnEditGender = findViewById(R.id.btnEditGender);

        btnEditName.setOnClickListener(view ->
                showEditDialog("Edit Name", "Enter your name", txtUsername.getText().toString(), txtUsername, "UserName"));

        btnEditDob.setOnClickListener(view ->
                showEditDialog("Edit Date of Birth", "Enter your age", txtDob.getText().toString(), txtDob, "Dob"));

        btnCurrentJob.setOnClickListener(view ->
                showEditDialog("Edit CurrentJob", "Enter your Current Job", txtCurrentJob.getText().toString(), txtCurrentJob, "CurrentJob"));

        btnEditGender.setOnClickListener(view ->
                showEditDialog("Edit Gender", "Enter your gender", txtGender.getText().toString(), txtGender, "Gender"));

    }

    private void loadUserProfile(String userId) {
        List<String> userData = dbHelper.getFirstDetails("Users", "id = ?", new String[]{userId}, new String[]{"UserName", "Age", "CurrentJob", "Email"});

        if (userData.size() >= 4) {
            txtUsername.setText(userData.get(0));
            txtDob.setText(userData.get(1));
            txtCurrentJob.setText(userData.get(2));
            txtGender.setText(userData.get(3));
        } else {
            Toast.makeText(this, "Failed to load user data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEditDialog(String title, String hint, String currentValue, TextView textView, String dbColumn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit, null);
        builder.setView(dialogView);

        EditText editText = dialogView.findViewById(R.id.editText);
        editText.setHint(hint);

        editText.setText(currentValue);

        builder.setTitle(title)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newValue = editText.getText().toString().trim();
                    if (!newValue.isEmpty()) {
                        textView.setText(newValue);
                        //updateUserData(dbColumn, newValue);
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
