package com.masterji.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AdapterFeedbacks extends FirebaseRecyclerAdapter<FeedbackData,AdapterFeedbacks.ViewHolderFeedback> {

    Context context;
    public AdapterFeedbacks(@NonNull FirebaseRecyclerOptions<FeedbackData> options, Context context) {
        super(options);
        this.context=context;

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderFeedback holder, int position, @NonNull FeedbackData model) {

        Toast.makeText(context,"Your Feedbacks😀",Toast.LENGTH_SHORT).show();

        if(model.getFtext().length()>11)
            holder.desc.setText(model.getFtext().substring(0,11)+"...");
        else if(model.getFtext().length()==0)
            holder.desc.setText("...");
        else
            holder.desc.setText(model.getFtext());
        if(model.getKey().length()>8)
        {
            holder.from.setText(model.getKey().substring(0,8)+"...");
        }
        else
            holder.from.setText(model.getKey());
        holder.feedDelete.setOnClickListener(new View.OnClickListener() {
        private  ProgressDialog deleting;
            @Override
            public void onClick(View v) {
                deleting=new ProgressDialog(context);
                deleting.create();
                deleting.setMessage("Deleting");
                deleting.show();
                DatabaseReference mref= FirebaseDatabase.getInstance().getReference("Feedback").child(getRef(position).getKey());
                mref.removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_SHORT).show();
                                    deleting.dismiss();
                                }
                                else {
                                    deleting.dismiss();
                                    Toast.makeText(context,"Something Went wrong",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        //Glide.with(context).load(model.getImageURI()).centerCrop().into(holder.profile);
        Glide.with(context).load(R.drawable.logo1).centerCrop().into(holder.profile);
        holder.feedbackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,FeedbackRead.class);
                //i.putExtra("imageuri",model.getImageuri());
                i.putExtra("username",model.getKey());
                i.putExtra("description",model.getFtext());
                i.putExtra("position",position);
                context.startActivity(i);
            }
        });
    }
    @NonNull
    @Override
    public ViewHolderFeedback onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderFeedback(LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_recyclerview_view,parent,false));
    }

    public class ViewHolderFeedback extends RecyclerView.ViewHolder{
        private ImageView profile,feedDelete;
        private TextView from,desc;
        private LinearLayout feedbackLayout;
        public ViewHolderFeedback(@NonNull View itemView) {
            super(itemView);
            profile=itemView.findViewById(R.id.feedback_profile);
            from=itemView.findViewById(R.id.username_feedback);
            desc=itemView.findViewById(R.id.feedback_description);
            feedbackLayout=itemView.findViewById(R.id.feedback_view);
            feedDelete=itemView.findViewById(R.id.delete_feedback);
        }
    }
}
