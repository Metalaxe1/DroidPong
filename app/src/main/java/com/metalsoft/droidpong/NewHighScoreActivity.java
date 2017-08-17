package com.metalsoft.droidpong;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class NewHighScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_high_score);
        WebView webView = (WebView) findViewById(R.id.fireworks_gif);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.loadUrl("file:///android_asset/fireworks.gif");

    }

    public void saveScore(View view) {
        TextView txtName = (TextView) findViewById(R.id.score_name);
        if (txtName != null && txtName.getText().toString().length() > 0){
            Intent intent = new Intent();
            intent.putExtra("name", txtName.getText().toString());
            if (getParent() == null) {
                setResult(RESULT_OK, intent);
            } else {
                getParent().setResult(Activity.RESULT_OK, intent);
            }
            finish();
        }
    }
}
