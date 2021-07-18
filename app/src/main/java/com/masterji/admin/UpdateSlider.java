package com.masterji.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpdateSlider extends AppCompatActivity {
    Context thisContext = this;
    SwipeRefreshLayout refreshUpdateSlider;
    ImageView addSlider;
    RecyclerView recyclerViewUpdateSlider;
    List<ImageData> sliderObjects;
    AdapterUpdateSlider adapterUpdateSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_slider);
        FirebaseApp.initializeApp(thisContext);
        sliderObjects=new ArrayList<ImageData>();
        recyclerViewUpdateSlider = findViewById(R.id.update_slider_recyclerview);
        recyclerViewUpdateSlider.setLayoutManager(new LinearLayoutManager(thisContext));
        refreshUpdateSlider = findViewById(R.id.refresh_update_slider);
        refreshUpdateSlider.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapterUpdateSlider.startListening();
                Toast.makeText(thisContext, "Refreshed", Toast.LENGTH_SHORT).show();
                refreshUpdateSlider.setRefreshing(false);
            }
        });
        //recylerviewadapter
        FirebaseRecyclerOptions<ImageData> options =
                new FirebaseRecyclerOptions.Builder<ImageData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Slider"), ImageData.class)
                        .build();
        adapterUpdateSlider=new AdapterUpdateSlider(options,thisContext);
        recyclerViewUpdateSlider.setAdapter(adapterUpdateSlider);
        addSlider = findViewById(R.id.add_slider);
        addSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent thistoAddslider=new Intent(thisContext,AddSlider.class);
                startActivity(thistoAddslider);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapterUpdateSlider.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapterUpdateSlider.stopListening();
    }
}