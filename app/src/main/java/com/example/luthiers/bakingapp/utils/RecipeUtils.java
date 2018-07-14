package com.example.luthiers.bakingapp.utils;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.luthiers.bakingapp.BuildConfig;
import com.example.luthiers.bakingapp.entities.Recipe;
import com.example.luthiers.bakingapp.pojos.Ingredient;
import com.example.luthiers.bakingapp.pojos.Step;
import com.example.luthiers.bakingapp.testing.SimpleIdlingResource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RecipeUtils {
    //For each recipe
    private static final String ID = "id";
    
    private static final String RECIPE_NAME = "name";
    private static final String RECIPE_LIST_OF_INGREDIENTS = "ingredients";
    private static final String RECIPE_STEPS = "steps";
    private static final String RECIPE_SERVINGS = "servings";
    private static final String RECIPE_IMAGE_URL = "image";
    
    //For each ingredient
    private static final String INGREDIENT_QUANTITY = "quantity";
    private static final String INGREDIENT_MEASURE = "measure";
    private static final String INGREDIENT = "ingredient";
    
    //For each step
    private static final String STEP_SHORT_DESCRIPTION = "shortDescription";
    private static final String STEP_DESCRIPTION = "description";
    private static final String STEP_VIDEO_URL = "videoURL";
    private static final String STEP_THUMBNAIL_URL = "thumbnailURL";
    
    //For testing
    private static SimpleIdlingResource sSimpleIdlingResource;
    
    //Get a recipe pojo from the json string
    public static List<Recipe> getRecipesFromJsonResponse(String jsonResponse) {
        try {
            //Create a JSONArray from the jsonResponse
            JSONArray results = new JSONArray(jsonResponse);
            
            //Create an array of recipes as a container
            ArrayList<Recipe> recipes = new ArrayList<>();
            
            //Iterate over the results
            for (int n = 0; n < results.length(); n++) {
                JSONObject jsonRecipe = results.getJSONObject(n);
                
                //Create an array for the ingredients as a container
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                
                //Get the list of ingredients
                JSONArray jsonIngredients = jsonRecipe.getJSONArray(RECIPE_LIST_OF_INGREDIENTS);
                for (int j = 0; j < jsonIngredients.length(); j++) {
                    JSONObject jsonIngredient = jsonIngredients.getJSONObject(j);
                    
                    //Create an ingredient for each item inside the ingredients JSONArray
                    Ingredient ingredient = new Ingredient(
                            (float) jsonIngredient.getInt(INGREDIENT_QUANTITY), //Cast the ingredient quantity to float since we get something like: 4.3
                            jsonIngredient.getString(INGREDIENT_MEASURE),
                            jsonIngredient.getString(INGREDIENT)
                    );
                    
                    ingredients.add(ingredient);
                }
                
                //Create an array for the steps as a container
                ArrayList<Step> steps = new ArrayList<>();
                
                //Get the list of steps
                JSONArray jsonSteps = jsonRecipe.getJSONArray(RECIPE_STEPS);
                for (int k = 0; k < jsonSteps.length(); k++) {
                    JSONObject jsonStep = jsonSteps.getJSONObject(k);
                    
                    //Create a step for each item inside the steps JSONArray
                    Step step = new Step(
                            jsonStep.getInt(ID),
                            jsonStep.getString(STEP_SHORT_DESCRIPTION),
                            jsonStep.getString(STEP_DESCRIPTION),
                            jsonStep.optString(STEP_VIDEO_URL, ""), //There could be a null value for video url
                            jsonStep.optString(STEP_THUMBNAIL_URL, "") //There could be a null value for image url
                    );
                    
                    steps.add(step);
                }
                
                //Create each recipe object
                Recipe recipe = new Recipe(
                        jsonRecipe.getInt(ID),
                        jsonRecipe.getString(RECIPE_NAME),
                        ingredients,
                        steps,
                        jsonRecipe.getInt(RECIPE_SERVINGS),
                        jsonRecipe.optString(RECIPE_IMAGE_URL, "") //There could be a null value for image url
                );
                
                recipes.add(recipe);
            }
            
            return recipes;
            
        } catch (JSONException e) {
            return null;
        }
    }
    
    /*
     * Since we can not use Room because Room makes all tha calls in background services,
     * We can get the list of ingredients from the SharedPreferences
     * */
    public static Recipe getRecipeFromJson(String jsonRecipe) {
        //Create a new Gson object, Gson is the fastest library to get small sets of Json data
        Gson gson = new Gson();
        
        //Create a TypeToken for Gson to use
        Type type = new TypeToken<Recipe>() {
        }.getType();
        
        return gson.fromJson(jsonRecipe, type);
    }
    
    public static String getJsonFromRecipe(Recipe recipe) {
        //Create a new Gson object, Gson is the fastest library to get small sets of Json data
        Gson gson = new Gson();
    
        return gson.toJson(recipe);
    }
    
    /*
    * For testing purposes we constructed the SimpleIdlingResource
    * */
    
    public static SimpleIdlingResource getSimpleIdlingResourceState() {
        //Check that the simpleIdlingResource instance is not null
        if (sSimpleIdlingResource == null) {
            sSimpleIdlingResource = new SimpleIdlingResource();
        }
        return sSimpleIdlingResource;
    }
    
    public static void setSimpleIdlingResourceIdleState(boolean isIdle) {
        //Check that the simpleIdlingResource is not null
        if (sSimpleIdlingResource == null) {
            sSimpleIdlingResource = new SimpleIdlingResource();
        }
        
        sSimpleIdlingResource.setIdleState(isIdle);
    }
}