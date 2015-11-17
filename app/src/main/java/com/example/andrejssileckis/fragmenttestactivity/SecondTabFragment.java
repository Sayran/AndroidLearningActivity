package com.example.andrejssileckis.fragmenttestactivity;

/**
 * Created by andrejs.sileckis on 10/27/2015.
 */

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;
import static android.support.v4.content.ContextCompat.getDrawable;


public class SecondTabFragment extends Fragment {

    private static final JsonController jsonController = new JsonController();
    private static GenericExpandableListAdapter expandableListAdapter;
    private static ArrayList<Country> countryArrayList ;
    private ExpandableListView expandableListView;
    private static ArrayList<Country> postSearchList;
    private static boolean tabSearchOpened = false;
    private static String searchQuery;
    private static Drawable iconOpenSearch;
    private static Drawable iconCloseSearch;
    private static MenuItem searchAction;
    private static EditText mSearchEt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        if(savedInstanceState == null) {
            expandableListAdapter = new
                    GenericExpandableListAdapter(getContext(), creatingCountryData());
        }
        else{
            postSearchList = savedInstanceState.getParcelableArrayList("FILTERED_COUTRIES");
            countryArrayList = savedInstanceState.getParcelableArrayList("COUNTRIES");
            tabSearchOpened = savedInstanceState.getBoolean("SEARCH_OPENED");
            searchQuery = savedInstanceState.getString("SEARCH_QUERY");
            expandableListAdapter = new GenericExpandableListAdapter(getContext(),
                    postSearchList);}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_tab_fragment, container, false);
        iconOpenSearch = getDrawable(getContext(), R.drawable.ic_menu_search);
        iconCloseSearch = getDrawable(getContext(), R.drawable.ic_menu_close_clear_cancel);
        if(savedInstanceState == null){
            expandableListView = (ExpandableListView)
                    view.findViewById(R.id.location_expand_list);
            countryArrayList = new
                    JsonController().getStructure(getContext(),1);
            expandableListView.setAdapter(expandableListAdapter);
        }
        else {
            /*try {
                countryArrayList = savedInstanceState.getParcelableArrayList("COUNTRIES");
                postSearchList = savedInstanceState.getParcelableArrayList("FILTERED_COUNTRIES");
                tabSearchOpened = savedInstanceState.getBoolean("SEARCH_OPENED");
                searchQuery = savedInstanceState.getString("SEARCH_QUERY");
            }
            catch (Exception e){
                e.printStackTrace();
            }*/
            //onViewStateRestored(savedInstanceState);
            expandableListView = (ExpandableListView)
                    view.findViewById(R.id.location_expand_list);
            expandableListAdapter = new
                    GenericExpandableListAdapter(getContext(), postSearchList);
            expandableListView.setAdapter(expandableListAdapter);

            if(tabSearchOpened){
                openSearchBar(searchQuery);
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("COUTRIES", ((ArrayList<Country>) countryArrayList));
        outState.putParcelableArrayList("FILTERED_COUNTRIES", ((ArrayList<Country>) postSearchList));
        outState.putBoolean("SEARCH_OPENED", tabSearchOpened);
        outState.putString("SEARCH_QUERY", searchQuery);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        expandableListAdapter.notifyDataSetChanged();
    }

    @Override
        public void onViewStateRestored(Bundle savedInstanceState) {
            super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            try {
                countryArrayList = savedInstanceState.getParcelableArrayList("COUNTRIES");
                postSearchList = savedInstanceState.getParcelableArrayList("FILTERED_COUNTRIES");
                tabSearchOpened = savedInstanceState.getBoolean("SEARCH_OPENED");
                searchQuery = savedInstanceState.getString("SEARCH_QUERY");
                expandableListAdapter.notifyDataSetChanged();
            }catch (NullPointerException e){
                Log.e("Null Pointer Exception","Null pointer after attempting to restore data " +
                        "SecondFragment.(onViewStateRestored)");
                e.printStackTrace();
            }
        }
        }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        searchAction = menu.findItem(R.id.action_search);
        super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            if (tabSearchOpened) {
                closeSearchBar();
            } else {
                openSearchBar(searchQuery);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openSearchBar(String queryText){
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.search_bar);
        actionBar.setDisplayShowTitleEnabled(false);
        mSearchEt = (EditText) actionBar.getCustomView().findViewById(R.id.etSearch);
        mSearchEt.addTextChangedListener(new SearchWatcher());
        mSearchEt.setText(queryText);
        mSearchEt.requestFocusFromTouch();

        searchAction.setIcon(iconCloseSearch);
        tabSearchOpened = true;
    }

    public void closeSearchBar(){
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(false);
        searchAction.setIcon(iconOpenSearch);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        tabSearchOpened = false;
    }

    public ArrayList<Country> creatingCountryData() {
        final ArrayList<Country> countries = jsonController.getStructure(getContext());
        return countries;
    }
    public ArrayList<ArrayList<String>> creatingCountryData(ArrayList<Country> data) {
        final ArrayList<Country> countries = data;
        ArrayList<String> countriesNamesList = new ArrayList<>();

        for (int i = 0; i < countries.size(); i++) {
            countriesNamesList.add(countries.get(i).getCountry() + ", " +
                    countries.get(i).getCapital());
        }

        ArrayList<ArrayList<String>> expandableListViewGroups = new ArrayList<ArrayList<String>>();
        expandableListViewGroups.add(countriesNamesList);

        return expandableListViewGroups;
    }

    public class SearchWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            searchQuery = mSearchEt.getText().toString();
            postSearchList = performSearch(countryArrayList, searchQuery);
        }

        @Override
        public void afterTextChanged(Editable s) {
            //expandableListAdapter.update(getContext(), postSearchList);
            expandableListAdapter.notifyDataSetChanged();

        }

    }
    /**
     * Goes through the given list and filters it according to the given query.
     *
     * @param countries list given as search sample
     * @param query to be searched
     * @return new filtered arrayList
     */
    private ArrayList<Country> performSearch(ArrayList<Country> countries, String query) {

        // First we split the query so that we're able
        // to search word by word (in lower case).
        String[] queryByWords = query.toLowerCase().split("\\s+");
        // Empty list to fill with matches.
        ArrayList<Country> countriesFiltered = new ArrayList<Country>();
        if(!query.equals("")){

        // Go through initial releases and perform search.
        /*for (Country country : countries)*/
            for(Iterator<Country> it = countries.iterator(); it.hasNext();){
                Country next = it.next();
            // Content to search through (in lower case).
            String content = (next.getCountry() + " " + next.getCapital()).toLowerCase();
            for (String word : queryByWords) {
                if(content.contains(word)){
                    countriesFiltered.add(next);
                    it.remove();
                }
                else
                    it.remove();
            }
        }
            return countriesFiltered;
        }
        else
        return countries;
    }
}
