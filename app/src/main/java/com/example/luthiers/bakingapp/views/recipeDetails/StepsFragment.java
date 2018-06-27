package com.example.luthiers.bakingapp.views.recipeDetails;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.adapters.StepsAdapter;
import com.example.luthiers.bakingapp.entities.Recipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsFragment extends Fragment {
    
    private Recipe mRecipe;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Check that we get a not null bundle
        if (getArguments() != null && getArguments().getParcelable("recipe") != null) {
            mRecipe = getArguments().getParcelable("recipe");
        }
    }
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        
        //Initialize an instance of the StepsAdapter
        StepsAdapter stepsAdapter = new StepsAdapter();
        
        //Initialize an instance the recycler_view
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(stepsAdapter);
        
        //Set the ingredients list to the stepsAdapter instance
        stepsAdapter.setSteps(mRecipe.getSteps());
        
        return view;
    }
}