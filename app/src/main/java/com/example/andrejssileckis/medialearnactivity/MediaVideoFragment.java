package com.example.andrejssileckis.medialearnactivity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.andrejssileckis.fragmenttestactivity.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class MediaVideoFragment extends Fragment {
    private VideoView mVideoView;
    private int mPosition = 0;
    private ProgressDialog mProgressDialog;
    private MediaController mMediaController;
    private MediaDataManager mVideoDataManager = new MediaDataManager("video");
    private ArrayList<HashMap<String, String>> mVideoList;
    private ArrayList<String> mVideoKeys = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_video, container, false);

        if(mMediaController == null) {
            mMediaController = new MediaController(getContext());
        }

        mVideoView = (VideoView) view.findViewById(R.id.videoView);

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle("Video Viewer");
        mProgressDialog.setMessage("Rendering...");
        mProgressDialog.setCancelable(false);

       // mProgressDialog.show();
        videoKeyBuilder(mVideoDataManager.getPlayList());
        /*Toast.makeText(getContext(),videoKeyBuilder(mVideoDataManager.getPlayList()).size()+"",
                Toast.LENGTH_LONG).show();*/
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoPath(mVideoKeys.get(2));

        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mProgressDialog.dismiss();
                mVideoView.seekTo(mPosition);
                if(mPosition == 0){
                    mVideoView.requestFocus();
                    //mVideoView.start();
                }
                else{mVideoView.pause();}
            }
        });

        return view;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            mPosition = savedInstanceState.getInt("Position");
            mVideoView.seekTo(mPosition);
            mVideoView.start();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Position",mVideoView.getCurrentPosition());
    }

    public ArrayList videoKeyBuilder(ArrayList<HashMap<String, String>> hashMapArrayList){
        mVideoList = hashMapArrayList;

        for (HashMap hashMap:mVideoList){
            Set<String> keys = hashMap.keySet();
            Toast.makeText(getContext(),hashMap.get(keys.iterator().next())+"",Toast.LENGTH_SHORT).show();
            mVideoKeys.add(hashMap.get(keys.iterator().next())+"");


        }

        return mVideoKeys;
    }


}
