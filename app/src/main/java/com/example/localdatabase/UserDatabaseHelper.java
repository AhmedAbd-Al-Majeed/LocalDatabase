package com.example.localdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
/**
 * Database helper for localDataBase app. Manages database creation and version management.
 */
public class UserDatabaseHelper extends SQLiteOpenHelper {

    /** Name of the database file */
    public static final String DATABASE_NAME = "users.db";
    /** Name of database table for pets */
    public static final String TABLE_NAME = "users";
    /**
     * Unique ID number for the user (only for use in the database table).
     * Type: INTEGER
     */
    public static final String _ID= "ID";
    /**
     * Name of the User.
     * Type: TEXT
     */
    public static final String COLUMN_NAME = "NAME";
    /**
     * age of the User.
     * Type: INTEGER
     */
    public static final String COLUMN_AGE = "AGE";
    /**
     * job title of the User.
     * Type: TEXT
     */
    public static final String COLUMN_JOBTITLE = "JOBTITLE";
    /**
     * gender of the User.
     * Type: TEXT
     */
    public static final String COLUMN_GENDER = "GENDER";

    /**
     * Constructs a new instance of {@link UserDatabaseHelper}.
     * @param context of the app
     */
    public UserDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a String that contains the SQL statement to create the users table
        String SQL_CREATE_USER_TABLE =  "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL , "
                + COLUMN_AGE + " TEXT , "
                + COLUMN_JOBTITLE + " TEXT , "
                + COLUMN_GENDER + " TEXT );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_USER_TABLE);


    }
    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }

    //
    public boolean addData(String name, String age, String jobTitle, String gender){
        // to be able to write on the database
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a ContentValues object where column names are the keys,
        // and user's attributes are the values.
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME,name);
        contentValues.put(COLUMN_AGE,age);
        contentValues.put(COLUMN_JOBTITLE, jobTitle);
        contentValues.put(COLUMN_GENDER, gender);

        //save the insert method return value in result
        long result  = db.insert(TABLE_NAME, null, contentValues);

        //if statement to make sure that the data was successfully inserted.
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    // select all data from the table
    // returns a cursor
    public Cursor showData(){
        // to be able to read from the database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    //update the values of specific user's ID
    public boolean updateData(String id, String name,String age, String jobTitle, String gender){
        // to be able to write on the database
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a ContentValues object where column names are the keys,
        // and user's attributes are the values.
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID,id);
        contentValues.put(COLUMN_NAME,name);
        contentValues.put(COLUMN_AGE,age);
        contentValues.put(COLUMN_JOBTITLE,jobTitle);
        contentValues.put(COLUMN_GENDER,gender);
        //update the values in the table depending on the ID of the user
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});
        return true;
    }


    //delete the values of specific user's ID
    public Integer deleteData(String id){
        // to be able to write on the database
        SQLiteDatabase db = this.getWritableDatabase();
        //delete the values in the table depending on the ID of the user
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

    //delete all data in the table's database
    public void deleteAll()
    {
        // to be able to write on the database
        SQLiteDatabase db = this.getWritableDatabase();
        //delete all data
        db.delete(TABLE_NAME,null,null);
        db.execSQL("delete FROM "+ TABLE_NAME);
        //close the database
        // this will prevent multiple manipulations to the database and save your application from a potential crash
        db.close();
    }
    //get all data to View in the ListView
    public ArrayList<User> getAllData(){

        ArrayList<User> arrayList= new ArrayList<>();
        // to be able to read from the database
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        //move the cursor through the table to get the data from it
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String age = cursor.getString(2);
            String jobTitle = cursor.getString(3);
            String gender = cursor.getString(4);
            User user = new User(id, name, age, jobTitle, gender);
            if (name == null) {
                throw new IllegalArgumentException("Pet requires a name");
            }else {
                arrayList.add(user);
            }
        }
        return arrayList;
    }

}
