package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private ImageView logo_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        logo_img=(ImageView) findViewById(R.id.logo);
        Animation splash_ani = AnimationUtils.loadAnimation(this,R.anim.all_anims);
        logo_img.startAnimation(splash_ani);

        final Intent anim_int = new Intent(SplashScreen.this,MainActivity.class);

        Thread time = new Thread(){
            public void run(){
                try{
                    sleep(4000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(anim_int);
                    finish();
                }
            }
        };
        time.start();
    }
}