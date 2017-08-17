package com.metalsoft.droidpong;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PongActivity extends AppCompatActivity {

    private Handler handler;
    private Button buttonEnd;
    private TextView ballStartText, gameOverText;
    private int lowestScore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pong);
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!= null){
            lowestScore = (int) bundle.get("lowest_score");
        }
        GameAnimationView animation = (GameAnimationView) findViewById(R.id.view);
        handler = new Handler();
        animation.setCustomObjectListener(new GameAnimationView.PongEventListener() {
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
                if (data.contains("▲")){
                    String[] pieces = data.split("▲");
                    ScoresDatabase db = new ScoresDatabase(getApplicationContext());
                    if (pieces[0].equals("Score") && (Integer.valueOf(pieces[1]) > db.returnLowestScore()) || db.returnTotalCount() < 5){
                        db.addNewScore("Metalaxe", Integer.valueOf(pieces[1]), Double.valueOf(pieces[2]));
                    }
                }

            }
        });
        View mContentView = findViewById(R.id.fullscreen_content);
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
