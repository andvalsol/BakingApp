package com.example.luthiers.bakingapp.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.adapters.RecipeDetailAdapter;
import com.example.luthiers.bakingapp.entities.Recipe;
import com.example.luthiers.bakingapp.pojos.Step;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment implements RecipeDetailAdapter.StepsClickListener {
    
    private Recipe mRecipe;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Check if there were any arguments passed to this Fragment
        if (getArguments() != null  && getArguments().getParcelable(getActivity().getString(R.string.recipe)) != null) {
            mRecipe = getArguments().getParcelable(getActivity().getString(R.string.recipe));
        }
    }
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recycler_view, container, false);
        
        //Create an instance of the RecipeDetailAdapter
        RecipeDetailAdapter recipeDetailAdapter = new RecipeDetailAdapter(mRecipe.getSteps(), mRecipe.getIngredients(), this);
        
        //Create an instance of the recycler view
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recipeDetailAdapter);
        
        return view;
    }
    
    @Override
    public void StepOnClick(Step step) {
        //Open the MediaFragment
        //Create a bundle so we can send the recipe
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);
    
        // Create new fragment, add the bundle to the fragment
        MediaFragment mediaFragment = new MediaFragment();
        mediaFragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
    
        transaction.replace(R.id.fragment_container, mediaFragment);
        transaction.addToBackStack(null);
    
        // Commit the transaction
        transaction.commit();
    }
}