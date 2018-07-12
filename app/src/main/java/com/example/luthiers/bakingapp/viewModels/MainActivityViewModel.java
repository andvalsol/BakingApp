package com.example.luthiers.bakingapp.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.luthiers.bakingapp.entities.Recipe;
import com.example.luthiers.bakingapp.repository.RecipeRepository;
import com.example.luthiers.bakingapp.utils.AppExecutors;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    
    private LiveData<List<Recipe>> mRecipes;
    
    public LiveData<List<Recipe>> getRecipesFromRepository() {
        /*
        * This allow us to get the recipes from the repository if it has not been initialized,
        and if it is initialized, then get the current recipes
        * */
        if (mRecipes == null) {
            //Initialize the RecipeRepository
            //Create an AppExecutors instance, even though the MainActivityViewModel is created only once and AppExecutors is a Singleton class
            RecipeRepository recipeRepository = new RecipeRepository(AppExecutors.getInstance());
            
            //Get the movies from the recipeRepository instance
            mRecipes = recipeRepository.getRecipes();
        }
        
        return mRecipes;
    }
}