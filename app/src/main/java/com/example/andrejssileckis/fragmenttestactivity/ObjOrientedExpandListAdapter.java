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
    private Context context;
    private ArrayList<Country> countryArrayList;
    JsonController jsonController = new JsonController();

    public ObjOrientedExpandListAdapter(Context context,ArrayList<Country> countries){
        this.context = context;
        this.countryArrayList = countries;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expand_list_view,null);
        }
        if(isExpanded){
            //Toast.makeText(context,"Group expanded.",Toast.LENGTH_SHORT).show();
        }
        else {
            //Toast.makeText(context,"Group not expanded.",Toast.LENGTH_SHORT).show();
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
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expand_list_view_child,null);
        }

        final TextView textChild = (TextView) convertView.findViewById
                (R.id.textChildViewInExpandListView);
        textChild.setText(countryArrayList.get(childPosition).getCountry() + ", "
                + countryArrayList.get(childPosition).getCapital());
        Object tag = (Country) countryArrayList.get(childPosition);
        //Toast.makeText(context,tag.toString(),Toast.LENGTH_SHORT).show();

        Button expandListChildViewButton = (Button) convertView.findViewById
                (R.id.buttonChildMapViewInExpandListView);
        expandListChildViewButton.setTag(tag);
        expandListChildViewButton.setOnClickListener(this);
        return convertView;
    }
}
