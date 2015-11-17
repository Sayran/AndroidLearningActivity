package com.example.andrejssileckis.fragmenttestactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by andrejs.sileckis on 11/13/2015.
 */
public class ObjOrientedExpandListAdapter extends GenericExpandableListAdapter {
    private Context mContext;
    private ArrayList<Country> mCountryArrayList;
    public final static JsonController JSON_CONTROLLER = new JsonController();

    public ObjOrientedExpandListAdapter(Context mContext,ArrayList<Country> countries){
        this.mContext = mContext;
        this.mCountryArrayList = countries;
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

        textGroup.setText("Sorted Countries");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expand_list_view_child,null);
        }

        final TextView textChild = (TextView) convertView.findViewById
                (R.id.textChildViewInExpandListView);
        textChild.setText(mCountryArrayList.get(childPosition).getCountry() + ", "
                + mCountryArrayList.get(childPosition).getCapital());
        Object tag = mCountryArrayList.get(childPosition);
        //Toast.makeText(mContext,tag.toString(),Toast.LENGTH_SHORT).show();

        Button expandListChildViewButton = (Button) convertView.findViewById
                (R.id.buttonChildMapViewInExpandListView);
        expandListChildViewButton.setTag(tag);
        expandListChildViewButton.setOnClickListener(this);
        return convertView;
    }
}
