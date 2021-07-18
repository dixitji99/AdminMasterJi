package com.masterji.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

public class UploadPDF extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String className="Class";
    String pdfTitle=null;
    Uri imageuri=null;
    Context thisContext=this;
    Intent galleryIntent;
    LinearLayout uploadPDF;
    Spinner classSpinner;
    EditText pdfTitleET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_pdf);
        FirebaseApp.initializeApp(thisContext);
        classSpinner= findViewById(R.id.class_spinner);
        ArrayAdapter<CharSequence> adapterString =ArrayAdapter.createFromResource(this,R.array.numbers, R.layout.spinner_list);
        adapterString.setDropDownViewResource(R.layout.spinner_list);
        classSpinner.setAdapter(adapterString);
        classSpinner.setOnItemSelectedListener(this);
        uploadPDF=findViewById(R.id.upload_pdf);
        uploadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfTitleET=findViewById(R.id.pdf_title);
                pdfTitle=pdfTitleET.getText().toString();
                if(className.equals("Class")) {
                    ((TextView) classSpinner.getSelectedView()).setError("Select Class");
                }
                else if(pdfTitle.isEmpty())
                {
                    pdfTitleET.setError("Enter Title");
                }
                else
                {
                    Toast.makeText(thisContext,"class:"+className,Toast.LENGTH_SHORT).show();
                    galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                    // We will be redirected to choose pdf
                    galleryIntent.setType("application/pdf");
                    startActivityForResult(Intent.createChooser(galleryIntent,"Select PDF"), 1);
                }
            }
        });
    }
    ProgressDialog dialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK&&data!=null&&data.getData()!=null) {

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");

            // this will show message uploading
            // while pdf is uploading
            dialog.show();
            imageuri = data.getData();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
            Toast.makeText(this, imageuri.toString(), Toast.LENGTH_SHORT).show();

            // Here we are uploading the pdf in firebase storage with the name of current time
            Toast.makeText(thisContext, pdfTitle, Toast.LENGTH_SHORT).show();

            final StorageReference filepath = storageReference.child("Notes").child(className);
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
                        dialog.dismiss();
                        Uri uri = task.getResult();
                        String myurl;
                        myurl = uri.toString()+"/"+pdfTitle;
                        PdfData pdfData=new PdfData(myurl,pdfTitle);
                        databaseReference.child("Notes").child(className).child(pdfTitle).setValue(pdfData);
                        Toast.makeText(thisContext, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(thisContext, "UploadedFailed", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(thisContext,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        className= parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //((TextView)parent.getSelectedView()).setError("Select Class");
    }
}