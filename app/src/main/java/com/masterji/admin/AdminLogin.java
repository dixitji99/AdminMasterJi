package com.masterji.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AdminLogin extends AppCompatActivity {
    private Button adminloginbtn;
    private String username,adminpassword;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private ProgressDialog pd;
    private EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Admin");
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        adminloginbtn = findViewById(R.id.studentloginbtn);
        pd = new ProgressDialog(this);
        adminloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkvalidation();
                //Toast.makeText(Tutee_Login.this, "clicked..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkvalidation() {

        username = email.getText().toString().trim();
        adminpassword = password.getText().toString().trim();
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable())
        {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.internet_connection_alert);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            Button button = dialog.findViewById(R.id.retrybtn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });
            dialog.show();
        }
        else{
            if(username.isEmpty()){
                email.setError("Empty");
                email.requestFocus();
            }else if(adminpassword.isEmpty()){
                password.setError("Empty");
                password.requestFocus();
            }else {
                signin();
            }
        }


    }

    private void signin() {
        pd.setMessage("Signing...");
        pd.show();
        mAuth.signInWithEmailAndPassword(username,adminpassword)
                .addOnCompleteListener(AdminLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            pd.dismiss();
                            email.setText("");
                            password.setText("");
                            email.requestFocus();
                            startActivity(new Intent(AdminLogin.this,AdminView.class));
                            finish();
                        }else{
                            pd.dismiss();
                            email.setText("");
                            password.setText("");
                            email.requestFocus();
                            Toast.makeText(AdminLogin.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}