package com.example.andrejssileckis.fragmenttestactivity;

/**
 * Created by andrejs.sileckis on 10/27/2015.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class ThirdTabFragment extends ListFragment {

    private String mVersions[];
    public  ThirdTabFragment(){
        mVersions = new String[]{
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

        ListAdapter listAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, mVersions);
        setListAdapter(listAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.third_tab_fragment, container, false);
        return view;
    }
    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {

        Bundle bundle = new Bundle();
        bundle.putString("element", getListView().getItemAtPosition(position).toString());
        Intent mStartResultActivityIntent =
                new Intent("com.example.andrejssileckis.fragmenttestactivity.ResultActivity");
        mStartResultActivityIntent.putExtras(bundle);
        startActivity(mStartResultActivityIntent);

    }
}

