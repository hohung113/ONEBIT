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
import com.example.onebitmoblie.homepage.HomeActivity;
import com.example.onebitmoblie.profile.ProfileActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView rcvNotification;
    private List<NotificationVM> notificationVMS;
    private NotificationAdapter notificationAdapter;
    Button btnHome, btnTracking ,btnProfile ;
    private NotificationDAO notificationDAO;
    private NotificationLineDAO notificationLineDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);
        //setup DB
        try {
            notificationDAO = new NotificationDAO(this);
            notificationLineDAO = new NotificationLineDAO(this);
        } catch (IOException e) {
            e.printStackTrace();
        }


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
//        long notiId1 = notificationDAO.insertNotification(new Notifications(
//                "a1b2c3d4e5f67890abcdef1234567890",  // ID mới
//                false,
//                "2024-03-01 10:30:00",
//                "2024-03-01 12:00:00",
//                "admin",
//                "System Update",
//                "The system will undergo maintenance at midnight.",
//                NotificationType.SYSTEM
//        ));
//        long notiId2 = notificationDAO.insertNotification(new Notifications(
//                "9f8e7d6c5b4a3210abcdef0123456789",  // ID mới
//                false,  // Đã bị xóa (isDeleted = true)
//                "2024-03-03 14:45:00",
//                "2024-03-03 15:10:00",
//                "teacher",
//                "New Assignment Posted",
//                "A new assignment has been uploaded for your course.",
//                NotificationType.SCHEDULES
//        ));
        List<Notifications> notifications = notificationDAO.getAllNotifications();
        for (Notifications notification: notifications) {
            Log.d("NotificationCheck", "ID: " + notification.getId() + " | Content: " + notification.getContent());
        }
        ;
        //setup data of notificationLine

        //set list recycle view
        rcvNotification = findViewById(R.id.recyclerView);
        notificationVMS = new ArrayList<>();
//        List<NotificationLines> notificationLines = notificationLineDAO.getByToUserId("bcf32912bfc1acd8c4245461554c4cf6");
//        if(notificationLines!=null){
//            for (NotificationLines notificationLine : notificationLines){
//                Notifications notification = notificationDAO.getNotificationsById(notificationLine.getNotificationId());
//                NotificationVM notificationVM = new NotificationVM();
//                notificationVM.setNotificationId(notificationLine.getNotificationId());
//                notificationVM.setSchedulingId(notificationLine.getSchedulingId());
//                notificationVM.setTitle(notification.getTitle());
//                notificationVM.setContent(notification.getContent());
//                notificationVM.setNotificationType(notification.getType());
//                notificationVM.setCreatedAt(notification.getCreatedAt());
//                notificationVM.setModifiedAt(notification.getModifiedAt());
//                notificationVM.setCreated(notificationLine.getIsRead());
//                notificationVMS.add(notificationVM);
//            }
//
//        }


        notificationAdapter = new NotificationAdapter(notificationVMS);
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