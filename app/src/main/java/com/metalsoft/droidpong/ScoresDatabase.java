package com.metalsoft.droidpong;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Anthony Ratliff on 7/24/2017.
 */

class ScoresDatabase extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "PongScores.db";
    private static final String SINGLE_SCORE_TABLE = "single_score_table";
    private static final String USER_NAME = "name";
    private static final String USER_SCORE = "score";
    private static final String USER_RATIO = "date";


    ScoresDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SINGLE_SCORE_TABLE + " (" + USER_NAME + " TEXT, " + USER_SCORE + " INTEGER, " + USER_RATIO + " DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + SINGLE_SCORE_TABLE);
    }

    boolean addNewScore(String name, double score, double ratio){
        if (!name.isEmpty() && score != 0 && ratio != 0) {
            int MAX_SIZE = 5;
            if (returnTotalCount() >= MAX_SIZE){
                if (score <= returnLowestScore()){
                    return false;
                }
            }
            DecimalFormat formatter = new DecimalFormat("#.0");
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(USER_NAME, name);
            contentValues.put(USER_SCORE, score);
            contentValues.put(USER_RATIO, formatter.format(ratio));
            long result = db.insert(SINGLE_SCORE_TABLE, null, contentValues);
            if (returnTotalCount() > MAX_SIZE){
                removeLowest();
            }
            return result != -1;
        }
        return false;
    }

    Cursor getAllScores(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + SINGLE_SCORE_TABLE + " ORDER BY " + USER_SCORE + " DESC", null);
    }

    private void removeAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SINGLE_SCORE_TABLE, null, null);
    }

    private boolean addCompleteTable(ArrayList<Player> table){
        long success = -1;
        if (table != null && table.size() > 0){
            this.removeAllData();
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            DecimalFormat formatter = new DecimalFormat("#.0");
            for (int count = 0;count < table.size();count++){
                contentValues.put(USER_NAME, table.get(count).getPlayerName());
                contentValues.put(USER_SCORE, table.get(count).getBallHits());
                contentValues.put(USER_RATIO, formatter.format(table.get(count).getGameRatio()));
                success = db.insert(SINGLE_SCORE_TABLE, null, contentValues);
            }
        }
        return success != -1;
    }

    int returnTotalCount(){
        Cursor result = getAllScores();
        return result.getCount();
    }

    private void removeLowest(){
        Cursor result = getAllScores();
        if (result.getCount() >= 5) {
            ArrayList<Player> players = new ArrayList<>();
            for (int c = 0; c < result.getCount() - 1; c++) {
                result.moveToNext();
                players.add(new Player(result.getString(0), result.getInt(1), result.getDouble(2)));
            }
            addCompleteTable(players);
        }
    }

    int returnLowestScore(){
        Cursor result = getAllScores();
        if (result.getCount() > 0) {
            result.moveToLast();
            return result.getInt(1);
        }
        return 0;
    }
}
