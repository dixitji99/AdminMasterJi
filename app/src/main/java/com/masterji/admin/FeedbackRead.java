package com.masterji.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FeedbackRead extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_read);
        Intent data=getIntent();
        ImageView profile=(ImageView)findViewById(R.id.full_profile_feedback);
        //Glide.with(this).load(data.getStringExtra("imageuri")).centerCrop().into(profile);
        Glide.with(this).load(R.drawable.logo1).centerCrop().into(profile);
        TextView username=(TextView)findViewById(R.id.username_full_feedback);
        username.setText(data.getStringExtra("username"));
        TextView desc=(TextView)findViewById(R.id.full_feedback_description);
        desc.setText(data.getStringExtra("description"));
    }
}