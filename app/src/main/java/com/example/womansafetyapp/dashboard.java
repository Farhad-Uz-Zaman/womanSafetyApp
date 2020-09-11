package com.example.womansafetyapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class dashboard extends Fragment  {

    Button sendReportManually;
    EditText username;
    EditText  details;
    FirebaseDatabase db;
    DatabaseReference dr;
    public final int TAKE_IMAGE_CODE= 10001;
    ImageView image;
    Bitmap bitmap;
    String name,detail;


    public dashboard() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        getActivity().setTitle("Report");

        sendReportManually = (Button) view.findViewById(R.id.send);
        username = view.findViewById(R.id.userName);
        details = view.findViewById(R.id.reportDetails);
        image=(ImageView)view.findViewById(R.id.image);



        sendReportManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = FirebaseDatabase.getInstance();//"ReportManual"
                dr = db.getReference("ReportManual");

                 name = username.getText().toString();
                 detail = details.getText().toString();

                if (!name.isEmpty() && !detail.isEmpty()){
                    reportManual rm = new reportManual(name,detail);
                    dr.child(name).setValue(rm);
                    Toast.makeText(getActivity(), "report received", Toast.LENGTH_SHORT).show();
                     username.setText("");
                     details.setText("");
                }
                else if (name.isEmpty() || detail.isEmpty()){
                    Toast.makeText(getActivity(), "fill sections properly", Toast.LENGTH_SHORT).show();

                }


                handleUpload(bitmap);





            }
        });



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
                    bitmap = (Bitmap) data.getExtras().get("data");
                    image.setImageBitmap(bitmap);
                   // handleUpload(bitmap);



            }


        }
    }


    private void handleUpload(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("MolesterImages")
                .child(uid).child(name+".jpeg");

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


                        Toast.makeText(getActivity(),"Image recieved",Toast.LENGTH_SHORT).show();

                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getActivity(),"Image isn't sent",Toast.LENGTH_SHORT).show();

                    }
                });


    }




}
