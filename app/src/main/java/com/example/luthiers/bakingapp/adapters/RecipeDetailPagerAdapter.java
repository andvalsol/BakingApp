package com.example.luthiers.bakingapp.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.luthiers.bakingapp.entities.Recipe;
import com.example.luthiers.bakingapp.views.recipeDetails.IngredientsFragment;
import com.example.luthiers.bakingapp.views.recipeDetails.StepsFragment;

public class RecipeDetailPagerAdapter extends FragmentStatePagerAdapter {
    
    private int mNumOfTabs;
    private Recipe mRecipe;
    
    public RecipeDetailPagerAdapter(FragmentManager fm, int numOfTabs, Recipe recipe) {
        super(fm);
        
        this.mNumOfTabs = numOfTabs;
        mRecipe = recipe;
    }
    
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    
    @Override
    public Fragment getItem(int position) {
        //Create a generic bundle
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", mRecipe);
        
        switch (position) {
            case 0:
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setArguments(bundle);
                return ingredientsFragment;
            case 1:
                StepsFragment stepsFragment = new StepsFragment();
                stepsFragment.setArguments(bundle);
                return stepsFragment;
            default:
                return null;
        }
    }
}