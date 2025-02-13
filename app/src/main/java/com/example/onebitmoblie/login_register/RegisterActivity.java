package com.example.onebitmoblie.login_register;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onebitmoblie.R;
import com.example.onebitmoblie.databaseconfig.DbHelper;

import java.util.regex.Pattern;

public class RegisterActivity extends Activity {

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

        Button btnRegister = findViewById(R.id.btn_register);

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

        if (TextUtils.isEmpty(password) || !PASSWORD_PATTERN.matcher(password).matches()) {
            showAlert("Password must be at least 6 characters, include one uppercase letter and one special character.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Passwords do not match.");
            return;
        }

        try (DbHelper dbHelper = new DbHelper(this, null)) {
            if (dbHelper.isEmailExists(email)) {
                showAlert("Email is already registered.");
                return;
            }

            String passwordHash = Integer.toString(password.hashCode());
            dbHelper.insertUser(name, email, passwordHash);

            Toast.makeText(this, "Registration successful!", Toast.LENGTH_LONG).show();

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
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Validation Error")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}
