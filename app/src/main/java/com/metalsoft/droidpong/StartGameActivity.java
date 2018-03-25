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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Anthony Ratliff on 7/17/2017.
 */

public class StartGameActivity extends AppCompatActivity {
    private Button singleStart;
    private Handler handler;
    private ScoresDatabase scoresDatabase;
    private ArrayList<Player> players = new ArrayList<>();
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
        LinearLayout optionsLayout = (LinearLayout) findViewById(R.id.layout_game_options);
        ViewGroup.LayoutParams params = optionsLayout.getLayoutParams();
        int width = getResources().getDisplayMetrics().widthPixels;
        params.width = (int) (width*.80);
        optionsLayout.setLayoutParams(params);
        hthButton.setVisibility(View.GONE);
        scoresDatabase = new ScoresDatabase(this);
        //scoresDatabase.removeAllData();
    }

    public void startSingleGameClick(View v){
        singleStart.setPressed(true);
        singleStart.setAllCaps(false);
        singleStart.setBackgroundColor(Color.parseColor("#1946BA"));
        singleStart.setTextColor(Color.WHITE);
        singleStart.invalidate();
        handler.postDelayed(delayedSingleButtonPress, 100);
    }

    private Runnable delayedSingleButtonPress = new Runnable() {

        @Override
        public void run() {
            singleStart.setPressed(false);
            singleStart.setBackgroundColor(Color.WHITE);
            singleStart.setTextColor(Color.parseColor("#1946BA"));
            singleStart.invalidate();
            Intent intent = new Intent(StartGameActivity.this, SingleActivity.class);
            intent.putExtra("lowest_score", returnLowestScore());
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

    public int returnLowestScore(){
        int lowest = 0;
        if (players != null && players.size() > 0){
            for (int counter = 0;counter < players.size();counter++){
                if (lowest == 0 || lowest > players.get(counter).getBallHits()){
                    lowest = players.get(counter).getBallHits();
                }
            }
        }
        return lowest;
    }

    private void loadScoresList(){
        players = new ArrayList<>();
        RelativeLayout scoresLayout = (RelativeLayout) findViewById(R.id.layout_score_list);
        int lowestScore = 0;
        Cursor result = scoresDatabase.getAllScores();
        if (result.getCount() == 0){
            scoresLayout.setVisibility(View.GONE);
        } else {
            ListView highScores = (ListView) findViewById(R.id.high_scores_list);
            while (result.moveToNext()){
                players.add(new Player(result.getString(0), result.getInt(1), result.getDouble(2)));
                if (lowestScore == 0  || lowestScore > result.getInt(1)){
                    lowestScore = result.getInt(1);
                }
            }
            highScores.setAdapter(new HighScoreAdapter(this, players));
            scoresLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadScoresList();
    }
}
