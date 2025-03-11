package com.example.onebitmoblie.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.onebitmoblie.Data.DatabaseEntities.Notifications;
import com.example.onebitmoblie.Data.NotificationType;
import com.example.onebitmoblie.databaseconfig.DbHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    private DatabaseReference dbRef;

    public NotificationDAO() {
        dbRef = FirebaseDatabase.getInstance().getReference("notifications");
    }

    public void insertNotification(Notifications notification) {
        String id = dbRef.push().getKey(); // Tạo ID tự động
        if (id != null) {
            notification.setId(id);
            dbRef.child(id).setValue(notification)
                    .addOnSuccessListener(aVoid -> Log.d("DB_SUCCESS", "Insert successful: " + id))
                    .addOnFailureListener(e -> Log.e("DB_ERROR", "Insert failed", e));
        }
    }

    public void getAllNotifications(final FirebaseCallback<List<Notifications>> callback) {
        dbRef.orderByChild("createdAt").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Notifications> notifications = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Notifications notification = data.getValue(Notifications.class);
                    if (notification != null && !notification.isDeleted()) {
                        notifications.add(notification);
                    }
                }
                callback.onCallback(notifications);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DB_ERROR", "Failed to fetch notifications", error.toException());
            }
        });
    }
    public void getByNotificationId(String notificationId, FirebaseCallback<Notifications> callback) {
        DatabaseReference notifRef = dbRef.child(notificationId); // ✅ Sửa lại đường dẫn đúng
        notifRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Notifications notif = snapshot.getValue(Notifications.class);
                    callback.onCallback(notif);
                } else {
                    Log.e("FIREBASE", "Không tìm thấy notificationId: " + notificationId);
                    callback.onCallback(null);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FIREBASE_ERROR", "Lỗi khi lấy dữ liệu: " + error.getMessage());
                callback.onCallback(null);
            }
        });
    }


    public interface FirebaseCallback<T> {
        void onCallback(T data);
    }

}
