package com.example.luthiers.bakingapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.luthiers.bakingapp.entities.Recipe;
import com.example.luthiers.bakingapp.repository.network.RecipeNetworkDataSource;
import com.example.luthiers.bakingapp.utils.AppExecutors;
import com.example.luthiers.bakingapp.utils.RecipeUtils;

import java.io.IOException;
import java.util.List;

public class RecipeRepository {
    
    private final RecipeNetworkDataSource mRecipeNetworkDataSource;
    private final AppExecutors mAppExecutors;
    private final MutableLiveData recipes = new MutableLiveData<Recipe>();
    
    public RecipeRepository(AppExecutors appExecutors) {
        mRecipeNetworkDataSource = new RecipeNetworkDataSource();
        mAppExecutors = appExecutors;
    }
    
    public LiveData<List<Recipe>> getRecipes() {
        mAppExecutors.getDiskIO().execute(() -> {
            try {
                //Get the jsonResponse from the RecipeNetworkDataSource
                String jsonResponse = RecipeNetworkDataSource.setupHttpConnection();
                
                //Set the value to the recipes live data. Use postValue since we're using a background thread
                recipes.postValue(RecipeUtils.getRecipesFromJsonResponse(jsonResponse));
                
            } catch (IOException e) {
                recipes.postValue(null);
            }
        });
        
        return recipes;
    }
}