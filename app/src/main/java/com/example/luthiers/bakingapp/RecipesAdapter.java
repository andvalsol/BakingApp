package com.example.luthiers.bakingapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.luthiers.bakingapp.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {
    
    //Create a list to store the list of recipes
    private List<Recipe> mRecipes = new ArrayList<>();
    
    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_recipe_layout, parent, false);
        
        return new RecipesViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
        Log.d("Recipes", "onBindViewHolder method called");
        holder.bind(mRecipes.get(position));
    }
    
    @Override
    public int getItemCount() {
        Log.d("Recipes", "getItemCount method called");
        return mRecipes.size();
    }
    
    public void setRecipes(List<Recipe> recipes) {
        Log.d("Recipes", "setRecipes method called!");
        
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
        }
        
        void bind(Recipe recipe) {
            Log.d("Recipes", "Bind method called!");
            
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
}
