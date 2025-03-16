package com.example.onebitmoblie.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.onebitmoblie.Data.ViewModels.ScheduleModels;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    public interface FirebaseCallback<T> {
        void onCallback(T result);
    }

    public void getSchedulesByUserId(String userId, FirebaseCallback<List<ScheduleModels>> callback) {
        db.getReference("scheduling").orderByChild("userId").equalTo(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<ScheduleModels> list = new ArrayList<>();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            ScheduleModels schedule = data.getValue(ScheduleModels.class);
                            if (schedule != null && !schedule.isDeleted()) {
                                list.add(schedule);
                            }
                        }
                        callback.onCallback(list);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("DB_ERROR", "Failed to fetch schedules", error.toException());
                    }
                });
    }
}