package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TeacherSignUp extends AppCompatActivity {

    private Button teach_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_sign_up);

        teach_signup=findViewById(R.id.teach_signup);
        teach_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teach_signup = new Intent(TeacherSignUp.this,TeacherMenu.class);
                startActivity(teach_signup);
            }
        });
    }
}