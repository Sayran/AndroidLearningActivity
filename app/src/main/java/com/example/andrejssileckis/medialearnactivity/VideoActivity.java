package com.example.andrejssileckis.medialearnactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.andrejssileckis.fragmenttestactivity.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class VideoActivity extends AppCompatActivity {

    private static final int AUTO_HIDE_DELAY_MILLIS = 800;
    CustomMediaController customMediaController;

    private VideoView mVideoView;
    private int mPosition = 0;
    private MediaController mMediaController;
    private MediaDataManager mVideoDataManager = new MediaDataManager("video");
    private ArrayList<HashMap<String, String>> mVideoList = new ArrayList<>();
    private ArrayList<String> mVideoKeys = new ArrayList<>();
    private static boolean mIsActivityPaused = false;
    private static boolean mIsReceived = false;
    private ImageButton mTestViewButton;
    private View mControlsView;
    private VideoActivity.BroadcastListener mBroadcastListener;

    private static final int UI_HIDE_OPTIONS = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);
        setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mVideoList = ((ArrayList<HashMap<String, String>>)
                getIntent().getSerializableExtra("data"));
        /*mBroadcastListener = new BroadcastListener();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("VIDEO_DATA");
        registerReceiver(mBroadcastListener, intentFilter);*/

        mControlsView = findViewById(R.id.fullscreen_content_controls);
//        Button testButton = (Button) findViewById(R.id.testButton);
        mVideoView = (VideoView) findViewById(R.id.videoView);
        mVideoView.setOnSystemUiVisibilityChangeListener(new SysUiVisibilityListener());

        if(mMediaController == null) {
           // mMediaController = new MediaController(this);
            customMediaController = new CustomMediaController(this);
            mMediaController = customMediaController;
/*            if(!mTestViewButton.isShown()) mTestViewButton.setVisibility(View.VISIBLE);
            else mTestViewButton.setVisibility(View.INVISIBLE);*/
        }
        if(mVideoList!=null && mVideoList.size()!=0  ) {
            mMediaController.setAnchorView(mVideoView);
            mVideoView.setMediaController(mMediaController);

            mVideoKeys = videoKeyBuilder(mVideoList);
            mVideoView.setVideoPath(mVideoKeys.get(2));

            mVideoView.requestFocus();
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mVideoView.seekTo(mPosition);
                    if (mPosition == 0) {
                        mVideoView.requestFocus();
                        // mVideoView.start();
                    } else {
                        mVideoView.pause();
                    }
                }
            });
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mMediaController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSystemUiVisibility(UI_HIDE_OPTIONS);
            }
        });

        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSystemUiVisibility(UI_HIDE_OPTIONS);
            }
        });

        this.getWindow().getDecorView().setSystemUiVisibility(UI_HIDE_OPTIONS);
    }

    @Override
    protected void onPause() {
        mIsActivityPaused = true;
        super.onPause();
    }

    @Override
    protected void onResume() {
        mIsActivityPaused = false;
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if(mBroadcastListener != null) unregisterReceiver(mBroadcastListener);
        super.onDestroy();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null) {
            mIsReceived = true;
            mVideoView.seekTo(savedInstanceState.getInt("Position"));
            mIsActivityPaused = false;
//            mVideoView.start();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Position", mVideoView.getCurrentPosition());
    }
    private class SysUiVisibilityListener implements View.OnSystemUiVisibilityChangeListener {
        @Override
        public void onSystemUiVisibilityChange(int visibility) {
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mVideoView.setSystemUiVisibility(UI_HIDE_OPTIONS);
                        }
                    });
                }
            }, AUTO_HIDE_DELAY_MILLIS);
        }
    }

    public ArrayList videoKeyBuilder(ArrayList<HashMap<String, String>> hashMapArrayList){
        mVideoList = hashMapArrayList;

        for (HashMap hashMap:mVideoList){
            Set<String> keys = hashMap.keySet();
/*     Toast.makeText(getBaseContext(),hashMap.get(keys.iterator().next())+"",
                Toast.LENGTH_SHORT).show();*/
            mVideoKeys.add(hashMap.get(keys.iterator().next())+"");
        }
        return mVideoKeys;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class BroadcastListener extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        mVideoList = ((ArrayList<HashMap<String, String>>)
                intent.getSerializableExtra("data"));
        if(mVideoList!=null) {
            Toast.makeText(context, mVideoList.size() +
                    " amount of videos found", Toast.LENGTH_SHORT).show();
            mIsReceived = true;
        }
        }
    }

}
