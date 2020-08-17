package com.example.womansafetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {
    private EditText email;
    private EditText passWord;
    private Button login;
    private Button signup;
    private FirebaseAuth authenticate;
    private DatabaseReference ref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        email=(EditText)findViewById(R.id.email);
        passWord=(EditText)findViewById(R.id.Password);
        login = (Button) findViewById(R.id.login);

        authenticate= FirebaseAuth.getInstance();
        ref= FirebaseDatabase.getInstance().getReference().child("Users");



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



         signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(loginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void userLogin( String email, String password)
    {

        authenticate.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(loginActivity.this,"Logged In",Toast.LENGTH_SHORT).show();

                            ref.addValueEventListener(new ValueEventListener() {


                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                                        UserInfo  userinfo = dataSnapshot1.getValue(UserInfo.class);


                                        Intent intent= new Intent(loginActivity.this, MainActivity.class);
                                        intent.putExtra("NAME",userinfo.Name);
                                        intent.putExtra("USERNAME",userinfo.Username);
                                        intent.putExtra("EMAIL",userinfo.Mail);
                                        intent.putExtra("PHONE",userinfo.Phone);
                                        intent.putExtra("ADDRESS",userinfo.Address);
                                        startActivity(intent);
                                        finish();



                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });




                                } else {

                            Toast.makeText(loginActivity.this,"Loggin Failed",Toast.LENGTH_SHORT).show();

                        }


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