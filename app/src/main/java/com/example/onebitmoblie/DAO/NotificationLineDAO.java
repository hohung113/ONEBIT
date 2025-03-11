package com.example.onebitmoblie.DAO;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.onebitmoblie.Data.DatabaseEntities.NotificationLines;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class NotificationLineDAO {
    private DatabaseReference dbRef;

    public NotificationLineDAO() {
        dbRef = FirebaseDatabase.getInstance().getReference("notificationLines");
    }

    public void insertNotificationLine(NotificationLines notificationLine) {
        String id = dbRef.push().getKey(); // Tạo ID tự động
        if (id != null) {
            notificationLine.setId(id);
            dbRef.child(id).setValue(notificationLine)
                    .addOnSuccessListener(aVoid -> Log.d("DB_SUCCESS", "Insert successful: " + id))
                    .addOnFailureListener(e -> Log.e("DB_ERROR", "Insert failed", e));
        }
    }

    public void markAsRead(String id) {
        dbRef.child(id).child("isRead").setValue(true)
                .addOnSuccessListener(aVoid -> Log.d("DB_SUCCESS", "Marked as read: " + id))
                .addOnFailureListener(e -> Log.e("DB_ERROR", "Update failed", e));
    }

    public void getAllNotificationLines(FirebaseCallback<List<NotificationLines>> callback) {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("FIREBASE", "onDataChange được gọi! Số lượng: " + snapshot.getChildrenCount());
                List<NotificationLines> list = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    NotificationLines notificationLine = data.getValue(NotificationLines.class);
                    if (notificationLine != null) {
                        list.add(notificationLine);
                    }
                }
                callback.onCallback(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FIREBASE_ERROR", "Lỗi khi lấy dữ liệu: " + error.getMessage());
            }
        });
    }


    public void getByNotificationId(String notificationId, FirebaseCallback<NotificationLines> callback) {
        dbRef.orderByChild("notificationId").equalTo(notificationId).limitToFirst(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            NotificationLines notificationLine = data.getValue(NotificationLines.class);
                            if (notificationLine != null && !notificationLine.isDeleted()) {
                                callback.onCallback(notificationLine);
                                return;
                            }
                        }
                        callback.onCallback(null);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("DB_ERROR", "Failed to fetch notification line", error.toException());
                    }
                });
    }

    public void getByToUserId(String toUserId, FirebaseCallback<List<NotificationLines>> callback) {
        dbRef.orderByChild("toUserId").equalTo(toUserId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<NotificationLines> list = new ArrayList<>();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            NotificationLines notificationLine = data.getValue(NotificationLines.class);
                            if (notificationLine != null && !notificationLine.isDeleted()) {
                                list.add(notificationLine);
                            }
                        }
                        callback.onCallback(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("DB_ERROR", "Failed to fetch notification lines", error.toException());
                    }
                });
    }

    public interface FirebaseCallback<T> {
        void onCallback(T result);
    }
}
