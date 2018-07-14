package com.example.luthiers.bakingapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.action.ViewActions.click;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.luthiers.bakingapp.utils.RecipeUtils;
import com.example.luthiers.bakingapp.views.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class) //Use AndroidJUnit4 test runner
public class MainActivityTest {
    
    @Rule //We're going to test the MainActivity
    public final ActivityTestRule<MainActivity> mMainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    
    //We've to create an IdlingResource object, since we're getting data from the network
    private IdlingResource mIdlingResource;
    
    //Set what happens before running the test
    /*
    * Here we've to set the idling resource.
    * */
    @Before
    public void setIdlingResource() {
        //Initialize the mIdlingResource instance
        mIdlingResource = RecipeUtils.getSimpleIdlingResourceState();
        
        //Register the idling resource
        IdlingRegistry.getInstance().register(mIdlingResource);
    }
    
    @Test
    public void recipeClicked_OpenRecipeDetailActivity() {
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click())); //Click position 0
    }
    
    //After the test we need to unregister the idling resource
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}