package com.example.onebitmoblie.login_register;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.onebitmoblie.R;
import com.example.onebitmoblie.common.PasswordHelper;
import com.example.onebitmoblie.common.SessionManager;
import com.example.onebitmoblie.databaseconfig.DbHelper;
import com.example.onebitmoblie.homepage.HomeActivity;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class LoginActivity extends Activity {
    private EditText emailInput, passwordInput;
    private ImageButton toggleVisibilityBtn;
    private DbHelper dbHelper;
    private boolean isPassVisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginButton);
        TextView signupText = findViewById(R.id.signupText);
        toggleVisibilityBtn =  findViewById(R.id.togglePasswordVisibility);

        try {
            dbHelper = new DbHelper(this, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loginButton.setOnClickListener(v -> attemptLogin());
        toggleVisibilityBtn.setOnClickListener(v->togglePasswordVisibility());
        signupText.setOnClickListener(v -> goToRegister());
    }

    public void warningPopup(Context context, String message) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast, null);

        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void goToRegister()
    {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    private void togglePasswordVisibility()
    {
        if(isPassVisible)
        {
            passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
            toggleVisibilityBtn.setImageResource(R.drawable.ic_visibility_off);
        }
        else
        {
            passwordInput.setTransformationMethod(null);
            toggleVisibilityBtn.setImageResource(R.drawable.ic_visibility);
        }
        isPassVisible = !isPassVisible;

        passwordInput.setSelection(passwordInput.getText().length());
    }
    private void attemptLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        String emptyWarning = "";

        if (email.isEmpty() || password.isEmpty()) {
            emptyWarning = "Please do not empty";
            if(email.isEmpty()&&password.isEmpty()) emptyWarning += " email and password";
            else if(email.isEmpty()) emptyWarning += " email";
            else emptyWarning += " password";
            warningPopup(this,emptyWarning);
            return;
        }

        //String hashedPassword = /*Integer.toString(password.hashCode());*/password;
        String hashedPassword = null;
        try {
            hashedPassword = PasswordHelper.hashPasswordMD5(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        List<String> user = dbHelper.getFirst("Users", "Email = '" + email + "' AND PasswordHash = '" + hashedPassword + "' AND IsDeleted = 0", new String[]{"Id"});

        if (checkUser(email, hashedPassword)) {
            Toast.makeText(this, "Login successfully!", Toast.LENGTH_SHORT).show();
            new SessionManager(this).saveUserData("test","test-id-101");
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            warningPopup(this, "Email or password incorrect!");
        }

    }

    private boolean checkUser(String email, String hashedPassword) {
        String query = "SELECT Id FROM Users WHERE Email = ? AND PasswordHash = ? AND IsDeleted = 0";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, new String[]{email, hashedPassword});

        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public void Logout()
    {
        new SessionManager(this).clearData();
        startActivity(new Intent(this, LoginActivity.class));
    }
}