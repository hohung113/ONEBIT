package com.example.onebitmoblie.login_register;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.onebitmoblie.R;
import com.example.onebitmoblie.databaseconfig.DbHelper;

import java.io.IOException;
import java.util.List;

public class LoginActivity extends Activity {
    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView signupText;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.signupText);

        try {
            dbHelper = new DbHelper(this, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loginButton.setOnClickListener(v -> attemptLogin());
        //signupText.setOnClickListener(v -> goToRegister());
    }

    private void attemptLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        String hashedPassword = password;
        List<String> user = dbHelper.getFirst("Users", "Email = '" + email + "' AND PasswordHash = '" + hashedPassword + "' AND IsDeleted = 0", new String[]{"Id"});
        int countUser = user.size();
        if (checkUser(email, hashedPassword)) {
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkUser(String email, String hashedPassword) {
        String query = "SELECT Id FROM Users WHERE Email = ? AND PasswordHash = ? AND IsDeleted = 0";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, new String[]{email, hashedPassword});

        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }
}