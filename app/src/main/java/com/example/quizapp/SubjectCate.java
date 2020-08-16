package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SubjectCate extends AppCompatActivity {

    private Button btn_sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_cate);

        btn_sub=findViewById(R.id.btn_sub1);
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_sub1 = new Intent(SubjectCate.this,QuestionCate.class);
                startActivity(intent_sub1);
            }
        });
    }
}