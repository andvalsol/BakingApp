package com.example.luthiers.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.example.luthiers.bakingapp.views.recipeDetails.IngredientsFragment;
import com.example.luthiers.bakingapp.views.recipeDetails.StepsFragment;

public class RecipeDetailPagerAdapter extends FragmentStatePagerAdapter {
    
    private int mNumOfTabs;
    
    public RecipeDetailPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        
        this.mNumOfTabs = numOfTabs;
    }
    
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IngredientsFragment();
            case 1:
                return new StepsFragment();
            default:
                return null;
        }
    }
}
