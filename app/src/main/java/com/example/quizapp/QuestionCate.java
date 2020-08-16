package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionCate extends AppCompatActivity {

    private Button btn_quecat1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_cate);

        btn_quecat1=findViewById(R.id.btn_quecat1);
        btn_quecat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_quecat1 =new Intent(QuestionCate.this,QuestionShow.class);
                startActivity(int_quecat1);
            }
        });
    }
}