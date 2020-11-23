package com.example.coursewebapp.Database;

import android.content.ContentValues;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.coursewebapp.Models.*;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_USER);
        db.execSQL(SQL_CREATE_ENTRIES_MESSAGE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES_USER);
        db.execSQL(SQL_DELETE_ENTRIES_MESSAGE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String SQL_CREATE_ENTRIES_USER =
            "CREATE TABLE " + UserMaster.User.TABLE_NAME + " (" +
                    UserMaster.User._ID + " INTEGER PRIMARY KEY," +
                    UserMaster.User.COLUMN_NAME_NAME + " TEXT," +
                    UserMaster.User.COLUMN_NAME_PASSWORD + " TEXT," +
                    UserMaster.User.COLUMN_NAME_TYPE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_USER =
            "DROP TABLE IF EXISTS " + UserMaster.User.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES_MESSAGE =
            "CREATE TABLE " + UserMaster.Message.TABLE_NAME + " (" +
                    UserMaster.Message._ID + " INTEGER PRIMARY KEY," +
                    UserMaster.Message.COLUMN_NAME_TEACHER + " TEXT," +
                    UserMaster.Message.COLUMN_NAME_SUBJECT + " TEXT," +
                    UserMaster.Message.COLUMN_NAME_TYPE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES_MESSAGE =
            "DROP TABLE IF EXISTS " + UserMaster.Message.TABLE_NAME;

    /*
     *
     * Registration
     */

    public boolean registerUser(String mUserName, String mPassword, String mType) {

        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserMaster.User.COLUMN_NAME_NAME, mUserName);
        values.put(UserMaster.User.COLUMN_NAME_PASSWORD, mPassword);
        values.put(UserMaster.User.COLUMN_NAME_TYPE, mType);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserMaster.User.TABLE_NAME, null, values);

        if (newRowId == -1) {
            return false;
        } else {
            return true;
        }
    }

    /*
     *
     * Login validation
     *
     */

    public String checkLogin(String mUserName, String mPassword) {

        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserMaster.User.COLUMN_NAME_NAME,
                UserMaster.User.COLUMN_NAME_PASSWORD,
                UserMaster.User.COLUMN_NAME_TYPE
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserMaster.User.COLUMN_NAME_NAME + " = ? AND " + UserMaster.User.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = {mUserName, mPassword};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                UserMaster.User.COLUMN_NAME_NAME + " ASC";

        Cursor cursor = db.query(
                UserMaster.User.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List validUser = new ArrayList<>();
        while (cursor.moveToNext()) {
            long validUserID = cursor.getLong(
                    cursor.getColumnIndexOrThrow(UserMaster.User._ID));

            String validUserType = cursor.getString(
                    cursor.getColumnIndexOrThrow(UserMaster.User.COLUMN_NAME_TYPE));

            validUser.add(validUserID); //0
            validUser.add(validUserType); //1
        }
        cursor.close();



        if (validUser.isEmpty()) {
            return "No User";
        } else {
            Log.d("checkType", "checkLogin: "+validUser.get(1));
            if (validUser.get(1).equals("Teacher")){
                String UserType = "Teacher";
                return UserType;
            }
            else if (validUser.get(1).equals("Student")){
                String UserType = "Student";
                return UserType;
            }
            else{
                return "No Type";
            }
        }

    }

    /*
    *
    * insert message teacher
    *
     */

    public boolean sendMessage(String mUserName, String mSubject, String mMessage){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserMaster.Message.COLUMN_NAME_TEACHER, mUserName);
        values.put(UserMaster.Message.COLUMN_NAME_SUBJECT, mSubject);
        values.put(UserMaster.Message.COLUMN_NAME_TYPE, mMessage);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserMaster.Message.TABLE_NAME, null, values);

        if (newRowId == -1){
            return false;
        }else{
            return true;
        }

    }

    /*
    *
    * retrieve all messages
    *
     */

    public List<MessageObj> readAllMessages(){

        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                UserMaster.Message.COLUMN_NAME_TEACHER,
                UserMaster.Message.COLUMN_NAME_SUBJECT,
                UserMaster.Message.COLUMN_NAME_TYPE,

        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserMaster.Message.COLUMN_NAME_TEACHER + " = ?";
        String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                BaseColumns._ID + " ASC";

        Cursor cursor = db.query(
                UserMaster.Message.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List <MessageObj> MessageObjectsList = new ArrayList<>();
        MessageObj messageObj;
        while(cursor.moveToNext()) {
            int messageID = cursor.getInt(cursor.getColumnIndexOrThrow(UserMaster.Message._ID));
            String teacherName = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Message.COLUMN_NAME_TEACHER));
            String subject = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Message.COLUMN_NAME_SUBJECT));
            String message = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Message.COLUMN_NAME_TYPE));

            messageObj = new MessageObj(messageID, teacherName, subject, message);

            MessageObjectsList.add(messageObj);
        }
        cursor.close();

        return MessageObjectsList;



    }

}

