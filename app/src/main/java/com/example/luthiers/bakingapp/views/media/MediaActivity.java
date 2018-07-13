package com.example.luthiers.bakingapp.views.media;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.pojos.Step;

public class MediaActivity extends AppCompatActivity {
    
    private Fragment mMediaFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_layout);
        
        Log.d("Meedia", "MediaActivity onCreate called");
        
        //For persistence we need to save the fragment state
        //Check if savedInstanceState is not null
        if (savedInstanceState != null) {
            mMediaFragment = getSupportFragmentManager().getFragment(savedInstanceState, "mediaFragment");
        } else {
            mMediaFragment = new MediaFragment();
        }
    
        //Get the step information if a bundle is passed
        Step step = setStep();
    
        //Open the MediaFragment
        openMediaFragment(step);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        
        getSupportFragmentManager().putFragment(outState, "mediaFragment", mMediaFragment);
    }
    
    private Step setStep() {
        //Check if we're getting a bundle
        if (getIntent() != null && getIntent().getExtras().getParcelable("step") != null) {
            return getIntent().getExtras().getParcelable("step");
        }
        
        return null;
    }
    
    private void openMediaFragment(Step step) {
        //Create a new bundle object so that it can be attached to the MediaFragment object
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);
    
        //Add to the mediaFragment object the bundle instance
        mMediaFragment.setArguments(bundle);
        
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_recipe_detail_container, mMediaFragment) //We don't want overlapping fragments
                .commit();
    }
}