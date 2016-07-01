package com.rohan.loginapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Rohan Sampson on 5/24/2016.
 */
public class LoginDataBaseAdapter {
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    static final String DATABASE_CREATE = "Create table LOGIN(ID integer primary key autoincrement, USERNAME text, PASSWORD text);";
    public SQLiteDatabase db;
    private final Context context;
    private DatabaseHelper databaseHelper;

    public LoginDataBaseAdapter(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(this.context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public LoginDataBaseAdapter open() throws SQLException {
        db = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void InsertEntry(String username, String password) {
        ContentValues newValues = new ContentValues();
        newValues.put("USERNAME",username);
        newValues.put("PASSWORD",password);
        db.insert("LOGIN", null, newValues);
    }

    public int deleteEntry(String UserName){
        String where = "USERNAME=?";
        int numberOfEntriesDeleted = db.delete("LOGIN",where, new String[]{UserName});
        return numberOfEntriesDeleted;
    }

    public String getSingleEntry(String UserName){
        Cursor cursor = db.query("LOGIN", null, "USERNAME=?", new String[]{UserName},null,null,null);
        if(cursor.getCount()<1){
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;

    }

    public void updateEntry(String UserName, String Password){
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("USERNAME",UserName);
        updatedValues.put("PASSWORD",Password);
        String where = "USERNAME=?";
        db.update("LOGIN",updatedValues,where,new String[]{UserName});
    }

}
