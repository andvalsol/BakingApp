package com.example.luthiers.bakingapp.views;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.luthiers.bakingapp.BuildConfig;
import com.example.luthiers.bakingapp.MainActivityViewModel;
import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.adapters.RecipesAdapter;
import com.example.luthiers.bakingapp.entities.Recipe;
import com.example.luthiers.bakingapp.utils.RecipeUtils;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.RecipesOnClickListener {
    
    private int mAppWidgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
    
        
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            //The user has entered this activity to set a determined recipe as a widget
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
    
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
    
    @Override
    protected void onStart() {
        super.onStart();
    
        Log.d("MainActivity.", "MainActivity started");
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
        //Check if the user wants to open the recipe or wants to set the recipe as a widget
        if (mAppWidgetId == 0) {
            //Create an intent to open the RecipeDetailActivity with the extra parcelable Recipe POJO
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        } else {
            RecipeUtils.saveRecipeInSharedPrefs(this, recipe);
            
            createWidgetIntent();
            
            //Finish the activity and all its back stack
            setResult(RESULT_CANCELED);
            finishAffinity();
        }
    }
    
    private void createWidgetIntent() {
        //Create an intent so that we can send the broadcast to the BakingAppWidgetProvider
        Intent intent = new Intent(this, BakingAppWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        //Send to the intent the widgetId again so that we can know which widget was clicked
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, intent);
        
        //Send the broadcast
        sendBroadcast(intent);
    }
}