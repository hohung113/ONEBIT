package com.example.onebitmoblie.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.onebitmoblie.Data.DatabaseEntities.NotificationLines;
import com.example.onebitmoblie.databaseconfig.DbHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationLineDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public NotificationLineDAO(Context context) throws IOException {
        dbHelper = new DbHelper(context, null);
        db = dbHelper.getWritableDatabase();
    }

    public long insertNotificationLine(NotificationLines notificationLine) {
        ContentValues values = new ContentValues();
        values.put("isDeleted", notificationLine.isDeleted() ? 1 : 0);
        values.put("createdAt", notificationLine.getCreatedAt());
        values.put("modifiedAt", notificationLine.getModifiedAt());
        values.put("modifiedBy", notificationLine.getModifiedBy());
        values.put("notificationId", notificationLine.getNotificationId());
        values.put("schedulingId", notificationLine.getSchedulingId());
        values.put("toUserId", notificationLine.getToUserId());
        values.put("isRead", notificationLine.getIsRead() ? 1 : 0);  // Chuyển Boolean thành int

        return db.insert("NotificationLines", null, values);
    }

    public int markAsRead(String id){
        ContentValues values = new ContentValues();
        values.put("isRead",1);
        return db.update("NotificationLines", values, "id=?", new String[]{id});
    }
    public NotificationLines getByNotificationId(String notificationId) {
        NotificationLines notificationLine = null;
        Cursor cursor = db.query("NotificationLines", null, "notificationId = ? AND isDeleted = 0",
                new String[]{notificationId}, null, null, "createdAt DESC", "1"); // Giới hạn 1 kết quả

        if (cursor.moveToFirst()) {
            notificationLine = new NotificationLines(
                    cursor.getString(0),
                    cursor.getInt(5) == 1,  // Chuyển int -> boolean
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4) == 1  // Chuyển int -> boolean
            );
        }
        cursor.close();
        return notificationLine;
    }

    public List<NotificationLines> getByToUserId(String toUserId) {
        List<NotificationLines> list = new ArrayList<>();
        Cursor cursor = db.query("NotificationLines", null, "toUserId = ? AND isDeleted = 0",
                new String[]{toUserId}, null, null, "createdAt DESC");

        if (cursor.moveToFirst()) {
            do {
                NotificationLines notificationLine = new NotificationLines(
                        cursor.getString(0),
                        cursor.getInt(5) == 1,  // Chuyển int -> boolean
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4) == 1  // Chuyển int -> boolean
                );
                list.add(notificationLine);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
