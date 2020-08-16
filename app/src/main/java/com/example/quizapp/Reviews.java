package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Reviews extends AppCompatActivity {

    private Button feedbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        feedbacks=findViewById(R.id.go_add_feed);
        feedbacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_feed_add = new Intent(Reviews.this,AddFeedback.class);
                startActivity(int_feed_add);
            }
        });
    }
}