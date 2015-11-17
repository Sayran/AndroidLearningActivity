package com.example.andrejssileckis.fragmenttestactivity;

/**
 * Created by andrejs.sileckis on 10/27/2015.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MainActivityFragment mTab1 = new MainActivityFragment();
                return mTab1;
            case 1:
                SecondTabFragment mTab2 = new SecondTabFragment();
                return mTab2;
            case 2:
                ThirdTabFragment mTtab3 = new ThirdTabFragment();
                return mTtab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
