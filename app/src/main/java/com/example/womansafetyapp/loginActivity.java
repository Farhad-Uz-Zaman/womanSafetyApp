package com.example.womansafetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    private EditText email;
    private EditText passWord;
    private Button login;
    private FirebaseAuth authenticate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=(EditText)findViewById(R.id.email);
        passWord=(EditText)findViewById(R.id.Password);
        login = (Button) findViewById(R.id.login);

        authenticate= FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getData()){
                    String logEmail=email.getText().toString();
                    String logPass=passWord.getText().toString();
                    userLogin(logEmail,logPass);
               }
            }
        });



        Button signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(loginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void userLogin(String email,String password)
    {
        authenticate.signInWithEmailAndPassword(email,password).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(loginActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
            }
        });

        authenticate.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(loginActivity.this,"Logged in",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(loginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean getData()
    {
        String loginEmail= email.getText().toString();
        String loginPassWord= passWord.getText().toString();

        if(loginEmail.isEmpty()||loginPassWord.isEmpty())
        {
            Toast.makeText(loginActivity.this,"Please Complete the fields",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }




}