package com.example.luthiers.bakingapp.views.media;

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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.luthiers.bakingapp.R;
import com.example.luthiers.bakingapp.pojos.Step;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
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
    private ProgressBar mProgressBar;
    private long mLastViewedPosition = 0; //Initialize the position to be 0
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        Log.d("Meedia", "MediaFragment onCreate called");
    
        //Get the arguments passed, if there are any
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mStep = bundle.getParcelable("step");
        }
        
        /*We want to save the position last position where the user saw the video
        * in case there's a device orientation
        * */
        //Check that the savedInstanceState is not null
        if (savedInstanceState != null) {
            mLastViewedPosition = savedInstanceState.getLong("lastPosition", 0);
        }
    }
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_media, container, false);
        
        //Initialize the Exo Player view
        mPlayerView = view.findViewById(R.id.pv_exo_player);
        
        //Check that the mStep instance and the tv_step_description are not null
        mTvDescription = view.findViewById(R.id.tv_step_description);
        
        //Initialize the progress bar
        mProgressBar = view.findViewById(R.id.pb_media);
        
        if (isPortraitMode()) setPortraitView();
        else setLandscapeView();
        
        return view;
    }
    
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        
        //Save the last viewed position
        outState.putLong("lastPosition", mLastViewedPosition);
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
    public void onResume() {
        super.onResume();
    
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), new DefaultTrackSelector());
    
        //Bound the player to the player view
        mPlayerView.setPlayer(mExoPlayer);
    
        //Tell Exo Player what we want to play, provide a Factory for data sources and then specify a media source
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "BakingApp"));
    
        ExtractorMediaSource extractorMediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mStep.getVideoURL()));//Since the url is a string we need to parse it to an Uri
    
        //Prepare the exo player to start buffer the data and set player to play when ready to begin playback automatically
        mExoPlayer.prepare(extractorMediaSource);
        //Set the exo player to seek to the mLastViewedPosition, whatever that position is
        mExoPlayer.seekTo(mLastViewedPosition);
        
        mExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
            }
        
            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            }
        
            @Override
            public void onLoadingChanged(boolean isLoading) {
            }
        
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_BUFFERING) {
                    mProgressBar.setVisibility(View.VISIBLE);
                } else {
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        
            @Override
            public void onRepeatModeChanged(int repeatMode) {
            }
        
            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
            }
        
            @Override
            public void onPlayerError(ExoPlaybackException error) {
            }
        
            @Override
            public void onPositionDiscontinuity(int reason) {
            }
        
            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            }
        
            @Override
            public void onSeekProcessed() {
            }
        });
    
        mExoPlayer.setPlayWhenReady(true);
    }
    
    @Override
    public void onPause() {
        //Get the last viewed position
        mLastViewedPosition = mExoPlayer.getCurrentPosition();
    
        //Proceed to release the Exo Player
        releaseExoPlayer();
        
        super.onPause();
    }
    
    private void releaseExoPlayer() {
        mPlayerView.setPlayer(null);
        mExoPlayer.release();
        mExoPlayer = null;
    }
}