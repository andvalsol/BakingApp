package com.example.luthiers.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.pojos.Step;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    
    //Create a list to store the steps for the preparation of the recipe
    private List<Step> mSteps = new ArrayList<>();
    
    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_step_layout, parent, false);
        
        return new StepsViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        holder.bind(mSteps.get(position));
    }
    
    @Override
    public int getItemCount() {
        return mSteps.size();
    }
    
    public void setSteps(List<Step> steps) {
        mSteps.addAll(steps);
        
        notifyDataSetChanged();
    }
    
    class StepsViewHolder extends RecyclerView.ViewHolder {
        
        private TextView mShortDescription;
        private ImageView mVideoThumbnail;
        
        StepsViewHolder(View itemView) {
            super(itemView);
            
            mShortDescription = itemView.findViewById(R.id.tv_short_description);
            mVideoThumbnail = itemView.findViewById(R.id.iv_step);
        }
        
        void bind(Step step) {
            //Set the short description
            mShortDescription.setText(step.getShortDescription());
            
            //Set the image thumbnail
            Glide.with(mVideoThumbnail)
                    .load(step.getThumbnailURL())
                    .into(mVideoThumbnail);
        }
    }
}