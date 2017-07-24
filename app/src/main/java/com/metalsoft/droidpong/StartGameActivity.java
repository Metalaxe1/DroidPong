package com.metalsoft.droidpong;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Anthony Ratliff on 7/17/2017.
 */

public class StartGameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Droid Pong");
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1946BA")));
        }
    }

    public void startSingleGameClick(View v){
        Button singleStart = (Button) v.findViewById(R.id.button_single_start);
        singleStart.setPressed(true);
        singleStart.invalidate();
        //singleStart.setPressed(false);
        //singleStart.invalidate();
        Intent intent = new Intent(this, PongActivity.class);
        startActivity(intent);
    }

    public void startHtHGameClick(View v){

    }
}
