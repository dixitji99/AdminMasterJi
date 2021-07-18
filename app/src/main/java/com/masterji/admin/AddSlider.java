package com.masterji.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Date;

public class AddSlider extends AppCompatActivity {
    CardView addSlider;
    EditText title;
    String title1=null;
    Context thisContext=this;
    int dataCode = 0;
    Uri imageuri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_slider);
        addSlider=findViewById(R.id.add_slider2);
        addSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=findViewById(R.id.slider_title);
                title1=title.getText().toString();
                if(title1.isEmpty())
                {
                    title.setError("Enter Title");
                }
                else
                {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(2, 1)
                            .start((Activity) thisContext);
                    dataCode = 12;
                }
            }
        });
    }
    ProgressDialog uploadProgress;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Here we are initialising the progress dialog box
        uploadProgress = new ProgressDialog(this);
        uploadProgress.setMessage("Uploading");
        // this will show message uploading
        // while pdf is uploading
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        uploadProgress.show();
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageuri = result.getUri();
                final String timestamp = "" + System.currentTimeMillis();
                final String messagePushID = timestamp;
                Toast.makeText(this, imageuri.toString(), Toast.LENGTH_SHORT).show();
                // Here we are uploading the image in firebase storage with the name of current time
                final StorageReference filepath = storageReference.child("Slider").child(messagePushID + "." + "img");
                Toast.makeText(this, filepath.getName(), Toast.LENGTH_SHORT).show();
                filepath.putFile(imageuri).continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            // After uploading is done it progress
                            // dialog box will be dismissed
                            uploadProgress.dismiss();
                            Uri uri = task.getResult();
                            String myurl;
                            myurl = uri.toString()+"/"+messagePushID + "."+"img";
                            ImageData imageData = new ImageData(title1,myurl);
                            databaseReference.child("Slider").child(String.valueOf(new Date())).setValue(imageData);
                            Toast.makeText(thisContext, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            uploadProgress.dismiss();
                            Toast.makeText(thisContext, "UploadedFailed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(thisContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

            }
        }
    }
}