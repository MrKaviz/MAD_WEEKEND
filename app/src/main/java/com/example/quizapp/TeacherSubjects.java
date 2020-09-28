package com.example.quizapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeacherSubjects extends AppCompatActivity {

    private Button addCate, cateAddBTN;
    private Dialog addCateDialog;
    private EditText cateNameDia;
    private RecyclerView recyclerView;
    public static List<Category> catList = new ArrayList<>();
    private FirebaseFirestore firestore;
    private TeacherSubAdapter subAdapter;
    public static int choosedSub=0;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TextView name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_subjects);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("App");

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        recyclerView = findViewById(R.id.teachRecycle);
        addCate=findViewById(R.id.cateAdd);

        addCateDialog = new Dialog(TeacherSubjects.this);
        addCateDialog.setContentView(R.layout.add_subject);
        addCateDialog.setCancelable(true);
        addCateDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        cateNameDia=addCateDialog.findViewById(R.id.addCateTXT);
        cateAddBTN=addCateDialog.findViewById(R.id.addCateBTN);

        firestore=FirebaseFirestore.getInstance();


        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.showIconOnly();

        spaceNavigationView.setSpaceBackgroundColor(ContextCompat.getColor(this, R.color.white));
        spaceNavigationView.setCentreButtonIcon(R.drawable.ic_add);
        spaceNavigationView.setInActiveCentreButtonIconColor(ContextCompat.getColor(this, R.color.white));
        spaceNavigationView.setCentreButtonColor(ContextCompat.getColor(this, R.color.btns));
        spaceNavigationView.setInActiveSpaceItemColor(ContextCompat.getColor(this, R.color.in_btn));
        spaceNavigationView.setActiveSpaceItemColor(ContextCompat.getColor(this, R.color.pressed_btn));

        spaceNavigationView.addSpaceItem(new SpaceItem("Home", R.drawable.ic_home_));
        spaceNavigationView.addSpaceItem(new SpaceItem("Questions", R.drawable.question_ic));
        spaceNavigationView.addSpaceItem(new SpaceItem("Discussion", R.drawable.ic_notifications));
        spaceNavigationView.addSpaceItem(new SpaceItem("Feedback", R.drawable.ic_feedback));

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                cateNameDia.getText().clear();
                addCateDialog.show();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        Intent intentdef =new Intent(TeacherSubjects.this,TeacherMenu.class);
                        startActivity(intentdef);
                        return;
                    case 1:
                        Intent intent1 = new Intent(TeacherSubjects.this,TeacherSubjects.class);
                        startActivity(intent1);
                        return;
                    case 2:
                        Intent intent2 = new Intent(TeacherSubjects.this,TeacherNews.class);
                        startActivity(intent2);
                        return;
                    case 3:
                        Intent intent3 =new Intent(TeacherSubjects.this,TeacherReview.class);
                        startActivity(intent3);
                        return;

                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });

        cateAddBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cateNameDia.getText().toString().isEmpty())
                {
                    cateNameDia.setError("Enter Category Name");
                    return;
                }

                addNewCategory(cateNameDia.getText().toString());

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()){
                    case R.id.userP:
                        Toast.makeText(TeacherSubjects.this, "User Account", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.ques:
                        Intent intent1 = new Intent(TeacherSubjects.this,TeacherSubjects.class);
                        startActivity(intent1);
                        break;
                    case R.id.newsf:
                        Intent intent2 = new Intent(TeacherSubjects.this,TeacherNews.class);
                        startActivity(intent2);
                        break;
                    case R.id.revw:
                        Intent intent3 =new Intent(TeacherSubjects.this,TeacherReview.class);
                        startActivity(intent3);
                        break;
                    case R.id.logoutB:
                        FirebaseAuth.getInstance().signOut();
                        logoutToast();
                        startActivity(new Intent(getApplicationContext(),TeacherLogin.class));
                        finish();
                }
                return true;
            }
        });

        View hView =  navigationView.getHeaderView(0);
        name = hView.findViewById(R.id.name);
        email = hView.findViewById(R.id.email);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        loadData();

    }

    private void loadData()
    {
        catList.clear();

        firestore.collection("QUIZ").document("Categories")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful())
                {
                    DocumentSnapshot doc = task.getResult();

                    if(doc.exists())
                    {
                        long count = (long)doc.get("Count");

                        for(int i=1; i <= count; i++)
                        {
                            String catName = doc.getString("CAT" + String.valueOf(i)+"_NAME");
                            String catID = doc.getString("CAT"+String.valueOf(i)+"_ID");
                            catList.add(new Category(catID,catName,"0","1"));
                        }

                        subAdapter = new TeacherSubAdapter(catList);
                        recyclerView.setAdapter(subAdapter);


                    }
                    else
                    {
                        Toast.makeText(TeacherSubjects.this,"No Category Document Exists!",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
                else
                {

                    Toast.makeText(TeacherSubjects.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

       private void addNewCategory(final String title) {

        addCateDialog.dismiss();

        final Map<String,Object> catData = new ArrayMap<>();
        catData.put("Name",title);
        catData.put("Sets",0);
        catData.put("Counter","1");

        final String doc_id = firestore.collection("QUIZ").document().getId();

        firestore.collection("QUIZ").document(doc_id)
                .set(catData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Map<String,Object> catDoc = new ArrayMap<>();
                        catDoc.put("CAT" + String.valueOf(catList.size() + 1) + "_NAME",title);
                        catDoc.put("CAT" + String.valueOf(catList.size() + 1) + "_ID",doc_id);
                        catDoc.put("Count", catList.size() + 1);

                        firestore.collection("QUIZ").document("Categories")
                                .update(catDoc)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        showToast();
                                        //Toast.makeText(TeacherSubjects.this,"Category added successfully",Toast.LENGTH_SHORT).show();

                                        catList.add(new Category(doc_id,title,"0","1"));

                                        subAdapter.notifyItemInserted(catList.size());

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(TeacherSubjects.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TeacherSubjects.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


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