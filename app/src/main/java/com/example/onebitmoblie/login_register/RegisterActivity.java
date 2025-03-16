package com.example.onebitmoblie.login_register;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.example.onebitmoblie.Data.DatabaseEntities.Users;
import com.example.onebitmoblie.Data.Role;
import com.example.onebitmoblie.R;
import com.example.onebitmoblie.common.PasswordHelper;
import com.example.onebitmoblie.databaseconfig.DbHelper;
import com.example.onebitmoblie.homepage.HomeActivity;
import com.example.onebitmoblie.profile.ProfileActivity;
import com.example.onebitmoblie.settings.SettingActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity {
    private DbHelper DBHelper;
    private SQLiteDatabase db;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText edtName = findViewById(R.id.edt_name);
        EditText edtCurrentJob = findViewById(R.id.edt_current_job);
        EditText edtEmail = findViewById(R.id.edt_email);
        EditText edtPassword = findViewById(R.id.edt_password);
        EditText edtConfirmPassword = findViewById(R.id.edt_confirm_password);

        ImageButton togglePassword = findViewById(R.id.togglePasswordVisibility);
        ImageButton toggleConfirmPassword = findViewById(R.id.toggleConfirmPasswordVisibility);

        Button btnRegister = findViewById(R.id.btn_register);
        TextView tvLoginRedirect = findViewById(R.id.tv_login_redirect);

        tvLoginRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Thiết lập toggle password visibility
        setupPasswordToggle(edtPassword, togglePassword);
        setupPasswordToggle(edtConfirmPassword, toggleConfirmPassword);

        // Create Users table
        try (DbHelper dbHelper = new DbHelper(this, null)) {
            dbHelper.createUsersTable();
        } catch (Exception e) {
            Toast.makeText(this, "Database setup failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        btnRegister.setOnClickListener(v -> handleRegister(
                edtName, edtCurrentJob, edtEmail, edtPassword, edtConfirmPassword
        ));
    }

    private void setupPasswordToggle(EditText editText, ImageButton toggleButton) {
        toggleButton.setOnClickListener(v -> {
            if (editText.getTransformationMethod() instanceof PasswordTransformationMethod) {
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                toggleButton.setImageResource(R.drawable.ic_visibility_off);
            } else {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                toggleButton.setImageResource(R.drawable.ic_visibility);
            }
            editText.setSelection(editText.getText().length());
        });
    }

    private void handleRegister(EditText edtName, EditText edtCurrentJob,
                                EditText edtEmail, EditText edtPassword,
                                EditText edtConfirmPassword) {
        String name = edtName.getText().toString().trim();
        String currentJob = edtCurrentJob.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            showAlert("Full Name is required.");
            return;
        }

        if (TextUtils.isEmpty(currentJob)) {
            showAlert("Current Job is required.");
            return;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showAlert("Invalid Email format.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Passwords do not match.");
            return;
        }

        if (TextUtils.isEmpty(password) || !PASSWORD_PATTERN.matcher(password).matches()) {
            showAlert("Password must be at least 6 characters, include one uppercase letter and one special character.");
            return;
        }

        try (DbHelper dbHelper = new DbHelper(this, null)) {
            if (dbHelper.isEmailExists(email)) {
                showAlert("Email is already registered.");
                return;
            }

            String passwordHash = PasswordHelper.hashPasswordMD5(password);
            String userId = java.util.UUID.randomUUID().toString();

            // Default
            int age = 10;
            int role = 1;
            boolean deleted = false;
            String createdAt = java.time.Instant.now().toString();
            String modifiedAt = createdAt;
            String modifiedBy = userId;

            String[] words = name.split("\\s+");
            String userName = words[words.length - 1];
            Users newUser = new Users(userId,deleted,createdAt,modifiedAt,modifiedBy,userName,name,passwordHash,age,email,currentJob, Role.NORMAL_USER);

            saveUserToFirebase(newUser);

        } catch (Exception e) {
            showAlert("Registration failed: " + e.getMessage());
        }
    }

    private void comeBackLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Validation Error")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void saveUserToFirebase(Users user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.child(user.getId()).setValue(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(RegisterActivity.this, "User registered successfully!", Toast.LENGTH_LONG).show();
                    comeBackLogin();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RegisterActivity.this, "Failed to register user: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

}