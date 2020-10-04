package Teachers;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import Models.Questions;
import com.example.quizapp.R;
import com.example.quizapp.TeacherLogin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.util.Map;

import static Teachers.TeacherQuiz.questionsList;
import static Teachers.TeacherSubjects.catList;
import static Teachers.TeacherSubjects.choosedSub;
import static Teachers.TeacherTopics.topicId;
import static Teachers.TeacherTopics.topicNo;

public class TeachQuizAdd extends AppCompatActivity {

    private EditText newQues, ansA, ansB, ansC, ansD, corrAns;
    private String qStr, aStr, bStr, cStr, dStr, ansStr;
    private String act;
    private int questID;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TextView name,email;

    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teach_quiz_add);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Teacher Quiz Add");

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.showIconOnly();

        spaceNavigationView.setSpaceBackgroundColor(ContextCompat.getColor(this, R.color.white));
        spaceNavigationView.setCentreButtonIcon(R.drawable.ic_add);
        spaceNavigationView.setInActiveCentreButtonIconColor(ContextCompat.getColor(this, R.color.white));
        spaceNavigationView.setCentreButtonColor(ContextCompat.getColor(this, R.color.btns));
        spaceNavigationView.setInActiveSpaceItemColor(ContextCompat.getColor(this, R.color.in_btn));
        spaceNavigationView.setActiveSpaceItemColor(ContextCompat.getColor(this, R.color.pressed_btn));

        spaceNavigationView.addSpaceItem(new SpaceItem("Questions", R.drawable.question_ic));
        spaceNavigationView.addSpaceItem(new SpaceItem("Home", R.drawable.ic_home_));
        spaceNavigationView.addSpaceItem(new SpaceItem("Discussion", R.drawable.ic_notifications));
        spaceNavigationView.addSpaceItem(new SpaceItem("Feedback", R.drawable.ic_feedback));

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                qStr=newQues.getText().toString();
                aStr=ansA.getText().toString();
                bStr=ansB.getText().toString();
                cStr=ansC.getText().toString();
                dStr=ansD.getText().toString();
                ansStr=corrAns.getText().toString();

                quistionAddValidate( qStr, aStr,  bStr,  cStr,  dStr,  ansStr);

             /*   if(qStr.isEmpty()) {
                    newQues.setError("Question field is empty");
                    return;
                }

                if(aStr.isEmpty()) {
                    ansA.setError("Option A field is empty");
                    return;
                }

                if(bStr.isEmpty()) {
                    ansB.setError("Option B field is empty");
                    return;
                }
                if(cStr.isEmpty()) {
                    ansC.setError("Option C field is empty");
                    return;
                }
                if(dStr.isEmpty()) {
                    ansD.setError("Option D field is empty");
                    return;
                }
                if(ansStr.isEmpty()) {
                    corrAns.setError("Correct answer field is empty");
                    return;
                } */

                if(act.compareTo("Edit") == 0)
                {
                    editQuestion();
                }
                else {
                    insertNewQuestion();
                }
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        Intent intent1 = new Intent(TeachQuizAdd.this,TeacherSubjects.class);
                        startActivity(intent1);
                        return;
                    case 1:
                        Intent intentdef =new Intent(TeachQuizAdd.this,TeacherMenu.class);
                        startActivity(intentdef);
                        return;
                    case 2:
                        Intent intent2 = new Intent(TeachQuizAdd.this,TeacherNews.class);
                        startActivity(intent2);
                        return;
                    case 3:
                        Intent intent3 =new Intent(TeachQuizAdd.this,TeacherReview.class);
                        startActivity(intent3);
                        return;

                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });

        firestore = FirebaseFirestore.getInstance();

        newQues = findViewById(R.id.addQuestion);
        ansA = findViewById(R.id.answerA);
        ansB = findViewById(R.id.answerB);
        ansC = findViewById(R.id.answerC);
        ansD = findViewById(R.id.answerD);
        corrAns = findViewById(R.id.answerCorrect);

        act = getIntent().getStringExtra("Action");

        if(act.compareTo("Edit") == 0)
        {
            questID = getIntent().getIntExtra("Q_ID",0);
            retrieveData(questID);
            getSupportActionBar().setTitle("Question " + String.valueOf(questID + 1));
            //addnewQue.setText("Update");
        }
        else
        {
            getSupportActionBar().setTitle("Question " + String.valueOf(questionsList.size() + 1));
           // addnewQue.setText("Add");
        }

      /*  addnewQue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qStr=newQues.getText().toString();
                aStr=ansA.getText().toString();
                bStr=ansB.getText().toString();
                cStr=ansC.getText().toString();
                dStr=ansD.getText().toString();
                ansStr=corrAns.getText().toString();

                if(qStr.isEmpty()) {
                    newQues.setError("Question field is empty");
                    return;
                }

                if(aStr.isEmpty()) {
                    ansA.setError("Option A field is empty");
                    return;
                }

                if(bStr.isEmpty()) {
                    ansB.setError("Option B field is empty");
                    return;
                }
                if(cStr.isEmpty()) {
                    ansC.setError("Option C field is empty");
                    return;
                }
                if(dStr.isEmpty()) {
                    ansD.setError("Option D field is empty");
                    return;
                }
                if(ansStr.isEmpty()) {
                    corrAns.setError("Correct answer field is empty");
                    return;
                }

                if(act.compareTo("Edit") == 0)
                {
                    editQuestion();
                }
                else {
                    insertNewQuestion();
                }


            }
        });*/

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()){
                    case R.id.userP:
                        Toast.makeText(TeachQuizAdd.this, "User Account", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.ques:
                        Intent intent1 = new Intent(TeachQuizAdd.this,TeacherSubjects.class);
                        startActivity(intent1);
                        break;
                    case R.id.newsf:
                        Intent intent2 = new Intent(TeachQuizAdd.this,TeacherNews.class);
                        startActivity(intent2);
                        break;
                    case R.id.revw:
                        Intent intent3 =new Intent(TeachQuizAdd.this,TeacherReview.class);
                        startActivity(intent3);
                        break;
                    case R.id.logoutB:
                        FirebaseAuth.getInstance().signOut();
                        logoutToast();
                        startActivity(new Intent(getApplicationContext(), TeacherLogin.class));
                        finish();
                }
                return true;
            }
        });

        View hView =  navigationView.getHeaderView(0);
        name = hView.findViewById(R.id.name);
        email = hView.findViewById(R.id.email);
    }

    private void insertNewQuestion() {
        Map<String,Object> quesData = new ArrayMap<>();

        quesData.put("Question",qStr);
        quesData.put("A",aStr);
        quesData.put("B",bStr);
        quesData.put("C",cStr);
        quesData.put("D",dStr);
        quesData.put("Answer",ansStr);


        final String doc_id = firestore.collection("QUIZ").document(catList.get(choosedSub).getId())
                .collection(topicId.get(topicNo)).document().getId();

        firestore.collection("QUIZ").document(catList.get(choosedSub).getId())
                .collection(topicId.get(topicNo)).document(doc_id)
                .set(quesData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Map<String,Object> quesDoc = new ArrayMap<>();
                        quesDoc.put("Q" + String.valueOf(questionsList.size() + 1) + "_ID", doc_id);
                        quesDoc.put("Count",String.valueOf(questionsList.size() + 1));

                        firestore.collection("QUIZ").document(catList.get(choosedSub).getId())
                                .collection(topicId.get(topicNo)).document("Questions")
                                .update(quesDoc)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        showToast();

                                        questionsList.add(new Questions(
                                                doc_id,
                                                qStr,aStr,bStr,cStr,dStr, Integer.valueOf(ansStr)
                                        ));

                                        TeachQuizAdd.this.finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(TeachQuizAdd.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TeachQuizAdd.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void retrieveData(int id)
    {
        newQues.setText(questionsList.get(id).getQuestion());
        ansA.setText(questionsList.get(id).getAnswerA());
        ansB.setText(questionsList.get(id).getAnswerB());
        ansC.setText(questionsList.get(id).getAnswerC());
        ansD.setText(questionsList.get(id).getAnswerD());
        corrAns.setText(String.valueOf(questionsList.get(id).getCorectAns()));
    }


    private void editQuestion()
    {
        Map<String,Object> quesData = new ArrayMap<>();
        quesData.put("Question", qStr);
        quesData.put("A",aStr);
        quesData.put("B",bStr);
        quesData.put("C",cStr);
        quesData.put("D",dStr);
        quesData.put("Answer",ansStr);


        firestore.collection("QUIZ").document(catList.get(choosedSub).getId())
                .collection(topicId.get(topicNo)).document(questionsList.get(questID).getQustID())
                .set(quesData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        updateToast();

                        questionsList.get(questID).setQuestion(qStr);
                        questionsList.get(questID).setAnswerA(aStr);
                        questionsList.get(questID).setAnswerB(bStr);
                        questionsList.get(questID).setAnswerC(cStr);
                        questionsList.get(questID).setAnswerD(dStr);
                        questionsList.get(questID).setCorectAns(Integer.valueOf(ansStr));

                        TeachQuizAdd.this.finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TeachQuizAdd.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public boolean quistionAddValidate(String qStr, String aStr, String bStr, String cStr, String dStr, String ansStr){
        if(qStr.isEmpty()) {
            newQues.setError("Question field is empty");
            return true;
        }

        if(aStr.isEmpty()) {
            ansA.setError("Option A field is empty");
            return true;
        }

        if(bStr.isEmpty()) {
            ansB.setError("Option B field is empty");
            return true;
        }
        if(cStr.isEmpty()) {
            ansC.setError("Option C field is empty");
            return true;
        }
        if(dStr.isEmpty()) {
            ansD.setError("Option D field is empty");
            return true;
        }
        if(ansStr.isEmpty()) {
            corrAns.setError("Correct answer field is empty");
            return true;
        }
        return false;
    }


    public void showToast(){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_add,(ViewGroup) findViewById(R.id.add_toast) );
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public void logoutToast(){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_logout,(ViewGroup) findViewById(R.id.logout_toast) );
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public void updateToast(){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_update,(ViewGroup) findViewById(R.id.update_toast) );
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}