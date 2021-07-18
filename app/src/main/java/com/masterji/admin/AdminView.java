package com.masterji.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminView extends AppCompatActivity {
    Context This=this;
    private CardView updateSlider,uploadPDF,feedbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view);
        updateSlider=(CardView) findViewById(R.id.update_slider);
        updateSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(This,UpdateSlider.class);
                This.startActivity(intent);
            }
        });
        uploadPDF=(CardView) findViewById(R.id.upload_pdf);
        uploadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(This,UploadPDF.class);
                This.startActivity(intent);
            }
        });
        feedbacks=(CardView) findViewById(R.id.feedbacks);
        feedbacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(This,Feedbacks.class);
                This.startActivity(intent);
            }
        });
    }
}