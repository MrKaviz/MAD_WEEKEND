package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TeacherMenu extends AppCompatActivity {

    private Button addQuiz,addNews,addReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_menu);

        addQuiz=findViewById(R.id.btn_questions_teach);
        addQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teach_add = new Intent(TeacherMenu.this,TeacherSubjects.class);
                startActivity(teach_add);
            }
        });

        addNews=findViewById(R.id.btn_news);
        addNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teach_news = new Intent(TeacherMenu.this,TeacherNews.class);
                startActivity(teach_news);
            }
        });

        addReview=findViewById(R.id.btn_feedback);
        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teach_feed = new Intent(TeacherMenu.this,TeacherReview.class);
                startActivity(teach_feed);
            }
        });
    }
}