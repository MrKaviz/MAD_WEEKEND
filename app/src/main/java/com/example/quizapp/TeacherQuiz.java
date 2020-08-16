package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TeacherQuiz extends AppCompatActivity {

    private Button teach_add_quiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_quiz);

        teach_add_quiz=findViewById(R.id.btn_sub1_teach_ad);
        teach_add_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teach_add_quiz = new Intent(TeacherQuiz.this,TeachQuizAdd.class);
                startActivity(teach_add_quiz);
            }
        });
    }
}