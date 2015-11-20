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

    /*FragmentActivity listener;

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.listener = (FragmentActivity)context;

    }
*/    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


}
