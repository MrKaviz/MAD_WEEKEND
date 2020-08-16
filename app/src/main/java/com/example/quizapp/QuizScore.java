package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class QuizScore extends AppCompatActivity {

    private Button retry,back_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_score);

        retry=findViewById(R.id.btn_retry);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retry_int = new Intent(QuizScore.this,QuestionShow.class);
                startActivity(retry_int);
            }
        });

        back_menu=findViewById(R.id.btn_menu);
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btn_back_menu = new Intent(QuizScore.this,MainMenu.class);
                startActivity(btn_back_menu);
            }
        });
    }
}