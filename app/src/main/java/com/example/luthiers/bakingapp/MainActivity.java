package com.example.luthiers.bakingapp;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        //Initialize the adapter
        RecipesAdapter recipesAdapter = new RecipesAdapter();
        
        //Initialize the recycler view
        RecyclerView recyclerView = findViewById(R.id.rv_recipes_list);
        //The size of each item from the recycler won't change, so set the value to true, to let recycler view make optimizations with the layout
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recipesAdapter);
        
        //Initialize the MainActivityViewModel
        MainActivityViewModel mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        //Get the recipes from the MainActivityViewModel
        mainActivityViewModel.getRecipesFromRepository().observe(this, recipes -> {
            Log.d("Recipes", "Adding recipes to the adapter");
            recipesAdapter.setRecipes(recipes);
        });
    }
}