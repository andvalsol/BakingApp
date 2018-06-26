package com.example.luthiers.bakingapp.views;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luthiers.bakingapp.MainActivityViewModel;
import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.adapters.RecipesAdapter;
import com.example.luthiers.bakingapp.entities.Recipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements RecipesAdapter.RecipesOnClickListener {
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
    
    
        //Initialize the adapter
        RecipesAdapter recipesAdapter = new RecipesAdapter(this);
    
        //Initialize the recycler view
        RecyclerView recyclerView = view.findViewById(R.id.rv_recipes_list);
        //The size of each item from the recycler won't change, so set the value to true, to let recycler view make optimizations with the layout
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recipesAdapter);
    
        //Initialize the MainActivityViewModel
        MainActivityViewModel mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        //Get the recipes from the MainActivityViewModel
        mainActivityViewModel.getRecipesFromRepository().observe(this, recipesAdapter::setRecipes);
        
        return view;
    }
    
    @Override
    public void recipesOnClick(Recipe recipe) {
        //Create a bundle so we can send the recipe
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.recipe), recipe);
        
        // Create new fragment and transaction
        RecipeDetail recipeDetail = new RecipeDetail();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        
        transaction.replace(R.id.fragment_container, recipeDetail);
        transaction.addToBackStack(null);
        
        // Commit the transaction
        transaction.commit();
    }
}