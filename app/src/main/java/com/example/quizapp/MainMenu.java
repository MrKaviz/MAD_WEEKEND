package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    private Button btn_menu1,btn_menu2,btn_menu3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        btn_menu1=findViewById(R.id.btn_questions);
        btn_menu2=findViewById(R.id.btn_news);
        btn_menu3=findViewById(R.id.btn_feedback);

        btn_menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_que = new Intent(MainMenu.this,SubjectCate.class);
                startActivity(intent_que);
            }
        });

        btn_menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_new = new Intent(MainMenu.this,NewsFeeds.class);
                startActivity(intent_new);
            }
        });

        btn_menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_rev =new Intent(MainMenu.this,Reviews.class);
                startActivity(intent_rev);
            }
        });

    }
}