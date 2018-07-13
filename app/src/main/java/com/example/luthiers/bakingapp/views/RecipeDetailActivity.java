package com.example.luthiers.bakingapp.views;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.adapters.RecipeDetailAdapter;
import com.example.luthiers.bakingapp.entities.Recipe;
import com.example.luthiers.bakingapp.pojos.Step;
import com.example.luthiers.bakingapp.views.media.MediaActivity;
import com.example.luthiers.bakingapp.views.media.MediaFragment;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailAdapter.StepsClickListener {
    
    private FragmentManager mFragmentManager;
    private boolean mIsTwoPane;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Meedia", "RecipeDetailActivity onCreate called");
    
    
        setContentView(R.layout.recipe_detail_layout);
        
        //Set recipe if any bundle is passed to this activity
        Recipe recipe = getRecipe();
        
        //Set the title of the activity to be the recipe name
        setTitle(recipe.getName());
        
        //Create an instance of the RecipeDetailAdapter
        RecipeDetailAdapter recipeDetailAdapter = new RecipeDetailAdapter(recipe.getSteps(),
                recipe.getIngredients(),
                this,
                this);
        
        //Create an instance of the recycler view
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        //Set a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recipeDetailAdapter);
        
        //Instantiate the mFragmentManager
        mFragmentManager = getSupportFragmentManager();
        
        //Check if we have a two pane mode or not
        mIsTwoPane = isTwoPane();
        
        if (mIsTwoPane) {
            openMediaFragment(recipe.getSteps().get(0));
        }
    }
    
    private Recipe getRecipe() {
        //Check if there are any information passed to this activity
        if (getIntent() != null && getIntent().getExtras().getParcelable("recipe") != null) {
            return getIntent().getExtras().getParcelable("recipe");
        }
        
        return null;
    }
    
    private void openMediaFragment(Step step) {
        Log.d("Mediaa", "2openMediaFragment called");
        //Create a new bundle object so that it can be attached to the MediaFragment object
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);
        
        //Create an instance of the MediaFragment
        MediaFragment mediaFragment = new MediaFragment();
        //Add to the mediaFragment object the bundle instance
        mediaFragment.setArguments(bundle);
        
        //Since it's in two pane mode, set the MediaFragment in the fl_recipe_detail_container fragment container
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_recipe_detail_container, mediaFragment) //We need to use replace since we don't want any overlapping
                .commit();
    }
    
    private boolean isTwoPane() {
        return (findViewById(R.id.fl_recipe_detail_container) != null);
    }
    
    @Override
    public void StepOnClick(Step step) {
        //Check if we've a two pane mode or not
        if (mIsTwoPane) openMediaFragment(step);
        else openMediaActivity(step);
    }
    
    private void openMediaActivity(Step step) {
        Intent intent = new Intent(this, MediaActivity.class);
        intent.putExtra("step", step);
        startActivity(intent);
    }
}