package com.example.thamali;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TeacherNews extends AppCompatActivity {

    private Button addTeacherNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_news);

        addTeacherNews=findViewById(R.id.btn_sub1_teach_ad);
       addTeacherNews.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent int_news_add = new Intent(TeacherNews.this,NewsAdd.class);
               startActivity(int_news_add);
           }
       });
    }
}