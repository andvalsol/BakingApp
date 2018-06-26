package com.example.luthiers.bakingapp.views;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import com.example.luthiers.bakingapp.R;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setMainFragment();
    }
    
    private void setMainFragment() {
        MainFragment mainFragment = new MainFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        
        transaction.add(R.id.fragment_container, mainFragment);
        transaction.addToBackStack(null);
        
        // Commit the transaction
        transaction.commit();
    }
}