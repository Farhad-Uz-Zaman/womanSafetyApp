package com.example.womansafetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    EditText name;
    EditText username;
    EditText password;
    EditText mail;
    EditText confirmpassword;
    EditText address;
    Button signup;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name=(EditText) findViewById(R.id.name);
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        mail=(EditText) findViewById(R.id.mail);
        confirmpassword=(EditText) findViewById(R.id.confirmpassword);
        address=(EditText) findViewById(R.id.address);
        signup = (Button) findViewById(R.id.signup);
        firebaseAuth=FirebaseAuth.getInstance();



    }

    public void signup(View v){

        String mail_string=mail.getText().toString().trim();
        String password_string=password.getText().toString().trim();
        String confirmpassword_string=confirmpassword.getText().toString().trim();

        if(TextUtils.isEmpty(mail_string)){
            Toast.makeText(SignupActivity.this,"Please Enter Your Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password_string)){
            Toast.makeText(SignupActivity.this,"Please Enter Your Password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(confirmpassword_string)){
            Toast.makeText(SignupActivity.this,"Please Confirm Your Password",Toast.LENGTH_SHORT).show();
            return;
        }

        if(password_string.length()<6){
            Toast.makeText(SignupActivity.this,"Password is too short",Toast.LENGTH_SHORT).show();
        }



        if(password_string.equals(confirmpassword_string)){

            firebaseAuth.createUserWithEmailAndPassword(mail_string, password_string)
                    .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                Toast.makeText(SignupActivity.this,"Registration Complete",Toast.LENGTH_SHORT).show();
                                finish();


                            } else {

                                Toast.makeText(SignupActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });

        }








    }


}