package com.example.andrejssileckis.medialearnactivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MediaDataManager {
    /*final String MEDIA_PATH = Environment.getExternalStorageDirectory()
            .getPath() + "/";*/
    final String MEDIA_PATH = "/storage/sdcard1/";
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<>();
    private ArrayList<HashMap<String, String>> videoList = new ArrayList<>();
    private ArrayList<String> mVideoKeys = new ArrayList<>();
    private ArrayList<String> mAudioKeys = new ArrayList<>();
    private String mp3Pattern = ".mp3";
    private String mAviPattern = ".avi";
    private static String sRequiredAction = "";

    // Constructor
    public MediaDataManager(String string) {
        sRequiredAction = string;
    }

    /**
     * Function to read all mp3 files and store the details in
     * ArrayList
     * */
    public ArrayList<HashMap<String, String>> getPlayList() {
        File home = new File(MEDIA_PATH);
        File[] listFiles = home.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file : listFiles) {
                System.out.println(file.getAbsolutePath());
                if (file.isDirectory()) {
                    if(sRequiredAction.equals("song")) scanDirectory(file);
                    if(sRequiredAction.equals("video")) videoScanDirectory(file);
                } else {
                    if(sRequiredAction.equals("song")) addSongToList(file);
                    if(sRequiredAction.equals("video")) addVideoToList(file);
                }
            }
        }
        // return songs list array
        switch (sRequiredAction) {
            case "song":
                audioKeyBuilder(songsList);
                return songsList;
            case "video":
                videoKeyBuilder(videoList);
                return videoList;
            default:
                return null;
        }
    }

    private void scanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }

                }
            }
        }
    }

    private void addSongToList(File song) {
        if (song.getName().endsWith(mp3Pattern)) {
            HashMap<String, String> songMap = new HashMap<>();
            songMap.put("songTitle",
                    song.getName().substring(0, (song.getName().length() - 4)));
            songMap.put("songPath", song.getPath());

            // Adding each song to SongList
            songsList.add(songMap);
        }
    }

    private void videoScanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        videoScanDirectory(file);
                    } else {
                        addVideoToList(file);
                    }

                }
            }
        }
    }

    private void addVideoToList(File video){
        if (video.getName().endsWith(mAviPattern)) {
            HashMap<String, String> videoMap = new HashMap<>();
            videoMap.put("videoTitle",
                    video.getName().substring(0, (video.getName().length() - 4)));
            videoMap.put("videoPath", video.getPath());

            // Adding each video to VideoList
            videoList.add(videoMap);
        }
    }

    public void videoKeyBuilder(ArrayList<HashMap<String, String>> hashMapArrayList){
        for (HashMap hashMap:hashMapArrayList){
            Set<String> keys = hashMap.keySet();
/*     Toast.makeText(getContext(),hashMap.get(keys.iterator().next())+"",
                Toast.LENGTH_SHORT).show();*/
            mVideoKeys.add(hashMap.get(keys.iterator().next())+"");
        }
    }

    public void audioKeyBuilder(ArrayList<HashMap<String, String>> hashMapArrayList){
        for (HashMap hashMap:hashMapArrayList){
            Set<String> keys = hashMap.keySet();
/*     Toast.makeText(getContext(),hashMap.get(keys.iterator().next())+"",
                Toast.LENGTH_SHORT).show();*/
            mAudioKeys.add(hashMap.get(keys.iterator().next())+"");
        }
    }

    public ArrayList<String> getmAudioKeys() {
        return mAudioKeys;
    }

    public ArrayList<String> getmVideoKeys() {
        return mVideoKeys;
    }
}
