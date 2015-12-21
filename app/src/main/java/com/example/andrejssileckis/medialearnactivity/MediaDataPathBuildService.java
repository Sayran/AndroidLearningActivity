package com.example.andrejssileckis.medialearnactivity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Created by andrejs.sileckis on 12/1/2015.
 */
public class MediaDataPathBuildService extends Service {
    private static MediaDataManager sMediaDataManager;
    private static MediaDataStorageClass sMediaDataStorage;
/*    private ServiceHandler mServiceHandler;*/
    private LocalBroadcastManager mLocalBroadcastManager;
    public static final String ACTION =
            "com.example.andrejssileckis.medialearnactivity.VideoActivity";
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    @Override
    public void onCreate(){
        super.onCreate();
        Toast.makeText(getApplicationContext(),"Service Created.",Toast.LENGTH_SHORT).show();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
        /*sMediaDataManager = new MediaDataManager("song");
        sMediaDataStorage = new MediaDataStorageClass();
        sMediaDataStorage.setSongsList(sMediaDataManager.getPlayList());*/
        ThreadPoolExecutor threadPoolExecutor= new ThreadPoolExecutor(
                NUMBER_OF_CORES+1,
                NUMBER_OF_CORES+1,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
        );
        Runnable taskToRun = new Runnable() {
            @Override
            public void run() {
/*                sMediaDataManager = new MediaDataManager("song");*/
                sMediaDataStorage = new MediaDataStorageClass();
/*                sMediaDataStorage.setSongsList(sMediaDataManager.getPlayList());*/

                /*Toast.makeText(getApplicationContext(),sMediaDataStorage.getSongsList().size()+"",
                        Toast.LENGTH_LONG).show();*/
                sMediaDataManager = new MediaDataManager("video");
                sMediaDataStorage.setVideoList(sMediaDataManager.getPlayList());
                Intent dataTransferIntent = new Intent
                        ("VIDEO_DATA");
                dataTransferIntent.putExtra("data",sMediaDataStorage.getVideoList());
                sendBroadcast(dataTransferIntent);
                stopSelf();
            }
        };
        threadPoolExecutor.execute(taskToRun);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
