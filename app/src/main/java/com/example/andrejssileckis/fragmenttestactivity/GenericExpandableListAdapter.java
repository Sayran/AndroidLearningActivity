package com.example.andrejssileckis.fragmenttestactivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by andrejs.sileckis on 11/10/2015.
 */

public class GenericExpandableListAdapter extends BaseExpandableListAdapter
        implements View.OnClickListener {

    private ArrayList<Continent> mOriginalContinentList;
    private Context mContext;
    private ArrayList<Continent> mChangedList;
    private String mSearchString;


    public GenericExpandableListAdapter (Context mContext, ArrayList<Continent> groups){
        this.mContext = mContext;
        this.mOriginalContinentList = new ArrayList<>();
        this.mOriginalContinentList.addAll(groups);
        this.mChangedList = new ArrayList<>();
        this.mChangedList.addAll(groups);

    }
    
    @Override
    public int getGroupCount() {
        return mChangedList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<Country> countryArrayList = mChangedList.get(groupPosition).getCountryList();
        return countryArrayList.size();
    }

    @Override
    public int getChildTypeCount() {
        return super.getChildTypeCount();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mChangedList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Country> countryArrayList = mChangedList.get(groupPosition).getCountryList();
        Country country = countryArrayList.get(childPosition);
        return country;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Continent continent = (Continent) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expand_list_view,null);
        }

        TextView textGroup = (TextView) convertView.findViewById
                (R.id.textGroupViewInExpandListView);

        textGroup.setText(continent.getName().trim());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {

        Country country = (Country) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expand_list_view_child, null);
        }

        TextView textChild = (TextView) convertView.findViewById
                (R.id.textChildViewInExpandListView);

        textChild.setText(country.getCountry() + " " + country.getCapital());

        Object tag;
        tag = country;
        /*Toast.makeText(mContext,tag.toString(),Toast.LENGTH_SHORT).show();*/
        Button expandListChildViewButton = (Button) convertView.findViewById
                (R.id.buttonChildMapViewInExpandListView);
        expandListChildViewButton.setTag(tag);
        expandListChildViewButton.setOnClickListener(this);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query, Context context){
        this.mSearchString = query.toLowerCase();
        Log.v(String.valueOf(mChangedList.size()), "GenericExpandableListAdapter");
        ArrayList<Continent> tempContinents = new ArrayList<>();
        boolean flag = false;
        //mChangedList.clear();
        /*Toast.makeText(context, mSearchString + " this is current data in search",
                Toast.LENGTH_SHORT).show();*/

        if(mSearchString.isEmpty()){
            mChangedList.clear();
            mChangedList.addAll(mOriginalContinentList);
        }
        else {
            ArrayList<Continent> continents = mChangedList;
            for(Continent continent:continents){
                ArrayList<Country> countryArrayList = continent.getCountryList();
                ArrayList<Country> newCountryArrayList = new ArrayList<>();

                for(Country country:countryArrayList){
                    if(country.getCapital().toLowerCase().contains(mSearchString) ||
                            country.getCountry().toLowerCase().contains(mSearchString)){
                        newCountryArrayList.add(country);
                    }
                }
                if(newCountryArrayList.size() > 0){
                    Continent nContinent = new Continent(continent.getName(),newCountryArrayList);
                    flag = true;
                    tempContinents.add(nContinent);
                }
            }
        }
        if(flag){
            mChangedList.clear();
            mChangedList.addAll(tempContinents);
            tempContinents.clear();
        }
        Log.v(String.valueOf(mChangedList.size()), "GenericExpandableListAdapter");
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

        Country counrtyObj = (Country)v.getTag();
        String geo;
        geo = counrtyObj.getLatitude() + ", " + counrtyObj.getLongitude();
        Toast.makeText(v.getContext(), geo + " .", Toast.LENGTH_SHORT).show();
        // Create a Uri from an intent string. Use the result to create an Intent.
        //Uri gmmIntentUri = Uri.parse("google.streetview:cbll=" + geo);
        String strUri = "http://maps.google.com/maps?q=loc:" + geo +
                " (" + counrtyObj.getCapital() + ")";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        //intent.setClassName("com.google.android.apps.maps",
        // "com.example.andrejssileckis.fragmenttestactivity.MapsActivity");

        v.getContext().startActivity(intent);

    }
}
