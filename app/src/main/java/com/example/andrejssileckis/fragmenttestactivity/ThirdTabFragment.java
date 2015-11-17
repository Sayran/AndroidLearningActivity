package com.example.andrejssileckis.fragmenttestactivity;

/**
 * Created by andrejs.sileckis on 10/27/2015.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ThirdTabFragment extends ListFragment {

    private String versions[];
    public  ThirdTabFragment(){
        versions = new String[]{
                "Marshmallow",
                "Lollipop",
                "Kit Kat",
                "Jelly Bean",
                "Ice cream Sandwich",
                "GingerBread",
                "HoneyComb",
                "Froyo"
        };
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, versions);
        setListAdapter(listAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.third_tab_fragment, container, false);
        /*View view = super.onCreateView(inflater, container, savedInstanceState);*/
        return view;
    }
    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {

        /*Toast.makeText(getContext(), getListView().getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();*/
        Bundle bundle = new Bundle();
        bundle.putString("element", getListView().getItemAtPosition(position).toString());
        Intent intent = new Intent("com.example.andrejssileckis.fragmenttestactivity.ResultActivity");
        intent.putExtras(bundle);
        startActivity(intent);
       // startActivity(new Intent("com.example.andrejssileckis.fragmenttestactivity.ResultActivity"));
    }
}

