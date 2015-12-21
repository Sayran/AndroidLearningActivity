package com.example.andrejssileckis.medialearnactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.andrejssileckis.fragmenttestactivity.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MediaVideoFragment extends Fragment {
    private VideoView mVideoView;
    private int mPosition = 0;
    private MediaController mMediaController;
    private MediaDataManager mVideoDataManager = new MediaDataManager("video");
    private MediaDataStorageClass mMediaDataStorageClass;
    private ArrayList<HashMap<String, String>> mVideoList;
    private ArrayList<String> mVideoKeys = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_video, container, false);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        if(mMediaController == null) {
            mMediaController = new MediaController(getContext());
            mVideoKeys = mVideoDataManager.getmVideoKeys();
        }

        mVideoView = (VideoView) view.findViewById(R.id.videoView);

        /*Toast.makeText(getContext(),videoKeyBuilder(mVideoDataManager.getPlayList()).size()+"",
                Toast.LENGTH_LONG).show();*/
        mVideoView.setMediaController(mMediaController);
        /*mVideoView.setVideoPath(mVideoKeys.get(2));

        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoView.seekTo(mPosition);
                if(mPosition == 0){
                    mVideoView.requestFocus();
                    //mVideoView.start();
                }
                else{mVideoView.pause();}
            }
        });
*/
        IntentFilter intentFilter = new IntentFilter(MediaDataPathBuildService.ACTION);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mediaReceiver, intentFilter);
        return view;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            mPosition = savedInstanceState.getInt("Position");
            mVideoView.seekTo(mPosition);
//            mVideoView.start();
        }
        IntentFilter intentFilter = new IntentFilter(MediaDataPathBuildService.ACTION);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mediaReceiver, intentFilter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Position",mVideoView.getCurrentPosition());
    }

    private BroadcastReceiver mediaReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mMediaDataStorageClass = (MediaDataStorageClass)intent.getSerializableExtra("result");
            Toast.makeText(getContext(),mMediaDataStorageClass.getVideoList().size()+
                    " amount of videos found",Toast.LENGTH_SHORT).show();
        }
    };

    /*public ArrayList videoKeyBuilder(ArrayList<HashMap<String, String>> hashMapArrayList){
        mVideoList = hashMapArrayList;

        for (HashMap hashMap:mVideoList){
            Set<String> keys = hashMap.keySet();
*//*     Toast.makeText(getContext(),hashMap.get(keys.iterator().next())+"",
                Toast.LENGTH_SHORT).show();*//*
            mVideoKeys.add(hashMap.get(keys.iterator().next())+"");
        }
        return mVideoKeys;
    }*/


}
