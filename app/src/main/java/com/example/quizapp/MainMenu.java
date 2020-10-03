package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MainMenu extends AppCompatActivity {

    private Button btn_menu1,btn_menu2,btn_menu3;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TextView name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        btn_menu1=findViewById(R.id.btn_questions);
        btn_menu2=findViewById(R.id.btn_news);
        btn_menu3=findViewById(R.id.btn_feedback);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student's Menu");

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

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

        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.showIconOnly();

        spaceNavigationView.setSpaceBackgroundColor(ContextCompat.getColor(this, R.color.white));
        spaceNavigationView.setCentreButtonIcon(R.drawable.ic_log_out);
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
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainMenu.this,"Successfully Logout",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),LoginScreen.class));
                finish();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        Intent intentdef =new Intent(MainMenu.this,MainMenu.class);
                        startActivity(intentdef);
                        return;
                    case 1:
                        Intent intent1 = new Intent(MainMenu.this,SubjectCate.class);
                        startActivity(intent1);
                        return;
                    case 2:
                        Intent intent2 = new Intent(MainMenu.this,NewsFeeds.class);
                        startActivity(intent2);
                        return;
                    case 3:
                        Intent intent3 =new Intent(MainMenu.this,Reviews.class);
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
                        Toast.makeText(MainMenu.this, "User Account", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.ques:
                        Intent intent1 = new Intent(MainMenu.this,SubjectCate.class);
                        startActivity(intent1);
                        break;
                    case R.id.newsf:
                        Intent intent2 = new Intent(MainMenu.this,NewsFeeds.class);
                        startActivity(intent2);
                        break;
                    case R.id.revw:
                        Intent intent3 =new Intent(MainMenu.this,Reviews.class);
                        startActivity(intent3);
                        break;
                    case R.id.logoutB:
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(MainMenu.this,"Successfully Logout",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),LoginScreen.class));
                        finish();
                }
                return true;
            }
        });

        View hView =  navigationView.getHeaderView(0);
        name = hView.findViewById(R.id.name);
        email = hView.findViewById(R.id.email);
        /*name.setText("Sejal");
        email.setText("sejal@gmail.com");*/



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

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainMenu.this,"Successfully Logout",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),LoginScreen.class));
        finish();
    }
}