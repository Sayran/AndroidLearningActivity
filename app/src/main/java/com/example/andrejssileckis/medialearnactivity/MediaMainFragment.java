package com.example.andrejssileckis.medialearnactivity;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrejssileckis.fragmenttestactivity.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * A placeholder fragment containing a simple view.
 */
public class MediaMainFragment extends Fragment implements View.OnClickListener {

    private Button mPlayButton, mPauseButton, mPrevButton, mNextButton;
    private MediaPlayer mMediaPlayer;
    private double mStartTime = 0;
    private double mEndTime = 0 ;
    private Handler mMyHandler = new Handler();
    private int mForwardTime = 5000;
    private int mBackwardTime = 5000;
    private static boolean ONE_TIME_ONLY = false;
    private SeekBar mSeekBar;
    private TextView mLogoText, mSongNameText, mPassesTimeText, mTotalTimeText;
    private ArrayList<HashMap<String, String>> mSongsList;
    private final MediaDataManager mSongsManager = new MediaDataManager("song");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media_main, container, false);

        initializeView(view);
        SongsFinder songsFinder = new SongsFinder();
        songsFinder.execute();
        return view;
    }



    private Runnable UpdateSongTime = new Runnable(){
        public void run(){
            mStartTime = mMediaPlayer.getCurrentPosition();
            mPassesTimeText.setText(String.format("%d:%d",
                    TimeUnit.MILLISECONDS.toMinutes((long) mStartTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) mStartTime) -
                            TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS
                                    .toMinutes((long) mStartTime)))
            );

            mSeekBar.setProgress((int) mStartTime);
            mMyHandler.postDelayed(this, 100);

        }
    };

    @Override
    public void onClick(View v) {
        int ID = v.getId();
        switch (ID){
            case (R.id.btn_player_play):{
                Toast.makeText(getContext()," Play button pressed", Toast.LENGTH_SHORT).show();
                mMediaPlayer.start();

                mEndTime = mMediaPlayer.getDuration();
                mStartTime = mMediaPlayer.getCurrentPosition();

                if(!ONE_TIME_ONLY){
                    mSeekBar.setMax((int)mEndTime);
                    ONE_TIME_ONLY = true;
                }
                mTotalTimeText.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) mEndTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) mEndTime) -
                                TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS
                                        .toMinutes((long) mEndTime))));

                mPassesTimeText.setText(String.format("%d:%d",
                                TimeUnit.MILLISECONDS.toMinutes((long) mStartTime),
                                TimeUnit.MILLISECONDS.toSeconds((long) mStartTime) -
                                        TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS
                                                .toMinutes((long) mStartTime)))
                );
                mSeekBar.setProgress((int) mStartTime);
                mMyHandler.postDelayed(UpdateSongTime, 100);
                mPauseButton.setEnabled(true);
                mPlayButton.setEnabled(false);
                break;
            }
            case (R.id.btn_player_pause):{
                Toast.makeText(getContext(),"Pause pressed",Toast.LENGTH_SHORT).show();
                mMediaPlayer.pause();
                mPauseButton.setEnabled(false);
                mPlayButton.setEnabled(true);
            }
            case (R.id.btn_player_previous):{
                int tempValue = (int)mStartTime;

                if((tempValue - mBackwardTime) > 0){
                    mStartTime = mStartTime - mBackwardTime;
                    mMediaPlayer.seekTo((int)mStartTime);
                } else{
                    Toast.makeText(getContext(),"You can't scroll back",Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case (R.id.btn_player_next):{
                int tempValue = (int)mStartTime;

                if((tempValue + mForwardTime) <= mEndTime){
                    mStartTime = mStartTime + mForwardTime;
                    mMediaPlayer.seekTo((int)mStartTime);
                }else
                    Toast.makeText(getContext(),"You can't go forward",Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                break;

        }
    }

    public class SongsFinder extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... params) {
            mSongsList = mSongsManager.getPlayList();
            return "All done.";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getContext(),"Song List builded, Found: " + mSongsList.size(),
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void initializeView(View view){

        mLogoText = (TextView) view.findViewById(R.id.textVIew_player_head);
        mSongNameText = (TextView) view.findViewById(R.id.textView_song_name);
        mPassesTimeText = (TextView) view.findViewById(R.id.textView_playtime);
        mTotalTimeText = (TextView) view.findViewById(R.id.textView_song_length);

        mPlayButton = (Button) view.findViewById(R.id.btn_player_play);
        mPlayButton.setOnClickListener(this);
        mPauseButton = (Button) view.findViewById(R.id.btn_player_pause);
        mPauseButton.setOnClickListener(this);
        mPrevButton = (Button) view.findViewById(R.id.btn_player_previous);
        mPrevButton.setOnClickListener(this);
        mNextButton = (Button) view.findViewById(R.id.btn_player_next);
        mNextButton.setOnClickListener(this);

        mMediaPlayer = MediaPlayer.create(getContext(), R.raw.ac_dc_hells_bells);

        mSeekBar = (SeekBar) view.findViewById(R.id.seekBar);
        mSeekBar.setClickable(false);
        mPauseButton.setEnabled(false);
    }
}
