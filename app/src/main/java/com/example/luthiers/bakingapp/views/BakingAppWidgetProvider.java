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
import com.example.luthiers.bakingapp.utils.RecipeUtils;

/**
 * For the BakingAppWidgetProvider, we want to add a quick access for a certain recipe,
 * so that when the user clicks the widget, it shows the right recipe
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {
    
    private RemoteViews mRemoteViews;
    
    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        //Set the widget
        mRemoteViews = setWidgetIntent(context, appWidgetId);
        
        //Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, mRemoteViews);
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        
        //Get the intent from the widget
        int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        
        //Get the RemoteView object in order to set the proper text view
        mRemoteViews = setWidgetIntent(context, widgetId);
        
        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            
            /*Get the correspond recipe from SharedPreferences, it should be better to use Room, but Room calls are all
             * made in background services
             * */
            Recipe recipe = RecipeUtils.getRecipe(context);
            
            //The gotten object can be null
            if (recipe != null) {
                mRemoteViews.setTextViewText(R.id.appwidget_recipe_name, recipe.getName());
                
                //Now that we got the recipe and set the proper name for the recipe we need to update the widget
                AppWidgetManager.getInstance(context).updateAppWidget(
                        new ComponentName(context, BakingAppWidgetProvider.class), mRemoteViews);
            }
        } else if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_ENABLED)) {
            Intent intent1 = createIntentForWidget(context, widgetId);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //We need to create a new task as we create the activity
            context.startActivity(intent1);
        }
    }
    
    private RemoteViews setWidgetIntent(Context context, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        
        //Create a wrapper for the intent, this wrapper is the Pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, createIntentForWidget(context, appWidgetId), 0);
        
        //Initialize the intent when the widget_layout is clicked
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
        
        return views;
    }
    
    private Intent createIntentForWidget(Context context, int appWidgetId) {
        //Create an intent to launch the MainActivity when clicked
        Intent intent = new Intent(context, MainActivity.class);
        //Pass the widgetId to the intent
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        
        return intent;
    }
    
    /*
     * Called when a widget is first created, and when the update time milliseconds goes to 0
     * */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
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