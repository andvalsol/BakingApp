package com.example.luthiers.bakingapp.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.adapters.RecipeDetailPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetail extends Fragment {
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    
        ViewPager viewPager = view.findViewById(R.id.pager);
        //Set the adapter to the view pager
        viewPager.setAdapter(new RecipeDetailPagerAdapter());
        
        return view;
    }
}