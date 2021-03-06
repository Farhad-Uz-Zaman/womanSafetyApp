package com.example.womansafetyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , BottomNavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout navdraw;
    FirebaseUser user;
    FirebaseAuth fAuth;
    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.nav_drawer_layout);
        Toolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);

        navdraw = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.navigation_view);

        // for slide/nav.drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,navdraw,tb,R.string.navigationDrawerOpen,R.string.navigationDrawerClose);
        navdraw.addDrawerListener(toggle);
        toggle.syncState();

        //for nav drawer header
        Bundle bundle= getIntent().getExtras();
        if(bundle!=null){
            uname = bundle.getString("NAME");
        }
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        View header = navView.getHeaderView(0);
        TextView userName = header.findViewById(R.id.user_name_drawer);
        TextView userMail = header.findViewById(R.id.user_mail_drawer);
        ImageView userImage = header.findViewById(R.id.image_drawer);

        userName.setText(uname);
        userMail.setText(user.getEmail());

        if(user.getPhotoUrl()!=null){
            //Glide.with(this).load(user.getPhotoUrl()).into(userImage);
        }


        // for nav.drawer fragments
        navView.setNavigationItemSelectedListener(this);

        //for bottom navigation
        BottomNavigationView bn = findViewById(R.id.bottomNav);
        bn.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new home()).commit();
            bn.setSelectedItemId(R.id.nav_home);

        }

    }



    // check if drawer closed or not
    public void onBackButtonPress (){
        if(navdraw.isDrawerOpen(GravityCompat.START)){
            navdraw.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }



    // for clicked item to stay selected
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new profile()).commit();
            break;
            case R.id.notifications:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new notifications()).commit();
            break;
            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new settings()).commit();
            break;
            //case R.id.contact:
            //    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new contact()).commit();
            //    break;
            case R.id.emergency_contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new emergency_contact()).commit();
                break;


            case R.id.about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new about()).commit();
            break;

            case R.id.share:
                Toast.makeText(this, "share the app", Toast.LENGTH_SHORT).show();
            break;

            case R.id.reportbug:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new reportBug()).commit();
             break;
            case R.id.support:
                Toast.makeText(this, "buy us a coffee!!!", Toast.LENGTH_SHORT).show();
            break;



            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new home()).commit();
                break;
            case R.id.dash:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new dashboard()).commit();
                break;
            case R.id.set:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new settings()).commit();
                break;
            case R.id.signout:
                signout s= new signout();
                Toast.makeText(this,"Signed Out",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(this, loginActivity.class);
                startActivity(intent);
                this.finish();
                break;

        }

        navdraw.closeDrawer(GravityCompat.START);
        return true;
    }
}
