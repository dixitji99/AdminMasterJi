package com.masterji.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class AdapterUpdateSlider extends FirebaseRecyclerAdapter<ImageData,AdapterUpdateSlider.ViewHolderForUS> {

    Context context;
    public AdapterUpdateSlider(@NonNull FirebaseRecyclerOptions<ImageData> options, Context context) {
        super(options);
        this.context=context;
        Toast.makeText(context,"Fetching Sliders",Toast.LENGTH_SHORT).show();
    }

    private ProgressDialog deleting;
    @Override
    protected void onBindViewHolder(@NonNull ViewHolderForUS holder, int position, @NonNull ImageData model) {
        Glide.with(context).load(model.getUrl()).centerCrop().into(holder.sliderImage);
        holder.deleteSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleting=new ProgressDialog(context);
                deleting.create();
                deleting.setMessage("Deleting");
                deleting.show();
                DatabaseReference mref= FirebaseDatabase.getInstance().getReference("Slider").child(getRef(position).getKey());
                mref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ImageData uriToDelete =snapshot.getValue(ImageData.class);
                        StorageReference storageReference= FirebaseStorage.getInstance().getReferenceFromUrl(uriToDelete.getUrl());
                        storageReference.delete();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
    }

    @NonNull
    @Override
    public ViewHolderForUS onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderForUS(LayoutInflater.from(parent.getContext()).inflate(R.layout.update_slider_recycler_view,parent,false));
    }

    public class ViewHolderForUS extends RecyclerView.ViewHolder {
        ImageView sliderImage,deleteSlider;
        public ViewHolderForUS(@NonNull View itemView) {
            super(itemView);
            deleteSlider=itemView.findViewById(R.id.delete_slider);
            sliderImage=itemView.findViewById(R.id.slider_image);
        }
    }
}
