package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TeacherSubjects extends AppCompatActivity {

    private Button teach_sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_subjects);

        teach_sub=findViewById(R.id.btn_sub1_teach);
        teach_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sub1_teach=new Intent(TeacherSubjects.this,TeacherQuiz.class);
                startActivity(sub1_teach);
            }
        });
    }
}