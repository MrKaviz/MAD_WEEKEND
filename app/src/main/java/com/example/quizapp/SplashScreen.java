package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import Models.Category;


public class SplashScreen extends AppCompatActivity {

    public static List<Category> catsList= new ArrayList<>();
    public static int cateIndex = 0;
    private FirebaseFirestore firestore;

    private ImageView logo_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        logo_img=(ImageView) findViewById(R.id.logo);
        Animation splash_ani = AnimationUtils.loadAnimation(this,R.anim.all_anims);
        logo_img.startAnimation(splash_ani);

        firestore= FirebaseFirestore.getInstance();

        Thread time = new Thread(){
            public void run(){
                    loadData();
            }
        };
        time.start();
    }

   /* private void loadData(){
        catList.clear();
        firestore.collection("QUIZ").document("Categories").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        long count = (long) doc.get("Count");

                        for (int i = 1; i <= count; i++) {
                            String catName = doc.getString("CAT" + String.valueOf(i));
                            catList.add(catName);
                        }
                        Intent anim_int = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(anim_int);
                        SplashScreen.this.finish();
                    } else {
                        Toast.makeText(SplashScreen.this, "No any categories", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(SplashScreen.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/


  private void loadData() {
       catsList.clear();
        firestore.collection("QUIZ").document("Categories").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    long count = (long) documentSnapshot.get("Count");

                    for(int i=1; i<=count; i++){
                        String catName = documentSnapshot.getString("CAT"+ String.valueOf(i)+"_NAME");
                        String catID = documentSnapshot.getString("CAT"+ String.valueOf(i)+"_ID");

                        catsList.add(new Category(catID,catName));
                    }
                    Intent anim_int = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(anim_int);
                    SplashScreen.this.finish();
                }else{
                    Toast.makeText(SplashScreen.this,"No any categories",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SplashScreen.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }



}