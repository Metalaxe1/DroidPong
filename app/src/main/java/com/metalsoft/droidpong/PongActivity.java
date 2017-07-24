package com.metalsoft.droidpong;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PongActivity extends AppCompatActivity {

    private View mContentView;
    private GameAnimationView animation;
    private Handler handler;
    private Button buttonEnd;
    private TextView ballStartText, gameOverText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pong);
        animation = (GameAnimationView) findViewById(R.id.view);
        handler = new Handler();
        animation.setCustomObjectListener(new GameAnimationView.PongEventListener() {
            @Override
            public void onObjectReady(String title) {

            }

            @Override
            public void onDataLoaded(String data) {
                if (data.equals("Game Over")){
                    handler.postDelayed(endGame, 3000);
                }
                if (data.equals("Missed Ball")){
                    buttonEnd.setVisibility(View.VISIBLE);
                    ballStartText.setVisibility(View.VISIBLE);
                }
                if (data.equals("Game Started")){
                    buttonEnd.setVisibility(View.GONE);
                    ballStartText.setVisibility(View.GONE);
                }
                if (data.equals("Game Over")){
                    gameOverText.setVisibility(View.VISIBLE);
                }

            }
        });
        mContentView = findViewById(R.id.fullscreen_content);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        buttonEnd = (Button) findViewById(R.id.button_end);
        buttonEnd.setVisibility(View.GONE);
        ballStartText = (TextView) findViewById(R.id.text_ball_start);
        gameOverText = (TextView) findViewById(R.id.text_game_over);
        gameOverText.setVisibility(View.GONE);
        //mContentView.setVisibility(View.INVISIBLE);
    }

    public void endGameClick(View v){
        finish();
    }

    private Runnable endGame = new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };


}
