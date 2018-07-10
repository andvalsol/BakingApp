package com.example.luthiers.bakingapp.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.pojos.Step;

public class MediaActivity extends AppCompatActivity {
    
    private Step mStep;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_layout);
        
        //Get the step information if a bundle is passed
        setStep();
    
        //Open the MediaFragment
        openMediaFragment();
    }
    
    private void setStep() {
        //Check if we're getting a bundle
        if (getIntent() != null && getIntent().getExtras().getParcelable("step") != null) {
            mStep = getIntent().getExtras().getParcelable("step");
        }
    }
    
    private void openMediaFragment() {
        //Create an instance of the MediaFragment
        MediaFragment mediaFragment = new MediaFragment();
        //Pass the step to the MediaFragment instance, by default pass the first element
        mediaFragment.setStep(mStep);
        
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, mediaFragment)
                .commit();
    }
}