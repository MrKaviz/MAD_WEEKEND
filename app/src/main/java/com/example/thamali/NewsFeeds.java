package com.example.thamali;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NewsFeeds extends AppCompatActivity {

    private Button news_v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feeds);

        news_v = (Button) findViewById(R.id.btn_quecat1);
        news_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte_news = new Intent(NewsFeeds.this, com.example.quizapp.NewsView.class);
                startActivity(inte_news);
            }
        });
    }
}