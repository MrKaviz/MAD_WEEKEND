package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class TeacherReview extends AppCompatActivity {

    private EditText edit1;
    private EditText edit2;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TextView name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit1);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Teacher's Feedback");

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.showIconOnly();

        spaceNavigationView.setSpaceBackgroundColor(ContextCompat.getColor(this, R.color.white));
        spaceNavigationView.setInActiveCentreButtonIconColor(ContextCompat.getColor(this, R.color.white));
        spaceNavigationView.setCentreButtonColor(ContextCompat.getColor(this, R.color.btns));
        spaceNavigationView.setInActiveSpaceItemColor(ContextCompat.getColor(this, R.color.in_btn));
        spaceNavigationView.setActiveSpaceItemColor(ContextCompat.getColor(this, R.color.pressed_btn));

        spaceNavigationView.addSpaceItem(new SpaceItem("Feedback", R.drawable.ic_feedback));
        spaceNavigationView.addSpaceItem(new SpaceItem("Home", R.drawable.ic_home_));
        spaceNavigationView.addSpaceItem(new SpaceItem("Questions", R.drawable.question_ic));
        spaceNavigationView.addSpaceItem(new SpaceItem("Discussion", R.drawable.ic_notifications));

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("massage/html");
                i.putExtra(Intent.EXTRA_EMAIL,new String[]{"admin@quizzapp.com"});
                i.putExtra(Intent.EXTRA_TEXT, "Name:\n" + edit1.getText() + "\n \n Message:\n" + edit2.getText());
                i.putExtra(Intent.EXTRA_SUBJECT,"Feedback from App user");
                try {
                    startActivity(Intent.createChooser(i, "Please Select Email"));
                } catch (android.content.ActivityNotFoundException e) {
                    Toast.makeText(TeacherReview.this, "There are no Email Clients", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        Intent intentdef =new Intent(TeacherReview.this,TeacherReview.class);
                        startActivity(intentdef);
                        return;
                    case 1:
                        Intent intent1 = new Intent(TeacherReview.this,TeacherMenu.class);
                        startActivity(intent1);
                        return;
                    case 2:
                        Intent intent2 = new Intent(TeacherReview.this,TeacherSubjects.class);
                        startActivity(intent2);
                        return;
                    case 3:
                        Intent intent3 =new Intent(TeacherReview.this,TeacherNews.class);
                        startActivity(intent3);
                        return;

                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()){
                    case R.id.userP:
                        Toast.makeText(TeacherReview.this, "User Account", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.ques:
                        Intent intent1 = new Intent(TeacherReview.this,TeacherSubjects.class);
                        startActivity(intent1);
                        break;
                    case R.id.newsf:
                        Intent intent2 = new Intent(TeacherReview.this,TeacherNews.class);
                        startActivity(intent2);
                        break;
                    case R.id.revw:
                        Intent intent3 =new Intent(TeacherReview.this,TeacherReview.class);
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