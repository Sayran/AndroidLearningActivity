package com.example.andrejssileckis.fragmenttestactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_main, container, false);
       MainActivity mainActivity = (MainActivity) getActivity();
       if(!mainActivity.getReceived()) mainActivity.launchBroadcastListener();
       else view.findViewById(R.id.btn_to_media).setVisibility(View.VISIBLE);

       return view;
    }



}
