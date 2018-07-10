package com.example.luthiers.bakingapp.views;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.luthiers.bakingapp.R;

/**
 * For the BakingAppWidgetProvider, we want to add a quick access for a certain recipe,
 * so that when the user clicks the widget, it shows the right recipe
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {
    
    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        //Set the widget
        RemoteViews remoteViews = setWidgetIntent(context, appWidgetId);
        remoteViews.setTextViewText(R.id.appwidget_recipe_name, "Nutella Pie");
        
        //Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        
        //Get the intent from the widget
        int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        
        //Get the RemoteView object in order to set the proper text view
        setWidgetIntent(context, widgetId);
    }
    
    private RemoteViews setWidgetIntent(Context context, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        //Create an intent to launch the MainActivity when clicked
        Intent intent = new Intent(context, MainActivity.class);
        //Pass the widgetId to the intent
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        
        //Create a wrapper for the intent, this wrapper is the Pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        
        //Initialize the intent when the widget_layout is clicked
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
        
        return views;
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
        // Enter relevant functionality for when the first widget is created
    }
    
    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}