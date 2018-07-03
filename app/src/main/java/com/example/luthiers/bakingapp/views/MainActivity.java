package com.example.luthiers.bakingapp.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.example.luthiers.bakingapp.MainActivityViewModel;
import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.adapters.RecipesAdapter;
import com.example.luthiers.bakingapp.entities.Recipe;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipesOnClickListener {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
    
        //Initialize the adapter
        RecipesAdapter recipesAdapter = new RecipesAdapter(this);
    
        //Initialize the recycler view
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        //The size of each item from the recycler won't change, so set the value to true, to let recycler view make optimizations with the layout
        recyclerView.setHasFixedSize(true);
    
        recyclerView.setLayoutManager(new GridLayoutManager(this, setGridColumns()));
        recyclerView.setAdapter(recipesAdapter);
    
        //Initialize the MainActivityViewModel
        MainActivityViewModel mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        //Get the recipes from the MainActivityViewModel
        mainActivityViewModel.getRecipesFromRepository().observe(this, recipes -> {
            //Double check that the recipes list are not null
            if (recipes != null) {
                recipesAdapter.setRecipes(recipes);
            }
        });
    }
    
    private int setGridColumns() {
        //Set the maximum size for the grid item
        int gridItemMaxSize = getResources().getDimensionPixelSize(R.dimen.grid_item_max_size); //Max grid item is set as 160dp
        
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        
        //Get the screen width size
        int screenWidth = displaymetrics.widthPixels;
        
        int columns = screenWidth / gridItemMaxSize;
        
        //We want to make sure that the are only 3 columns max, since 4 doesn't look well visually
        return columns >= 3 ? 3 : columns;
    }
    
    @Override
    public void recipesOnClick(Recipe recipe) {
        //Create an intent to open the RecipeDetailActivity with the extra parcelable Recipe POJO
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }
}