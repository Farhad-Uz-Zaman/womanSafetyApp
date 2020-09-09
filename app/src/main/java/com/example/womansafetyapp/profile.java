package com.example.womansafetyapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class profile extends Fragment {

    TextView name;
    TextView username;
    TextView mail;
    TextView phone;
    TextView address;
    TextView contact;
    ImageView image;
    String name_string,username_string,mail_string,phone_string,contact_string,address_string;
    public final int TAKE_IMAGE_CODE= 10001;

    public profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle("Profile");

        name=(TextView) view.findViewById(R.id.name);
        username=(TextView)view.findViewById(R.id.username);
        mail=(TextView)view.findViewById(R.id.mail);
        phone=(TextView)view.findViewById(R.id.phone);
        //contact=(TextView)view.findViewById(R.id.contact);
        address=(TextView)view.findViewById(R.id.address);
        image=(ImageView)view.findViewById(R.id.image);


        FirebaseUser us=FirebaseAuth.getInstance().getCurrentUser();

        if(us.getPhotoUrl()!=null){


      Glide.with(this).load(us.getPhotoUrl()).into(image);


        }

        Bundle bundle= getActivity().getIntent().getExtras();
        if(bundle!=null){
            name_string = bundle.getString("NAME");
            username_string = bundle.getString("USERNAME");
            mail_string = bundle.getString("EMAIL");
            phone_string = bundle.getString("PHONE");
            contact_string=bundle.getString("CONTACT");
            address_string = bundle.getString("ADDRESS");

        }

        name.setText(name_string);
        username.setText(username_string);
        phone.setText(phone_string);
        mail.setText(mail_string);
        address.setText(address_string);


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getActivity().getPackageManager())!=null){

                    startActivityForResult(intent,TAKE_IMAGE_CODE);
                }




            }
        });



        return view;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TAKE_IMAGE_CODE){

            switch(resultCode){
                case RESULT_OK :
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    image.setImageBitmap(bitmap);
                    handleUpload(bitmap);

            }


        }
    }


    private void handleUpload(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("ProfileImages")
                .child(uid+".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        getDownloadUrl(reference);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.e(TAG, "onFailure: ",e.getCause() );


                    }
                });



    }

    private void getDownloadUrl(StorageReference reference){

        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess: "+uri);
                        setUserProfileUri(uri);
                    }
                });



    }



    private void setUserProfileUri(Uri uri){

        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        Toast.makeText(getActivity(),"Updated Successfully",Toast.LENGTH_SHORT).show();

                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getActivity(),"Profile Image Upload Failed",Toast.LENGTH_SHORT).show();

                    }
                });


    }


}
