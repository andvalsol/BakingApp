package com.example.luthiers.bakingapp.views;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.entities.Recipe;
import com.example.luthiers.bakingapp.pojos.Ingredient;
import com.example.luthiers.bakingapp.utils.RecipeUtils;

import java.util.List;

/**
 * For the BakingAppWidgetProvider, we want to add a quick access for a certain recipe,
 * so that when the user clicks the widget, it shows the right recipe
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {
    
    private RemoteViews mRemoteViews;
    private int mAppWidgetId = -1; //Set the appWidgetId to be -1
    
    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        mAppWidgetId = appWidgetId;
    
        Log.d("WidgetId", "1The widgetId is: " + mAppWidgetId);
        //Set the widget
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        
        //Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, mRemoteViews);
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        
        //Get the RemoteView object in order to set the proper text view
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        
        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_ENABLED)) {
            //Reset value for mAppWidgetId
            mAppWidgetId = -1;
    
            Log.d("WidgetId", "2The widgetId is: " + mAppWidgetId);
            
        } else if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            Log.d("WidgetId", "3The widgetId is: " + mAppWidgetId);
    
            if (mAppWidgetId != -1) {
                Intent intent1 = new Intent(context, MainActivity.class);
                //Pass the widgetId to the intent
                intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                //We need to create a new task as we create the activity
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                
                context.startActivity(intent1);
            } else {
                //Get the correspond recipe from the intent and the widgetId
                String jsonRecipe = intent.getExtras().getString("recipe");
                int widgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
                
                //Get the recipe from the json string
                Recipe recipe = RecipeUtils.getRecipeFromJson(jsonRecipe);
                
                //The gotten object can be null
                if (recipe != null) {
                    //Set the views for the widget
                    Log.d("WidgetId", "3The widgetId is: " + widgetId);
    
                    setRemoteViews(recipe , context, widgetId);
                    
                    //Now that we got the recipe and set the proper name for the recipe we need to update the widget
                    AppWidgetManager.getInstance(context).updateAppWidget(
                            new ComponentName(context, BakingAppWidgetProvider.class), mRemoteViews);
                }
            }
        }
    }
    
    private void setRemoteViews(Recipe recipe, Context context, int widgetId) {
        PendingIntent pendingIntent = setPendingIntent(recipe, context, widgetId);
        
        //Initialize the intent when the widget_layout is clicked
        mRemoteViews.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
        
        //Set the title for the recipe widget
        mRemoteViews.setTextViewText(R.id.tv_widget_recipe_name, recipe.getName());
        
        //Set the ingredients for the recipe widget
        mRemoteViews.setTextViewText(R.id.tv_widget_recipe_ingredients, setIngredients(recipe.getIngredients()));
    }
    
    private PendingIntent setPendingIntent(Recipe recipe, Context context, int widgetId) {
        Log.d("WidgetId", "The widgetId is: " + widgetId);
        
        Intent intent1 = new Intent(context, MainActivity.class);
        //Pass the widgetId to the intent
        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        //Add the recipe to the intent
        intent1.putExtra("recipe", recipe);
        
        //Create a wrapper for the intent, this wrapper is the Pending intent
        return PendingIntent.getActivity(context, 0, intent1, 0);
    }
    
    private StringBuilder setIngredients(List<Ingredient> ingredients) {
        StringBuilder ingredientsList = new StringBuilder();
        
        for (Ingredient ingredient : ingredients) {
            ingredientsList.append(ingredient.getIngredient());
            //Add a new line for each ingredient
            ingredientsList.append(System.lineSeparator());
        }
        
        return ingredientsList;
    }
    
    /*
     * Called when a widget is first created, and when the update time milliseconds goes to 0
     * */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            Log.d("WidgetId", "5The widgetId is: " + appWidgetId);
    
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
    
    @Override
    public void onEnabled(Context context) {
    }
    
    @Override
    public void onDisabled(Context context) {
    }
}