package com.example.andrejssileckis.medialearnactivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MediaDataStorageClass implements Serializable{
    private ArrayList<HashMap<String, String>> mSongsList = new ArrayList<>();
    private ArrayList<HashMap<String, String>> mVideoList = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getSongsList() {
        return mSongsList;
    }

    public void setSongsList(ArrayList<HashMap<String, String>> mSongsList) {
        this.mSongsList = mSongsList;
    }

    public ArrayList<HashMap<String, String>> getVideoList() {
        return mVideoList;
    }

    public void setVideoList(ArrayList<HashMap<String, String>> mVideoList) {
        this.mVideoList = mVideoList;
    }

}
