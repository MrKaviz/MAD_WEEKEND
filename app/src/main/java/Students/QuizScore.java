package Students;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.quizapp.LoginScreen;
import com.example.quizapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class QuizScore extends AppCompatActivity {

    private Button retry,back_menu;
    private TextView score;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TextView name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_score);

        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quiz Score");

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        score = findViewById(R.id.scores);
       String score_str = getIntent().getStringExtra("SCORE");
       score.setText(score_str);

        back_menu=findViewById(R.id.btn_menu);
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btn_back_menu = new Intent(QuizScore.this,MainMenu.class);
                startActivity(btn_back_menu);
                QuizScore.this.finish();
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

        spaceNavigationView.addSpaceItem(new SpaceItem("Questions", R.drawable.question_ic));
        spaceNavigationView.addSpaceItem(new SpaceItem("Home", R.drawable.ic_home_));
        spaceNavigationView.addSpaceItem(new SpaceItem("Discussion", R.drawable.ic_notifications));
        spaceNavigationView.addSpaceItem(new SpaceItem("Feedback", R.drawable.ic_feedback));

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(QuizScore.this,"Successfully Logout",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LoginScreen.class));
                finish();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        Intent intent1 = new Intent(QuizScore.this,SubjectCate.class);
                        startActivity(intent1);
                        return;
                    case 1:
                        Intent intentdef =new Intent(QuizScore.this,MainMenu.class);
                        startActivity(intentdef);
                        return;
                    case 2:
                        Intent intent2 = new Intent(QuizScore.this,NewsFeeds.class);
                        startActivity(intent2);
                        return;
                    case 3:
                        Intent intent3 =new Intent(QuizScore.this,Reviews.class);
                        startActivity(intent3);
                        return;

                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });
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