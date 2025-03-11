package com.example.onebitmoblie.Notification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onebitmoblie.Adapter.NotificationAdapter;
import com.example.onebitmoblie.DAO.NotificationDAO;
import com.example.onebitmoblie.DAO.NotificationLineDAO;
import com.example.onebitmoblie.Data.DatabaseEntities.NotificationLines;
import com.example.onebitmoblie.Data.DatabaseEntities.Notifications;
import com.example.onebitmoblie.Data.NotificationType;
import com.example.onebitmoblie.Data.ViewModels.NotificationVM;
import com.example.onebitmoblie.R;
import com.example.onebitmoblie.common.SessionManager;
import com.example.onebitmoblie.homepage.HomeActivity;
import com.example.onebitmoblie.profile.ProfileActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView rcvNotification;
    private List<NotificationVM> notificationVMS;
    private NotificationAdapter notificationAdapter;
    Button btnHome, btnTracking ,btnProfile ;
    private NotificationDAO notificationDAO;
    private NotificationLineDAO notificationLineDAO;
    private NotificationLineDAO.FirebaseCallback<List<NotificationLines>> callbackRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);
        //setup DB
        notificationLineDAO = new NotificationLineDAO();
        notificationDAO = new NotificationDAO();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //get button back
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (toolbar.getNavigationIcon() != null) {
            int whiteColor = ContextCompat.getColor(this, R.color.white);
            toolbar.getNavigationIcon().setTint(whiteColor);
        }
        //setup data of notifications

        //setup data of notificationLine

        //set list recycle view
        rcvNotification = findViewById(R.id.recyclerView);
        notificationVMS = new ArrayList<>();
        List<NotificationLines> notificationLines = new ArrayList<>();

        callbackRef = result -> {
            notificationLines.clear();
            notificationLines.addAll(result);
            Log.d("CALLBACK", "Số phần tử nhận được: " + result.size());

            if (!notificationLines.isEmpty()) {
                AtomicInteger count = new AtomicInteger(0);  // Đếm số lần callback hoàn tất

                for (NotificationLines notificationLine : notificationLines) {
                    notificationDAO.getByNotificationId(notificationLine.getNotificationId(), notification -> {
                        NotificationVM notificationVM = new NotificationVM();
                        notificationVM.setNotificationLineId(notificationLine.getId());
                        notificationVM.setNotificationId(notificationLine.getNotificationId());
                        notificationVM.setSchedulingId(notificationLine.getSchedulingId());
                        notificationVM.setCreated(notificationLine.getIsRead());
                        Log.d("IsREAD", "check read: "+notificationLine.getIsRead());

                        if (notification != null) {
                            notificationVM.setTitle(notification.getTitle());
                            notificationVM.setContent(notification.getContent());
                            notificationVM.setNotificationType(notification.getType());
                            notificationVM.setCreatedAt(notification.getCreatedAt());
                            notificationVM.setModifiedAt(notification.getModifiedAt());
                        } else {
                            Log.d("NOTIF", "Không tìm thấy thông báo");
                        }

                        // Chỉ thêm vào danh sách sau khi dữ liệu đã được cập nhật
                        notificationVMS.add(notificationVM);

                        // Kiểm tra nếu đã xử lý xong tất cả phần tử
                        if (count.incrementAndGet() == notificationLines.size()) {
                            runOnUiThread(() -> {
                                notificationAdapter.notifyDataSetChanged();
                                Log.d("CALLBACK", "Cập nhật UI với " + notificationVMS.size() + " phần tử.");
                            });
                        }
                    });
                }
            }
        };
        SessionManager session = new SessionManager(this);
        String keyUser = session.getKeyId();
// Gọi Firebase
        //test userId ở đây, nào log dc firebase thì dán keyUser vào
        notificationLineDAO.getByToUserId("bcf32912bfc1acd8c4245461554c4cf6",callbackRef);

// Khởi tạo RecyclerView
        notificationAdapter = new NotificationAdapter(notificationVMS, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvNotification.setLayoutManager(linearLayoutManager);
        rcvNotification.setAdapter(notificationAdapter);



        //navigation
        btnHome = findViewById(R.id.btnHome);
        btnTracking = findViewById(R.id.btnTracking);
        btnProfile = findViewById(R.id.btnProfile);
        btnHome.setOnClickListener(view -> startActivity(new Intent(this, HomeActivity.class)));
        btnProfile.setOnClickListener(view -> startActivity(new Intent(this, ProfileActivity.class)));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // id mac dinh cua button back
        if (item.getItemId() == android.R.id.home){
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}