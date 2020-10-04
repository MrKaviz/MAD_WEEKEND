package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import Students.MainMenu;

public class StudentSignUp extends AppCompatActivity {

    EditText fName,lName,eMail,pass,school;
    Button btn_in;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_sign_up);

        fName = findViewById(R.id.PersonFirstName);
        lName=findViewById(R.id.PersonLastName);
        eMail=findViewById(R.id.editTextTextPersonEmail);
        pass=findViewById(R.id.personSchool);
        school=findViewById(R.id.editTextTextSchool);
        btn_in=findViewById(R.id.button);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainMenu.class));
            finish();
        }


        btn_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eMail.getText().toString().trim();
                String password = pass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    eMail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    pass.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    pass.setError("Password Must be >= 6 Characters");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            showToast();
                            startActivity(new Intent(getApplicationContext(),MainMenu.class));

                        }else {
                            Toast.makeText(StudentSignUp.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });


}

    public void showToast(){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast_register,(ViewGroup) findViewById(R.id.register_toast) );
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }
}