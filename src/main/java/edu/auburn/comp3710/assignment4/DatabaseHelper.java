package edu.auburn.comp3710.assignment4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SpendingManagement.db";
    public static final String TABLE_NAME = "Money";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 4);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int NewVersion){
        onCreate(db);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, DATE varchar(250), Amount DOUBLE, Event varchar(255))");
    }


   public void Create(String date, double amount, String event){
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues content = new ContentValues();
        content.put("Date", date);
        content.put("Amount", amount);
        content.put("Event", event);
        database.insert(TABLE_NAME,null, content);
   }
    public Cursor ReadData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return result;
    }
}