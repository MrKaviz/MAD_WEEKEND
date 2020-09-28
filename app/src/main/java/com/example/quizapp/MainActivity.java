package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button button;
    TextView login_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btnmain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterType();
            }

            public void openRegisterType()
            {
                Intent intent = new Intent(MainActivity.this , RegisterType.class);
                startActivity(intent);
            }
        });


        login_txt=(TextView)findViewById(R.id.login_txt);
        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"Successfully Login",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this,LoginType.class);
                startActivity(intent);


            }
        });
    }
}