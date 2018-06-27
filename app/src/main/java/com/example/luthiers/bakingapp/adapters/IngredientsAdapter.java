package com.example.luthiers.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.pojos.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {
    
    //Create a list to store the ingredients
    private List<Ingredient> mIngredients = new ArrayList<>();
    
    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_ingredient_layout, parent, false);
        
        return new IngredientsViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.bind(mIngredients.get(position));
    }
    
    @Override
    public int getItemCount() {
        return mIngredients.size();
    }
    
    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients.addAll(ingredients);
        
        notifyDataSetChanged();
    }
    
    class IngredientsViewHolder extends RecyclerView.ViewHolder {
        
        private TextView mIngredient;
        
        IngredientsViewHolder(View itemView) {
            super(itemView);
            
            mIngredient = itemView.findViewById(R.id.tv_ingredient);
        }
        
        void bind(Ingredient ingredient) {
            mIngredient
                    .setText(
                            String.format(mIngredient.getContext().getString(R.string.ingredient_format),
                                    ingredient.getQuantity().toString(),
                                    ingredient.getMeasure(),
                                    ingredient.getIngredient())
                    );
        }
    }
}