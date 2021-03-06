package com.metalsoft.droidpong;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PongActivity extends AppCompatActivity {

    private Handler handler;
    private ScoresDatabase db;
    private Button buttonEnd;
    private TextView ballStartText, gameOverText;
    private int finalScore = 0;
    private double finalRatio = 0.0;
    private SoundPool soundPool;
    private int sideSoundID = -1, paddleSoundID = -1, topSoundID = -1, missedSoundID = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pong);
        GameAnimationView animation = (GameAnimationView) findViewById(R.id.view);
        db = new ScoresDatabase(getApplicationContext());
        handler = new Handler();
        animation.setCustomObjectListener(new GameAnimationView.PongEventListener() {
            @Override
            public void onDataLoaded(String data) {
                if (data.equals("Missed Ball")){
                    buttonEnd.setVisibility(View.VISIBLE);
                    ballStartText.setVisibility(View.VISIBLE);
                }
                if (data.equals("Game Started")){
                    buttonEnd.setVisibility(View.GONE);
                    ballStartText.setVisibility(View.GONE);
                }
                if (data.equals("Side Sound")){
                    soundPool.play(sideSoundID, 1, 1, 0, 0, 1);
                }
                if (data.equals("Top Sound")){
                    soundPool.play(sideSoundID, 1, 1, 0, 0, 1);
                }
                if (data.equals("Paddle Sound")){
                    soundPool.play(paddleSoundID, 1, 1, 0, 0, 1);
                }
                if (data.equals("Missed Sound")){
                    soundPool.play(missedSoundID, 1, 1, 0, 0, 1);
                }
                if (data.contains("▲")){
                    String[] pieces = data.split("▲");
                    if (pieces[0].equals("Score")){
                        gameOverText.setVisibility(View.VISIBLE);
                        if ((Integer.valueOf(pieces[1]) > db.returnLowestScore()) || db.returnTotalCount() < 5) {
                            finalScore = Integer.valueOf(pieces[1]);
                            finalRatio = Double.valueOf(pieces[2]);
                            handler.postDelayed(highScore, 3000);
                        } else {
                            handler.postDelayed(endGame, 3000);
                        }
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
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        AssetManager assetManager = this.getAssets();
        try {
            AssetFileDescriptor sideSound = assetManager.openFd("wall.wav");
            AssetFileDescriptor topSound = assetManager.openFd("wall.wav");
            AssetFileDescriptor paddleSound = assetManager.openFd("paddle.wav");
            AssetFileDescriptor missedSound = assetManager.openFd("ball_missed.wav");
            sideSoundID = soundPool.load(sideSound, 0);
            topSoundID = soundPool.load(topSound, 0);
            paddleSoundID = soundPool.load(paddleSound, 0);
            missedSoundID = soundPool.load(missedSound, 0);

        } catch (IOException e) {
            e.printStackTrace();
        }

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

    private Runnable highScore = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(PongActivity.this, NewHighScoreActivity.class);
            startActivityForResult(intent, 1001);
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001){
            if (resultCode == RESULT_OK){
                Bundle results = data.getExtras();
                String playerName = (String) results.get("name");
                db.addNewScore(playerName, finalScore, finalRatio);
                finish();
            }
        }
    }
}
