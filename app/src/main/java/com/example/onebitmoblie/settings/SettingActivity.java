package com.example.onebitmoblie.settings;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.onebitmoblie.Notification.NotificationActivity;
import com.example.onebitmoblie.R;
import com.example.onebitmoblie.homepage.HomeActivity;
import com.example.onebitmoblie.login_register.LoginActivity;
import com.example.onebitmoblie.profile.ProfileActivity;

public class SettingActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TextView logoutTextView = findViewById(R.id.logoutTextView);

        if (logoutTextView != null) {
            logoutTextView.setOnClickListener(view -> {
                Log.d("DEBUG_LOG", "showLogoutDialog() được gọi");
                showLogoutDialog();
            });
        } else {
            Log.e("SettingActivity", "logoutTextView is NULL! Kiểm tra lại activity_setting.xml");
        }

        TextView deleteAccountTextView = findViewById(R.id.deleteAccountTextView);

        if (deleteAccountTextView != null) {
            deleteAccountTextView.setOnClickListener(view -> {
                Log.d("DEBUG_LOG", "showDeleteAccountDialog() được gọi");
                showDeleteAccountDialog();
            });
        } else {
            Log.e("SettingActivity", "deleteAccountTextView is NULL! Kiểm tra lại activity_setting.xml");
        }

        TextView reportBugTextView = findViewById(R.id.reportBugTextView);

        if (reportBugTextView != null) {
            reportBugTextView.setOnClickListener(view -> {
                Log.d("DEBUG_LOG", "showReportBugDialog() được gọi");
                showReportBugDialog();
            });
        } else {
            Log.e("SettingActivity", "reportBugTextView is NULL! Kiểm tra lại activity_setting.xml");
        }

    }

    public void goBack(android.view.View view){
       onBackPressed();
    }
    public void comeProfile(android.view.View view){
        Intent intent = new Intent(SettingActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
    public void openAbout(android.view.View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
    public void goToHome(android.view.View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void goToNotification(android.view.View view) {
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void goToProfile(android.view.View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void showLogoutDialog() {
        if (!isFinishing()) {  // Kiểm tra xem Activity có bị hủy không
            runOnUiThread(() -> {
                Log.d("SettingActivity", "Hàm showLogoutDialog() đang chạy");
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("Logout")
                        .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                        .setPositiveButton("Xác nhận", (dialog, which) -> logoutUser())
                        .setNegativeButton("Hủy", null)
                        .show();
            });
        } else {
            Log.e("SettingActivity", "Activity đã bị hủy trước khi hiển thị Dialog!");
        }
    }


    private void logoutUser() {
        Log.d("SettingActivity", "User logged out");

        // Xóa dữ liệu phiên đăng nhập
        getSharedPreferences("UserPrefs", MODE_PRIVATE)
                .edit()
                .clear()
                .apply();

        // Quay về màn hình đăng nhập
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void showDeleteAccountDialog() {
        if (!isFinishing()) {
            runOnUiThread(() -> {
                Log.d("SettingActivity", "Hàm showDeleteAccountDialog() đang chạy");
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("Delete Account")
                        .setMessage("Bạn có chắc chắn muốn xóa tài khoản? Hành động này không thể hoàn tác!")
                        .setPositiveButton("Xác nhận", (dialog, which) -> deleteUserAccount())
                        .setNegativeButton("Hủy", null)
                        .show();
            });
        } else {
            Log.e("SettingActivity", "Activity đã bị hủy trước khi hiển thị Dialog!");
        }
    }

    private void deleteUserAccount() {
        Log.d("SettingActivity", "User account deleted");

        // Xóa dữ liệu người dùng trong SharedPreferences
        getSharedPreferences("UserPrefs", MODE_PRIVATE)
                .edit()
                .clear()
                .apply();

        // Chuyển về màn hình đăng nhập
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void showReportBugDialog() {
        if (!isFinishing()) {
            runOnUiThread(() -> {
                Log.d("SettingActivity", "Hàm showReportBugDialog() đang chạy");
                new AlertDialog.Builder(SettingActivity.this)
                        .setTitle("Báo lỗi")
                        .setMessage("Bạn đã hoàn thành hoạt động này hay chưa?")
                        .setPositiveButton("Xong", (dialog, which) -> Log.d("SettingActivity", "Người dùng chọn Xong"))
                        .setNegativeButton("Chưa xong", (dialog, which) -> Log.d("SettingActivity", "Người dùng chọn Chưa xong"))
                        .show();
            });
        } else {
            Log.e("SettingActivity", "Activity đã bị hủy trước khi hiển thị Dialog!");
        }
    }



}
