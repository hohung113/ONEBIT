package com.example.onebitmoblie.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.onebitmoblie.databaseconfig.DbHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public NotificationDAO(Context context) {
        try {
            dbHelper = new DbHelper(context, null);
            db = dbHelper.getReadableDatabase(); // Mở database
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllNotifications() {
        List<String> notifications = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT title FROM notifications", null);
        if (cursor.moveToFirst()) {
            do {
                notifications.add(cursor.getString(0)); // Lấy cột "title"
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notifications;
    }
}
