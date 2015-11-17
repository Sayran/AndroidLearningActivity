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

    private ArrayList<ArrayList<Country>> expandListGroups = new ArrayList<ArrayList<Country>>();
    private Context mContext;
    private static ArrayList<Country> countryArrayList;


    public GenericExpandableListAdapter (Context mContext, ArrayList<Country> groups){
        this.mContext = mContext;
        countryArrayList = groups;
        this.expandListGroups.add(countryArrayList);
    }

    public void update(Context mContext,ArrayList<Country> countries){

        this.mContext = mContext;
        countryArrayList = countries;
        this.expandListGroups.add(countryArrayList);

    }

    public GenericExpandableListAdapter(){}

    @Override
    public int getGroupCount() {
        if(expandListGroups.size()>0)
        return expandListGroups.size();
        else return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(expandListGroups.get(groupPosition).size()>0)
        return expandListGroups.get(groupPosition).size();
        else
            return 0;
    }

    @Override
    public int getChildTypeCount() {
        return super.getChildTypeCount();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return expandListGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return expandListGroups.get(groupPosition).get(childPosition);
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
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expand_list_view,null);
        }
        if(isExpanded){
            //Toast.makeText(mContext,"Group expanded.",Toast.LENGTH_SHORT).show();
        }
        else {
            //Toast.makeText(mContext,"Group not expanded.",Toast.LENGTH_SHORT).show();
        }

        TextView textGroup = (TextView) convertView.findViewById
                (R.id.textGroupViewInExpandListView);

        textGroup.setText("Countries");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expand_list_view_child, null);

        }

        final TextView textChild = (TextView) convertView.findViewById
                (R.id.textChildViewInExpandListView);
        if(childPosition != getChildrenCount(groupPosition)){
        /*try {
            if(childPosition == 1){
                textChild.setText(countryArrayList.get(0).getCountry() + " "
                        + countryArrayList.get(0).getCapital());
                Object tag = countryArrayList.get(0);
                Button expandListChildViewButton = (Button) convertView.findViewById
                        (R.id.buttonChildMapViewInExpandListView);
                expandListChildViewButton.setTag(tag);
                expandListChildViewButton.setOnClickListener(this);
                return convertView;
            }else
            {*/
            try{
            Country country = countryArrayList.get(childPosition);
            if(country != null){

                textChild.setText(countryArrayList.get(childPosition).getCountry() + " "
                        + countryArrayList.get(childPosition).getCapital());
            }
                Object tag = countryArrayList.get(childPosition);
        /*Toast.makeText(mContext,tag.toString(),Toast.LENGTH_SHORT).show();*/
                Button expandListChildViewButton = (Button) convertView.findViewById
                        (R.id.buttonChildMapViewInExpandListView);
                expandListChildViewButton.setTag(tag);
                expandListChildViewButton.setOnClickListener(this);
                return convertView;

        }catch (IndexOutOfBoundsException e ){
            Log.e("OutOfBound Exception","Not certain why this error is happening");
        }
            return convertView;
        }else
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void onClick(View v) {

        Country counrtyObj = (Country)v.getTag();
        String geo = new String(counrtyObj.getLatitude() + ", " + counrtyObj.getLongitude());
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
