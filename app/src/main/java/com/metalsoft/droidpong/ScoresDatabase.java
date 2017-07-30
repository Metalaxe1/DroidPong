package com.metalsoft.droidpong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anthony Ratliff on 7/24/2017.
 */

public class ScoresDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "PongScores.db";
    public static final String SINGLE_SCORE_TABLE = "single_score_table";
    public static final String USER_NAME = "name";
    public static final String USER_SCORE = "score";
    public static final String EVENT_DATE = "date";


    public ScoresDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SINGLE_SCORE_TABLE + " (" + USER_NAME + " TEXT, " + USER_SCORE + " DOUBLE, " + EVENT_DATE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + SINGLE_SCORE_TABLE);
    }

    public boolean addNewScore(String name, double score, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, name);
        contentValues.put(USER_SCORE, score);
        contentValues.put(EVENT_DATE, date);
        long result = db.insert(SINGLE_SCORE_TABLE, null, contentValues);
        return result != -1;
    }

    public Cursor getAllScores(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + SINGLE_SCORE_TABLE, null);
    }
}
