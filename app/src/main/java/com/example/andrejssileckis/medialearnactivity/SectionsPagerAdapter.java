package com.example.andrejssileckis.medialearnactivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by andrejs.sileckis on 11/26/2015.
 */
/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {

            case 1:
                MediaVideoFragment mediaVideoFragment = new MediaVideoFragment();
                return mediaVideoFragment;
            default:
                MediaMainFragment mediaMainFragment = new MediaMainFragment();
                return mediaMainFragment;
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

}
