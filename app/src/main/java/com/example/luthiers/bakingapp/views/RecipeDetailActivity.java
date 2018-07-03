package com.example.luthiers.bakingapp.views;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.adapters.RecipeDetailAdapter;
import com.example.luthiers.bakingapp.entities.Recipe;
import com.example.luthiers.bakingapp.pojos.Step;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailAdapter.StepsClickListener {
    
    private Recipe mRecipe;
    private FragmentManager mFragmentManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_layout);
    
        //Check if there are any information passed to this activity
        if (getIntent() != null && getIntent().getExtras().getParcelable("recipe") != null) {
            mRecipe = getIntent().getExtras().getParcelable("recipe");
        }
        
        //Create an instance of the RecipeDetailAdapter
        RecipeDetailAdapter recipeDetailAdapter = new RecipeDetailAdapter(mRecipe.getSteps(), mRecipe.getIngredients(), this);
        
        //Create an instance of the recycler view
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        //Set a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recipeDetailAdapter);
        
        //Instantiate the mFragmentManager
        mFragmentManager = getSupportFragmentManager();
        
        //Check if we have a two pane mode or not
        boolean isTwoPane = isTwoPane();
        
        if (isTwoPane) {
            addMediaFragment(mRecipe.getSteps().get(0));
        }
    }
    
    private void addMediaFragment(Step step) {
        //Create an instance of the MediaFragment
        MediaFragment mediaFragment = new MediaFragment();
        //Pass the step to the MediaFragment instance, by default pass the first element
        mediaFragment.setStep(step);
        
        //Since it's in two pane mode, set the MediaFragment in the fl_recipe_detail_container fragment container
        mFragmentManager.beginTransaction()
                .add(R.id.fl_recipe_detail_container, mediaFragment)
                .commit();
    }
    
    private boolean isTwoPane() {
        return (findViewById(R.id.ll_recipe_detail_land) != null);
    }
    
    @Override
    public void StepOnClick(Step step) {
        addMediaFragment(step);
    }
}