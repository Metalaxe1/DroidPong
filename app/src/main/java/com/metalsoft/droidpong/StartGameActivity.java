package com.metalsoft.droidpong;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
            actionBar.setTitle("Droid Pong v1.0 by Anthony Ratliff");
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#22B14C")));
        }
    }

    public void startGameClick(View v){
        Intent intent = new Intent(this, PongActivity.class);
        startActivity(intent);
    }
}
