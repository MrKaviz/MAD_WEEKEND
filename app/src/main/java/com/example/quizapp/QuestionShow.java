package com.example.quizapp;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.quizapp.QuestionCate.topicsId;
import static com.example.quizapp.SplashScreen.cateIndex;
import static com.example.quizapp.SplashScreen.catsList;

public class QuestionShow extends AppCompatActivity implements View.OnClickListener {

    private TextView question, quesCount,timer;
    private Button answ1,answ2,answ3,answ4;
    private List<Questions> questionsList;
    private int questiionNum;
    private CountDownTimer countDown;
    private int scorem;
    private int setId;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TextView name,email;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_show);

        question = findViewById(R.id.questions);
        quesCount = findViewById(R.id.questionCount);
        timer= findViewById(R.id.quizTimer);
        setId= getIntent().getIntExtra("SETID",1);

        answ1 = findViewById(R.id.answer1);
        answ2 = findViewById(R.id.answer2);
        answ3 = findViewById(R.id.answer3);
        answ4 = findViewById(R.id.answer4);

        answ1.setOnClickListener(this);
        answ2.setOnClickListener(this);
        answ3.setOnClickListener(this);
        answ4.setOnClickListener(this);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quiz Started!");

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        questionsList=new ArrayList<>();
        firestore=FirebaseFirestore.getInstance();

        getQuestionList();
    }


    private void getQuestionList(){
        questionsList.clear();

        firestore.collection("QUIZ").document(catsList.get(cateIndex).getId())
                .collection(topicsId.get(setId)).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        Map<String, QueryDocumentSnapshot> docList = new ArrayMap<>();

                        for(QueryDocumentSnapshot doc : queryDocumentSnapshots)
                        {
                            docList.put(doc.getId(),doc);
                        }

                        QueryDocumentSnapshot quesListDoc  = docList.get("Questions");

                        String count = quesListDoc.getString("Count");

                        for(int i=0; i < Integer.valueOf(count); i++)
                        {
                            String quesID = quesListDoc.getString("Q" + String.valueOf(i+1) + "_ID");

                            QueryDocumentSnapshot quesDoc = docList.get(quesID);

                            questionsList.add(new Questions(
                                    quesID,
                                    quesDoc.getString("Question"),
                                    quesDoc.getString("A"),
                                    quesDoc.getString("B"),
                                    quesDoc.getString("C"),
                                    quesDoc.getString("D"),
                                    Integer.valueOf(quesDoc.getString("Answer"))
                            ));
                        }
                        setQuestions();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QuestionShow.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setQuestions() {
        timer.setText(String.valueOf(9));
        question.setText(questionsList.get(0).getQuestion());
        answ1.setText(questionsList.get(0).getAnswerA());
        answ2.setText(questionsList.get(0).getAnswerB());
        answ3.setText(questionsList.get(0).getAnswerC());
        answ4.setText(questionsList.get(0).getAnswerD());

        quesCount.setText(String.valueOf(1)+"/"+String.valueOf(questionsList.size()));
        startTimer();
        questiionNum=0;
    }

    private void startTimer() {
         countDown = new CountDownTimer(11000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished < 10000)
                    //timer.setText(String.format(Locale.getDefault(),"%02d:%02d",String.valueOf(millisUntilFinished/1000)));

                    timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                changeQuestion();

            }
        };
        countDown.start();
    }

    @Override
    public void onClick(View v) {

        int selectedOption=0;
        switch (v.getId()){
            case R.id.answer1:
                selectedOption=1;
                break;
            case R.id.answer2:
                selectedOption=2;
                break;
            case R.id.answer3:
                selectedOption=3;
                break;
            case R.id.answer4:
                selectedOption=4;
                break;

            default:
        }
        countDown.cancel();
        checkAnswer(selectedOption,v);

    }

    private void checkAnswer(int selectedOption, View view) {
        if(selectedOption==questionsList.get(questiionNum).getCorectAns()){
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#90ee90")));
            scorem++;
        }
        else{
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffcccb")));
            switch (questionsList.get(questiionNum).getCorectAns()){
                case 1:
                    answ1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#90ee90")));
                    break;
                case 2:
                    answ2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#90ee90")));
                    break;
                case 3:
                    answ3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#90ee90")));
                    break;
                case 4:
                    answ4.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#90ee90")));
                    break;
            }
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeQuestion();
            }
        },500);

    }

    private void changeQuestion() {
        if(questiionNum < questionsList.size() - 1){

            questiionNum++;
            playAnimation(question,0,0);
            playAnimation(answ1,0,1);
            playAnimation(answ2,0,2);
            playAnimation(answ3,0,3);
            playAnimation(answ4,0,4);

            quesCount.setText(String.valueOf(questiionNum + 1)+"/"+String.valueOf(questionsList.size()));
            timer.setText(String.valueOf(9));
            startTimer();
        }
        else{
            Intent int_next = new Intent(QuestionShow.this,QuizScore.class);
            int_next.putExtra("SCORE",String.valueOf(scorem)+"/"+String.valueOf(questionsList.size()));
            startActivity(int_next);
            QuestionShow.this.finish();
        }
    }

    private void playAnimation(final View view, final int value, final int viewNum) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if(value==0){
                            switch(viewNum){
                                case 0:
                                    ((TextView)view).setText(questionsList.get(questiionNum).getQuestion());
                                    break;
                                case 1:
                                    ((Button)view).setText(questionsList.get(questiionNum).getAnswerA());
                                    break;
                                case 2:
                                    ((Button)view).setText(questionsList.get(questiionNum).getAnswerB());
                                    break;
                                case 3:
                                    ((Button)view).setText(questionsList.get(questiionNum).getAnswerC());
                                    break;
                                case 4:
                                    ((Button)view).setText(questionsList.get(questiionNum).getAnswerD());
                                    break;
                            }
                            if(viewNum!=0)
                                ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));

                            playAnimation(view,1,viewNum);
                        }



                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDown.cancel();

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }



}