package com.example.onebitmoblie.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.onebitmoblie.Data.ViewModels.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDAO {
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    public interface FirebaseCallback<T> {
        void onCallback(T result);
    }

    public void getUserByEmail(String email, FirebaseCallback<UserModel> callback) {
        db.getReference("users").orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            UserModel user = data.getValue(UserModel.class);
                            callback.onCallback(user);
                            return;
                        }
                        callback.onCallback(null);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("DB_ERROR", "Failed to fetch user", error.toException());
                        callback.onCallback(null);
                    }
                });
    }
}
