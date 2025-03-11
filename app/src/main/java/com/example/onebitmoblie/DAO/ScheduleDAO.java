package com.example.onebitmoblie.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.onebitmoblie.Data.ViewModels.ScheduleModels;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    private DatabaseReference mDatabase;


    public ScheduleDAO() {

    }


    public void getUserSchedules(final ScheduleCallback callback) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userSchedulesRef = mDatabase.child("scheduling");

        userSchedulesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("FirebaseDebug", "DataSnapshot exists: " + snapshot.exists());

                if (!snapshot.exists()) {
                    Log.d("FirebaseDebug", "Không có dữ liệu trong scheduling");
                    callback.onSuccess(new ArrayList<>());
                    return;
                }
                List<ScheduleModels> scheduleList = new ArrayList<>();
                for (DataSnapshot scheduleSnapshot : snapshot.getChildren()) {
                    ScheduleModels schedule = scheduleSnapshot.getValue(ScheduleModels.class);

                    if (schedule != null) {
                        Log.d("FirebaseDebug", "Schedule loaded: " + schedule.getTitle());
                        scheduleList.add(schedule);
                    } else {
                        Log.w("FirebaseDebug", "Schedule bị null khi ánh xạ từ Firebase");
                    }
                }

                Log.d("FirebaseDebug", "Lấy được tổng cộng: " + scheduleList.size() + " lịch trình");
                callback.onSuccess(scheduleList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Lỗi Firebase: " + error.getMessage());
                callback.onError(error.getMessage());
            }
        });
    }




//    public void getUserSchedules(String userId, final ScheduleCallback callback) {
//        DatabaseReference userSchedulesRef = mDatabase.child("scheduling");
//
//        userSchedulesRef.orderByChild("id").equalTo(userId)  // 🔹 Filter by user ID
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        List<ScheduleModels> scheduleList = new ArrayList<>();
//                        for (DataSnapshot scheduleSnapshot : snapshot.getChildren()) {
//                            Schedule schedule = scheduleSnapshot.getValue(Schedule.class);
//                            if (schedule != null) {
//                                scheduleList.add(new ScheduleModels(
//                                        schedule.getDescription(),  // 🔹 Map correctly
//                                        schedule.getFromDate(),
//                                        50 // Default progress, modify if needed
//                                ));
//                            }
//                        }
//                        callback.onSuccess(scheduleList);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        callback.onError(error.getMessage());
//                    }
//                });
//    }



    public void listenForScheduleUpdates(String userId, final ScheduleCallback callback) {
        DatabaseReference userSchedulesRef = mDatabase.child("users").child(userId).child("scheduling");

        userSchedulesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ScheduleModels> scheduleList = new ArrayList<>();
                for (DataSnapshot scheduleSnapshot : snapshot.getChildren()) {
                    ScheduleModels schedule = scheduleSnapshot.getValue(ScheduleModels.class);
                    if (schedule != null) {
                        scheduleList.add(schedule);
                    }
                }
                callback.onSuccess(scheduleList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }


    public void addSchedule(String userId, Schedule schedule) {
        DatabaseReference userSchedulesRef = mDatabase.child("users").child(userId).child("scheduling");

        String scheduleId = userSchedulesRef.push().getKey();
        if (scheduleId != null) {
           // schedule.setId(scheduleId);
            userSchedulesRef.child(scheduleId).setValue(schedule)
                    .addOnSuccessListener(aVoid -> Log.d("Firebase", "Lịch trình đã được thêm"))
                    .addOnFailureListener(e -> Log.e("Firebase", "Lỗi khi thêm lịch trình", e));
        }
    }

    public void deleteSchedule(String userId, String scheduleId) {
        DatabaseReference scheduleRef = mDatabase.child("users").child(userId).child("scheduling").child(scheduleId);
        scheduleRef.removeValue()
                .addOnSuccessListener(aVoid -> Log.d("Firebase", "Lịch trình đã bị xóa"))
                .addOnFailureListener(e -> Log.e("Firebase", "Lỗi khi xóa lịch trình", e));
    }


    public interface ScheduleCallback {
        void onSuccess(List<ScheduleModels> schedules);
        void onError(String errorMessage);
    }
}
