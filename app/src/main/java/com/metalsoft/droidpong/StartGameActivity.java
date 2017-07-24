package com.metalsoft.droidpong;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Anthony Ratliff on 7/17/2017.
 */

public class StartGameActivity extends AppCompatActivity {
    private Button hthButton;
    private Button singleStart;
    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Droid Pong");
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1946BA")));
        }
        handler = new Handler();
        singleStart = (Button) findViewById(R.id.button_single_start);
        hthButton = (Button) findViewById(R.id.button_hth_start);
        hthButton.setVisibility(View.GONE);
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
}
