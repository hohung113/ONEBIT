package com.example.onebitmoblie.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.onebitmoblie.Data.DatabaseEntities.Notifications;
import com.example.onebitmoblie.Data.NotificationType;
import com.example.onebitmoblie.databaseconfig.DbHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public NotificationDAO(Context context) throws IOException {
        dbHelper = new DbHelper(context, null);
        db = dbHelper.getWritableDatabase();
        Log.d("DB_PATH", db.getPath());
    }

    public long insertNotification(Notifications notification) {
        long result = -1;
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Notifications'", null);
        if (cursor.getCount() > 0) {
            Log.d("DB_CHECK", "Table Notifications exists");
        } else {
            Log.d("DB_CHECK", "Table Notifications does NOT exist");
        }
        cursor.close();

        try{
            ContentValues values = new ContentValues();
            values.put("title", notification.getTitle());
            values.put("content", notification.getContent());
            values.put("type", notification.getType().getValue());
            if(notification.isDeleted() == true){
                values.put("isDeleted", 1);
            }else{
                values.put("isDeleted", 0);
            }
            values.put("createdAt", notification.getCreatedAt());
            values.put("modifiedAt", notification.getModifiedAt());
            values.put("modifiedBy", notification.getModifiedBy());

            result = db.insert("Notifications", null, values);
            if (result == -1) {
                Log.e("DB_ERROR", "Insert failed: " + notification.toString());
            } else {
                Log.d("DB_SUCCESS", "Insert successful, ID: " + result);
            }
        } catch (Exception e) {
            Log.e("DB_EXCEPTION", "Error inserting notification", e);
        }
        return result;

    }

    public List<Notifications> getAllNotifications() {
        List<Notifications> notifications = new ArrayList<>();
        Cursor cursor = db.query("Notifications",null,"isDeleted=0",null,null,null,"createdAt DESC");
        while (cursor.moveToNext()){
            Notifications notification = new Notifications();
            notification.setId(cursor.getString(0));
            notification.setTitle(cursor.getString(1));
            notification.setContent(cursor.getString(2));
            notification.setType(NotificationType.fromInt(cursor.getInt(3)));
            boolean isDeleted = cursor.getInt(4) == 1;
            notification.setDeleted(isDeleted);
            notification.setCreatedAt(cursor.getString(5));
            notification.setModifiedAt(cursor.getString(6));
            notification.setModifiedBy(cursor.getString(7));
            notifications.add(notification);

        }
        cursor.close();
        return notifications;
    }

    public Notifications getNotificationsById(String notificationId) {
        Notifications notification = new Notifications();
        Cursor cursor = db.query("Notifications", null, "id = ? AND isDeleted = 0",
                new String[]{notificationId}, null, null, "createdAt DESC", "1"); // Lấy 1 kết quả duy nhất

        if (cursor.moveToFirst()) {
            notification.setId(cursor.getString(0));
            notification.setTitle(cursor.getString(1));
            notification.setContent(cursor.getString(2));
            notification.setType(NotificationType.fromInt(cursor.getInt(3)));
            boolean isDeleted = cursor.getInt(4) == 1;
            notification.setDeleted(isDeleted);
            notification.setCreatedAt(cursor.getString(5));
            notification.setModifiedAt(cursor.getString(6));
            notification.setModifiedBy(cursor.getString(7));
        }
        cursor.close();
        return notification;
    }

    public int deleteNotification(String id){
        ContentValues values = new ContentValues();
        values.put("isDeleted", 1);
        return db.update("Notifications",values, "id=?", new String[]{id});
    }

    public void close(){
        db.close();
        dbHelper.close();
    }
}
