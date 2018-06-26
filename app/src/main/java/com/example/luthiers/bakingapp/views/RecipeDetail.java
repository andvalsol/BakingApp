package com.example.luthiers.bakingapp.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
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
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        
        ViewPager viewPager = view.findViewById(R.id.pager);
    
        //Add the tabs
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.ingredients));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.steps));
        
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        
        
        //Set the adapter to the view pager
        viewPager.setAdapter(new RecipeDetailPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount()));
        
        return view;
    }
}