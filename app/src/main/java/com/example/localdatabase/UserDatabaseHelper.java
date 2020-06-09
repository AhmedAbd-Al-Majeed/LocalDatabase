package com.example.localdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "users.db";
    public static final String TABLE_NAME = "users_table";

    public static final String _ID= "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_AGE = "AGE";
    public static final String COLUMN_JOBTITLE = "JOBTITLE";
    public static final String COLUMN_GENDER = "GENDER";



    public UserDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a String that contains the SQL statement to create the users table
        String SQL_CREATE_USER_TABLE =  "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_AGE + " INTEGER , "
                + COLUMN_JOBTITLE + " TEXT , "
                + COLUMN_GENDER + " TEXT );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS  " + TABLE_NAME);
    onCreate(db);
    }

    public boolean addData(String name, String age, String jobTitle, String gender){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME,name);
        contentValues.put(COLUMN_AGE,age);
        contentValues.put(COLUMN_JOBTITLE, jobTitle);
        contentValues.put(COLUMN_GENDER, gender);

        long result  = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

}
