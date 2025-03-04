package com.example.onebitmoblie.databaseconfig;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.onebitmoblie.Data.DatabaseEntities.Statistics;
import com.example.onebitmoblie.Data.DatabaseEntities.TableInfo;
import com.example.onebitmoblie.Data.DatabaseEntities.Users;
import com.example.onebitmoblie.Data.Role;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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
        Log.d("DbHelper", "Database Path: " + DATABASE_PATH);

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

    public void insertUser(String name, int age, String uName, String currentJob, String email, String password, int role) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("UserName", uName);
            values.put("FullName", name);
            values.put("Age", Math.max(age, 0));
            values.put("CurrentJob", currentJob);
            values.put("Email", email);
            values.put("PasswordHash", password);
            values.put("Role", Math.max(role, 0));
            values.put("IsDeleted", 0);
            values.put("CreatedBy", "System");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            values.put("Created_at", sdf.format(new Date()));
            values.put("Modified_at", sdf.format(new Date()));

            long result = -1;
            try {
                result = db.insertOrThrow("Users", null, values);
            } catch (SQLiteConstraintException e) {
                Log.e("DbHelper", "Lỗi ràng buộc (UNIQUE, CHECK, NOT NULL): " + e.getMessage());
            } catch (SQLiteException e) {
                Log.e("DbHelper", "Lỗi SQLite: " + e.getMessage());
            } catch (Exception e) {
                Log.e("DbHelper", "Lỗi khác: " + e.getMessage());
            }
        } catch (Exception e) {
            Log.e("DbHelper", "Lỗi khi chèn dữ liệu: " + e.getMessage(), e);
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
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TABLE_NAME;
            if (SELECTION_ARGS != null && SELECTION_ARGS.length > 0) {
                query = "SELECT " + String.join(", ", SELECTION_ARGS) + " FROM " + TABLE_NAME;
            }
            if (FILTER != null) {
                query += " WHERE " + FILTER;
            }

            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        results.add(cursor.getString(i));
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Errorrrrrr", "Query failed: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return results;
    }
    public List<String> getFirstDetails(String TABLE_NAME, String FILTER, String[] FILTER_ARGS, String[] SELECTION_ARGS) {
        List<String> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT " + String.join(", ", SELECTION_ARGS) + " FROM " + TABLE_NAME + " WHERE " + FILTER;
            cursor = db.rawQuery(query, FILTER_ARGS);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        results.add(cursor.getString(i));
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DB_ERROR", "Query failed: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return results;
    }


    public void syncDataToFirebase() {
        List<TableInfo> tables = new ArrayList<>();
        tables.add(new TableInfo("Users", new String[]{"id","userName", "fullName", "passwordHash", "age","email","currentJob","role","isDeleted","createdAt","modifiedAt","modifiedBy"}));
//        tables.add(new TableInfo("WorkShift", new String[]{"ShiftID", "ShiftName", "StartTime", "EndTime"}));
//        tables.add(new TableInfo("Employee", new String[]{"EmployeeID", "EmployeeName", "Phone", "Email"}));
//        tables.add(new TableInfo("Account", new String[]{"AccountID", "Passwordd", "Email", "EmployeeID"}));
//        tables.add(new TableInfo("LeaveType", new String[]{"LeaveTypeID", "LeaveTypeName"}));
//        tables.add(new TableInfo("LeaveRequest", new String[]{"LeaveID", "CreatedTime", "Status", "LeaveTypeID", "EmployeeID", "LeaveStartTime", "LeaveEndTime", "Reason","CountShift"}));
//        tables.add(new TableInfo("Attendance", new String[]{"AttendanceID", "CreatedTime", "AttendanceType", "EmployeeID", "ShiftID", "PlaceID", "Latitude", "Longitude"}));
        tables.add(new TableInfo("Statistics", new String[]{"id", "userId", "startTimeToReport", "endTimeToReport","contentReport","isDeleted","createdAt","modifiedAt","modifiedBy"}));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        SQLiteDatabase db = this.getReadableDatabase();

        for (TableInfo table : tables) {
            String query = "SELECT * FROM " + table.tableName;
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    // Lấy dữ liệu từ Cursor
                    String[] values = new String[table.columnNames.length];
                    boolean validRow = true;

                    for (int i = 0; i < table.columnNames.length; i++) {
                        int columnIndex = cursor.getColumnIndex(table.columnNames[i]);
                        if (columnIndex != -1) {
                            values[i] = cursor.getString(columnIndex);
                        } else {
                            validRow = false;
                            break;
                        }
                    }

                    if (validRow) {
                        switch (table.tableName) {
                            case "Users":
//                                Users users = new Users(values[0], values[1], values[2], values[3], Integer.parseInt(values[4]), values[5], values[6],Role.fromString(values[7]),Boolean.parseBoolean(values[8]),values[9],values[10],values[11]);
                                Users users = new Users(
                                        values[0],  // id
                                        Boolean.parseBoolean(values[8]), // isDeleted
                                        values[9],  // createdAt
                                        values[10], // modifiedAt
                                        values[11], // modifiedBy
                                        values[1],  // userName
                                        values[2],  // fullName
                                        values[3],  // passwordHash
                                        Integer.parseInt(values[4]),
                                        values[5],  // email
                                        values[6],  // currentJob
                                        Role.fromString(values[7])
                                );
                                databaseReference.child("users").child(values[0]).setValue(users);
                                break;
//                            case "WorkShift":
//                                WorkShift workShift = new WorkShift(values[0], values[1], values[2], values[3]);
//                                databaseReference.child("workshifts").child(values[0]).setValue(workShift);
//                                break;
//                            case "Employee":
//                                Employee employee = new Employee(values[0], values[1], values[2], values[3]);
//                                databaseReference.child("employees").child(values[0]).setValue(employee);
//                                break;
//                            case "Account":
//                                Account account = new Account(values[0], values[1], values[2], values[3]);
//                                databaseReference.child("accounts").child(values[0]).setValue(account);
//                                break;
//                            case "LeaveType":
//                                LeaveType leaveType = new LeaveType(values[0], values[1]);
//                                databaseReference.child("leavetypes").child(values[0]).setValue(leaveType);
//                                break;
//                            case "LeaveRequest":
//                                LeaveRequest leaveRequest = new LeaveRequest(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], Integer.parseInt(values[8]));
//                                databaseReference.child("leaverequests").child(values[0]).setValue(leaveRequest);
//                                break;
//                            case "Attendance":
//                                Attendance attendance = new Attendance(values[0], values[1], values[2], values[3], values[4], values[5], Double.parseDouble(values[6]), Double.parseDouble(values[7]));
//                                databaseReference.child("attendances").child(values[0]).setValue(attendance);
//                                break;
                            case "Statistics":
                                Statistics statistics = new Statistics(values[0],Boolean.parseBoolean(values[5]), values[6], values[7], (values[8]), values[1], values[2], values[3],values[4] );
                                databaseReference.child("statistics").child(values[0]).setValue(statistics);
                                break;
                        }
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }
}
