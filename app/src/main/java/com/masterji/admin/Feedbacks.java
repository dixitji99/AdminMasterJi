package com.masterji.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class Feedbacks extends AppCompatActivity {
    AdapterFeedbacks adapterFeedbacks;
    Context thiscontext=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedbacks);
        FirebaseApp.initializeApp(this);
        Toast.makeText(this,"Loading Feeds",Toast.LENGTH_SHORT).show();
        RecyclerView recyclerView= findViewById(R.id.feedback_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(thiscontext));
        FirebaseRecyclerOptions<FeedbackData> options =
                new FirebaseRecyclerOptions.Builder<FeedbackData>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Feedback"), FeedbackData.class)
                        .build();
        adapterFeedbacks=new AdapterFeedbacks(options,thiscontext);
        recyclerView.setAdapter(adapterFeedbacks);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapterFeedbacks.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapterFeedbacks.stopListening();
    }
}