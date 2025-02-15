package com.example.onebitmoblie.databaseconfig;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_PATH = "";
    private static final String DATABASE_NAME = "onebit.db";

    private Context context;
    SQLiteDatabase mDatabase;

    public DbHelper(Context context, SQLiteDatabase.CursorFactory factory) throws IOException {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        DATABASE_PATH = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
        this.context = context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            open();
        } else {
            createdatabase();
        }
    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if (!dbexist) {
            if (mDatabase != null && !mDatabase.isOpen())
                this.open();
            this.getReadableDatabase();
            this.close();
            try {
                copydatabase();
                this.getReadableDatabase();
                if (mDatabase != null && !mDatabase.isOpen())
                    this.open();
            } catch (IOException e) {
                throw e;
            }
        }
    }

    private void copydatabase() throws IOException {
        InputStream myinput = context.getAssets().open(DATABASE_NAME);

        String outfilename = DATABASE_PATH;

        OutputStream myoutput = new FileOutputStream(outfilename);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }

        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    private boolean checkdatabase() {
        boolean checkdb = false;
        try {
            File dbfile = new File(DATABASE_PATH);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    public void open() {
        String mypath = DATABASE_PATH;
        mDatabase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if (mDatabase != null) {
            mDatabase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mDatabase = db;
        try {
            this.createdatabase();
            open();
        } catch (Exception ex) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void insertUser(String name, String email, String password) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            // Create Users table if not exists
            createUsersTable();

            ContentValues values = new ContentValues();
            values.put("Id", java.util.UUID.randomUUID().toString());
            values.put("Name", name);
            values.put("Email", email);
            values.put("Password", password);

            long result = db.insert("Users", null, values);

            if (result == -1) {
                Log.e("DbHelper", "Failed to insert user");
            } else {
                Log.d("DbHelper", "User inserted successfully");
            }
        } catch (Exception e) {
            Log.e("DbHelper", "Error inserting user: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM Users WHERE Email = ?", new String[]{email});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public void createUsersTable() {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase(); // Open database connection
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Users (" +
                    "Id TEXT PRIMARY KEY, " +
                    "Name TEXT NOT NULL, " +
                    "Email TEXT NOT NULL UNIQUE, " +
                    "Password TEXT NOT NULL)";
            db.execSQL(createTableQuery);
            Log.d("DbHelper", "Users table created successfully.");
        } catch (Exception e) {
            Log.e("DbHelper", "Error creating Users table: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public List<List> loadDataHandler(String TABLE_NAME, String FILTER, String[] SELECTION_ARGS) {
        List<List> results = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        if (SELECTION_ARGS != null) {
            query = "SELECT " + String.join(", ", SELECTION_ARGS) + " FROM " + TABLE_NAME;
        }
        if (FILTER != null) {
            query += " WHERE " + FILTER;
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                List<String> result = new ArrayList<>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    result.add(cursor.getString(i));
                }
                ;
                results.add(result);
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        db.close();
        return results;
    }

    public void insertDataHandler(String TABLE_NAME, String[] COLUMNS, String[] VALUES) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + TABLE_NAME;
        if (COLUMNS != null){
            query += " (" + String.join(", ", COLUMNS) + ")";
        }
        query += " VALUES (";
        query += String.join(", ", VALUES);
        query += ")";
        db.execSQL(query);
        db.close();
    }

    public List<String> getLast(String TABLE_NAME, String FILTER, String[] SELECTION_ARGS) {
        List<String> results = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        if (SELECTION_ARGS != null) {
            query = "SELECT " + String.join(", ", SELECTION_ARGS) + " FROM " + TABLE_NAME;
        }
        if (FILTER != null) {
            query += " WHERE " + FILTER;
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            cursor.moveToLast();
            if (cursor.getCount() == 0) {
                return null;
            }
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                results.add(cursor.getString(i));
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        db.close();
        return results;
    }

    public List<String> getFirst(String TABLE_NAME, String FILTER, String[] SELECTION_ARGS) {
        List<String> results = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;
        if (SELECTION_ARGS != null) {
            query = "SELECT " + String.join(", ", SELECTION_ARGS) + " FROM " + TABLE_NAME;
        }
        if (FILTER != null) {
            query += " WHERE " + FILTER;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                results.add(cursor.getString(i));
            }
        } catch (Exception e) {
            Log.d("Errorrrrrr",  e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        db.close();
        return results;
    }
}
