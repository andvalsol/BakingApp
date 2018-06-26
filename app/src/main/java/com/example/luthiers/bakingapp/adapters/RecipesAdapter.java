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
import com.example.luthiers.bakingapp.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {
    
    //Create a list to store the list of recipes
    private List<Recipe> mRecipes = new ArrayList<>();
    private RecipesOnClickListener mRecipesOnClickListener;
    
    public RecipesAdapter(RecipesOnClickListener recipesOnClickListener) {
        mRecipesOnClickListener = recipesOnClickListener;
    }
    
    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recipe_layout, parent, false);
        
        return new RecipesViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        holder.bind(mRecipes.get(position));
    }
    
    @Override
    public int getItemCount() {
        return mRecipes.size();
    }
    
    public void setRecipes(List<Recipe> recipes) {
        mRecipes.addAll(recipes);
        
        notifyDataSetChanged();
    }
    
    class RecipesViewHolder extends RecyclerView.ViewHolder {
        
        private ImageView mRecipeImage;
        private TextView mRecipeName, mServings, mIngredients;
        
        RecipesViewHolder(View itemView) {
            super(itemView);
            
            mRecipeImage = itemView.findViewById(R.id.iv_recipe_image);
            mRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            mServings = itemView.findViewById(R.id.tv_servings);
            mIngredients = itemView.findViewById(R.id.tv_ingredients);
            
            itemView.setOnClickListener(v -> mRecipesOnClickListener.recipesOnClick(mRecipes.get(getAdapterPosition())));
        }
        
        void bind(Recipe recipe) {
            mRecipeName.setText(recipe.getName());
            mServings.setText(String.valueOf(recipe.getServings()));
            mIngredients.setText(String.valueOf(recipe.getIngredients().size()));
            
            //Set up the image using glide
            setGlideImage(mRecipeImage, recipe.getImageUrl());
        }
        
        private void setGlideImage(ImageView view, String imageUrl) {
            Glide.with(view)
                    .load(imageUrl)
                    .into(view);
        }
    }
    
    public interface RecipesOnClickListener {
        void recipesOnClick(Recipe recipe);
    }
}