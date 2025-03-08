package com.example.onebitmoblie.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;



public class ProfileActivity extends Activity {
    Button btnHome, btnTracking, btnNotification, btnProfile;
    ImageButton btnFAQ;
    Button btnEditName, btnEditDob, btnCurrentJob, btnEditGender, btnAddImage;
    private DbHelper dbHelper;
    private DatabaseReference mDatabase;
    private SessionManager _sessionManager;
    private TextView txtUsername, txtDob, txtCurrentJob, txtGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profiledetail);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(false);

        try {
            mDatabase = FirebaseDatabase.getInstance().getReference();
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
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setEnabled(false);

        txtUsername = findViewById(R.id.txtUsername);
        txtDob = findViewById(R.id.txtDob);
        txtCurrentJob = findViewById(R.id.txtCurrentJob);
        txtGender = findViewById(R.id.txtEmail);

        loadUserProfileFromFirebase(userId);
        
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
                showEditDialog("Edit Name", "Enter your name", txtUsername.getText().toString(), txtUsername, "fullName"));

        btnEditDob.setOnClickListener(view ->
                showEditDialog("Edit Date of Birth", "Enter your age", txtDob.getText().toString(), txtDob, "age"));

        btnCurrentJob.setOnClickListener(view ->
                showEditDialog("Edit Current Job", "Enter your Current Job", txtCurrentJob.getText().toString(), txtCurrentJob, "currentJob"));

        btnEditGender.setOnClickListener(view ->
                showEditDialog("Edit Email", "Enter your email", txtGender.getText().toString(), txtGender, "email"));

    }

    private void loadUserProfileFromFirebase(String userId) {
        Log.d("Firebase", "Creating reference for user: " + userId);
        DatabaseReference userRef = mDatabase.child("users").child(userId);
        Log.d("Firebase", "Reference path: " + userRef.toString());

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Firebase", "onDataChange called. Data exists: " + dataSnapshot.exists());

                if (dataSnapshot.exists()) {
                    Log.d("Firebase", "Getting individual fields");

                    try {
                        String fullName = dataSnapshot.child("fullName").getValue(String.class);
                        String age = String.valueOf(dataSnapshot.child("age").getValue());
                        String currentJob = dataSnapshot.child("currentJob").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);
                        Log.d("Firebase", "Updating UI with retrieved data");
                        updateProfileUI(fullName, age, currentJob, email);
                    } catch (Exception e) {
                        Log.e("Firebase", "Error processing data: " + e.getMessage());
                        Toast.makeText(ProfileActivity.this, "Error processing data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Firebase", "No data found for user: " + userId);
                    Toast.makeText(ProfileActivity.this, "User data not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "Database error: " + databaseError.getMessage());
                Toast.makeText(ProfileActivity.this, "Failed to load user data: " + databaseError.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfileUI(String fullName, String age, String currentJob, String email) {
        runOnUiThread(() -> {
            txtUsername.setText(fullName != null ? fullName : "");
            txtDob.setText(age != null ? age : "");
            txtCurrentJob.setText(currentJob != null ? currentJob : "");
            txtGender.setText(email != null ? email : "");
            Log.d("Firebase", "UI updated successfully");
        });
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

    public void goBack(android.view.View view){
        onBackPressed();
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
                        updateUserDataInFirebase(dbColumn, newValue);
                        //updateUserData(dbColumn, newValue);
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateUserDataInFirebase(String field, String value) {
        String userId = _sessionManager.getKeyId();
        if (userId != null) {
            // Update the specific field
            mDatabase.child("users").child(userId).child(field).setValue(value)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        // Also update modifiedAt timestamp
                        mDatabase.child("users").child(userId).child("modifiedAt")
                                .setValue(java.time.OffsetDateTime.now().toString());
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(ProfileActivity.this, "Failed to update profile: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show()
                    );
        }
    }

}
