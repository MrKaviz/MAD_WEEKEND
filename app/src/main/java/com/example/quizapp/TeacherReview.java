package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TeacherReview extends AppCompatActivity {

    private Button tAddRev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_review);

        tAddRev=findViewById(R.id.t_add_feed);
        tAddRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teach_feed_add = new Intent(TeacherReview.this,TeacherAddReview.class);
                startActivity(teach_feed_add);
            }
        });
    }
}