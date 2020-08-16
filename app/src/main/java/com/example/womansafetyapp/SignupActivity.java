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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText name;
    EditText username;
    EditText password;
    EditText mail;
    EditText confirmpassword;
    EditText address;
    EditText phone;
    Button signup;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

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
        phone=(EditText)findViewById(R.id.phone);
        signup = (Button) findViewById(R.id.signup);

        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");



    }

    public void signup(View v){

        final String name_string=name.getText().toString().trim();
        final String username_string=username.getText().toString().trim();
        final String mail_string=mail.getText().toString().trim();
        final String password_string=password.getText().toString().trim();
        final String confirmpassword_string=confirmpassword.getText().toString().trim();
        final String address_string=address.getText().toString().trim();
        final String phone_string=phone.getText().toString().trim();

        if(TextUtils.isEmpty(name_string)){
            Toast.makeText(SignupActivity.this,"Please Enter Your Name",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(username_string)){
            Toast.makeText(SignupActivity.this,"Please Enter Your Username",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(mail_string)){
            Toast.makeText(SignupActivity.this,"Please Enter Your Email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(phone_string)){
            Toast.makeText(SignupActivity.this,"Please Enter Your Phone Number",Toast.LENGTH_SHORT).show();
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



        if(TextUtils.isEmpty(address_string)){
            Toast.makeText(SignupActivity.this,"Please Enter Your Address",Toast.LENGTH_SHORT).show();
            return;
        }


        if(password_string.length()<6){
            Toast.makeText(SignupActivity.this,"Password is too short",Toast.LENGTH_SHORT).show();
        }




        if(password_string.equals(confirmpassword_string)){

            mAuth.createUserWithEmailAndPassword(mail_string, password_string)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                User user = new User(name_string,username_string,mail_string,address_string,phone_string);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignupActivity.this,"Registration Complete", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                            finish();

                                        } else {
                                            Toast.makeText(SignupActivity.this,"Authentication Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });



        }


     else{

            Toast.makeText(SignupActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();

        }





    }


}