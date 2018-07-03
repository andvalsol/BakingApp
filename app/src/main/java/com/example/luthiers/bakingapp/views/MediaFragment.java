package com.example.luthiers.bakingapp.views;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.pojos.Step;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaFragment extends Fragment {
    
    //Create an instance of a Step so that we can insert a step information gotten from a bundle
    private Step mStep;
    private PlayerView mPlayerView;
    private ExoPlayer mExoPlayer;
    private TextView mTvDescription;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_media, container, false);
        
        //Initialize the Exo Player view
        mPlayerView = view.findViewById(R.id.pv_exo_player);
        
        //Check that the mStep instance and the tv_step_description are not null
        mTvDescription = view.findViewById(R.id.tv_step_description);
        
        if (isPortraitMode()) setPortraitView();
        else setLandscapeView();
        
        return view;
    }
    
    public void setStep(Step step) {
        mStep = step;
    }
    
    private boolean isPortraitMode() {
        return mTvDescription != null;
    }
    
    private void setLandscapeView() {
        //Hide the action bar
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }
    
    private void setPortraitView() {
        //Set the description of the video
        if (mStep != null) mTvDescription.setText(mStep.getDescription());
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), new DefaultTrackSelector());
        
        //Bound the player to the player view
        mPlayerView.setPlayer(mExoPlayer);
        
        //Tell Exo Player what we want to play, provide a Factory for data sources and then specify a media source
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "BakingApp"));
        
        ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mStep.getVideoURL()));//Since the url is a string we need to parse it to an Uri
        
        //Prepare the player to start buffer the data and set player to play when ready to begin playback automatically
        mExoPlayer.prepare(extractorMediaSource);
        
        mExoPlayer.setPlayWhenReady(true);
    }
    
    @Override
    public void onStop() {
        super.onStop();
        
        //Release all resources
        mPlayerView.setPlayer(null);
        mExoPlayer.release();
        mExoPlayer = null;
    }
}