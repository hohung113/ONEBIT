package com.example.onebitmoblie.login_register;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onebitmoblie.R;
import com.example.onebitmoblie.databaseconfig.DbHelper;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtName, edtCurrentJob, edtEmail, edtPassword, edtConfirmPassword;
    private Button btnRegister;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Bind views
        edtName = findViewById(R.id.edt_name);
        edtCurrentJob = findViewById(R.id.edt_current_job);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);
        btnRegister = findViewById(R.id.btn_register);

        // Set up button click listener
        btnRegister.setOnClickListener(v -> handleRegister());
    }

    private void handleRegister() {
        // Get user input
        String name = edtName.getText().toString().trim();
        String currentJob = edtCurrentJob.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        // Validate input
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

        if (TextUtils.isEmpty(password) || !PASSWORD_PATTERN.matcher(password).matches()) {
            showAlert("Password must be at least 6 characters, include one uppercase letter and one special character.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Passwords do not match.");
            return;
        }

        try {
            DbHelper dbHelper = new DbHelper(this, null);

            // Check if email already exists
            if (dbHelper.isEmailExists(email)) {
                showAlert("Email is already registered.");
                return;
            }

            // Save user to database
            String passwordHash = Integer.toString(password.hashCode());
            dbHelper.insertUser(name, email, passwordHash);

            Toast.makeText(this, "Registration successful!", Toast.LENGTH_LONG).show();

            // Clear fields
            edtName.setText("");
            edtCurrentJob.setText("");
            edtEmail.setText("");
            edtPassword.setText("");
            edtConfirmPassword.setText("");
        } catch (Exception e) {
            showAlert("Registration failed: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Validation Error")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
