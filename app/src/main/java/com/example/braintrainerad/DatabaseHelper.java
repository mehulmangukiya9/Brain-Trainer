package com.example.braintrainerad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Player Detail.db";
    public static final String TABLE_NAME = "playertable";
    public  static final String COL_1 ="id";
    public static final String COL_2 ="Player";
    public  static final String COL_3 = "Score";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT, PLAYER TEXT, SCORE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
    }

    public boolean insert( String player, int score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, player);
        contentValues.put(COL_3, score);
        final long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getScore(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select * FROM "+ TABLE_NAME, null);
        return c;
    }
}