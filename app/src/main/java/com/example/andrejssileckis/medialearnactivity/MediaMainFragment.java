package com.example.andrejssileckis.medialearnactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrejssileckis.fragmenttestactivity.R;

/**
 * Created by andrejs.sileckis on 11/25/2015.
 */
/**
 * A placeholder fragment containing a simple view.
 */
public class MediaMainFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_media_main, container, false);
    }
}
