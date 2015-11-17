package com.example.andrejssileckis.fragmenttestactivity;

import java.util.ArrayList;

/**
 * Created by andrejs.sileckis on 11/17/2015.
 */
public class Continent {
    private String mName;
    private ArrayList<Country> mCountryList = new ArrayList<Country>();

    public Continent(String mName, ArrayList<Country> mCountryList) {
        super();
        this.mName = mName;
        this.mCountryList = mCountryList;
    }
    public String getName() {
        return mName;
    }
    public void setName(String mName) {
        this.mName = mName;
    }
    public ArrayList<Country> getCountryList() {
        return mCountryList;
    }
    public void setCountryList(ArrayList<Country> mCountryList) {
        this.mCountryList = mCountryList;
    }
}
