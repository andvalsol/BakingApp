package com.example.luthiers.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.pojos.Ingredient;
import com.example.luthiers.bakingapp.pojos.Step;
import com.example.luthiers.bakingapp.utils.AsyncTaskCacheBitmap;
import com.example.luthiers.bakingapp.utils.GlideClass;

import java.util.List;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    
    //Create a list to store the ingredients
    private List<Step> mSteps;
    private String mIngredients;
    private int numIngredients;
    private Context mContext;
    private StepsClickListener mStepsClickListener;
    
    public RecipeDetailAdapter(List<Step> steps,
                               List<Ingredient> ingredients,
                               StepsClickListener stepsClickListener,
                               Context context) {
        //Set the steps list and also the number of ingredients
        numIngredients = ingredients.size();
        mSteps = steps;
        
        mContext = context;
        
        mStepsClickListener = stepsClickListener;
        
        //Use the concatenateIngredients to append each ingredient into a single String
        concatenateIngredients(ingredients);
    }
    
    private void concatenateIngredients(List<Ingredient> ingredients) {
        StringBuilder stringBuilder = new StringBuilder();
        
        for (Ingredient ingredient : ingredients) {
            stringBuilder.append(ingredient.getQuantity());
            stringBuilder.append(" ");
            stringBuilder.append(ingredient.getMeasure());
            stringBuilder.append(" of ");
            stringBuilder.append(ingredient.getIngredient());
            stringBuilder.append(System.lineSeparator());
        }
        
        mIngredients = stringBuilder.toString();
    }
    
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        
        switch (viewType) {
            case R.layout.single_ingredient_layout:
                return new IngredientsViewHolder(view);
            
            case R.layout.single_step_layout:
                return new StepsViewHolder(view);
            
            default:
                return null;
        }
    }
    
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof IngredientsViewHolder) {
            ((IngredientsViewHolder) holder).bind(mIngredients, numIngredients);
        } else {
            ((StepsViewHolder) holder).bind(mSteps.get(position));
        }
    }
    
    @Override
    public int getItemCount() {
        return mSteps.size();
    }
    
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return R.layout.single_ingredient_layout;
            
            default:
                return R.layout.single_step_layout;
        }
    }
    
    class StepsViewHolder extends RecyclerView.ViewHolder {
        
        private ImageView mVideoThumbnail;
        private TextView mShortDescription;
        
        StepsViewHolder(View itemView) {
            super(itemView);
            
            mVideoThumbnail = itemView.findViewById(R.id.iv_step);
            mVideoThumbnail.setOnClickListener(v -> {
                
                Log.d("Steps", "The adapter position is: " + getAdapterPosition());
                mStepsClickListener.StepOnClick(mSteps.get(getAdapterPosition() - 1)); //We have to remove one since the first position comes from the ingredients
            });
            
            mShortDescription = itemView.findViewById(R.id.tv_short_description);
        }
        
        void bind(Step step) {
            //Add the short description to the mShortDescription text view
            mShortDescription.setText(step.getShortDescription());
            
            //Set the video thumbnail as a preview for the video
            setVideoThumbnail(step, mVideoThumbnail);
        }
    }
    
    private void setVideoThumbnail(Step step, ImageView imageView) {
        /*
         * There are two url from which we can get the thumbnail from:
         * 1- VideoUrl => using the AsyncTaskCacheBitmap
         * 2- ThumbnailUrl => using Glide by default
         * */
        
        Log.i("Step", "the thumbnail is: " + step.getThumbnailURL() + ", and the video url is: " + step.getVideoURL());
        if (!step.getThumbnailURL().isEmpty()) {
            GlideClass.setVideoThumbnail(step.getThumbnailURL(), imageView);
        }
        else AsyncTaskCacheBitmap.getVideoFrameFromUrl(step.getVideoURL(), imageView);
    }
    
    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        
        private TextView mIngredient;
        private TextView mNumIngredients;
        
        IngredientsViewHolder(View itemView) {
            super(itemView);
            
            mIngredient = itemView.findViewById(R.id.tv_ingredient);
            mNumIngredients = itemView.findViewById(R.id.tv_num_ingredients);
        }
        
        void bind(String ingredients, int numIngredients) {
            //Set the amount of ingredients to the tv_num_ingredients text view
            mNumIngredients.setText(String.valueOf(numIngredients) + mContext.getString(R.string._ingredients));
            
            mIngredient.setText(ingredients);
        }
    }
    
    public interface StepsClickListener {
        
        void StepOnClick(Step step);
    }
}