package com.metalsoft.droidpong;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Anthony Ratliff on 7/17/2017.
 */

public class StartGameActivity extends AppCompatActivity {
    private Button singleStart;
    private Handler handler;
    //private ScoresDatabase scoresDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Droid Pong");
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1946BA")));
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setIcon(R.drawable.pong_icon2);
            actionBar.setDisplayUseLogoEnabled(true);
        }
        handler = new Handler();
        singleStart = (Button) findViewById(R.id.button_single_start);
        Button hthButton = (Button) findViewById(R.id.button_hth_start);
        hthButton.setVisibility(View.GONE);
        //scoresDatabase = new ScoresDatabase(this);
        /*
        Cursor result = scoresDatabase.getAllScores();
        if (result.getCount() == 0){
            Toast.makeText(StartGameActivity.this, "There is NO data", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(StartGameActivity.this, "There is data. " + result.getCount() + " pieces of it.", Toast.LENGTH_LONG).show();
            StringBuffer buffer = new StringBuffer();
            while (result.moveToNext()){
                buffer.append("Name: " + result.getString(0) + "\n");
                buffer.append("Score: " + result.getDouble(1) + "\n");
                buffer.append("Date: " + result.getString(2) + "\n\n");
            }
            showMessage("Scores", buffer.toString());

        }
        Boolean success = scoresDatabase.addNewScore("Jerry Brammer", 87.6, "7/26/2017");
        if (success){
            Toast.makeText(StartGameActivity.this, "Data was stored.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(StartGameActivity.this, "Data was NOT stored.", Toast.LENGTH_LONG).show();
        } */
    }

    public void startSingleGameClick(View v){
        singleStart.setPressed(true);
        singleStart.setAllCaps(false);
        singleStart.setBackgroundColor(Color.parseColor("#1946BA"));
        singleStart.setTextColor(Color.WHITE);
        singleStart.invalidate();
        handler.postDelayed(delayedSingleButtonPress, 250);
    }

    private Runnable delayedSingleButtonPress = new Runnable() {

        @Override
        public void run() {
            singleStart.setPressed(false);
            singleStart.setBackgroundColor(Color.WHITE);
            singleStart.setTextColor(Color.parseColor("#1946BA"));
            singleStart.invalidate();
            Intent intent = new Intent(StartGameActivity.this, PongActivity.class);
            startActivity(intent);
        }
    };

        public void startHtHGameClick(View v){

    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
