package com.example.andrejssileckis.fragmenttestactivity;

/**
 * Created by andrejs.sileckis on 10/27/2015.
 */

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Iterator;

import static android.support.v4.content.ContextCompat.getDrawable;

public class SecondTabFragment extends Fragment {

    private static final JsonController JSON_CONTROLLER = new JsonController();
    private static GenericExpandableListAdapter sEpandableListAdapter;
    private static ArrayList<Country> sCountryArrayList ;
    private ExpandableListView mExpandableListView;
    private static ArrayList<Country> sPostSearchList;
    private static boolean sTabSearchOpened = false;
    private static String sSearchQuery;
    private static Drawable sIconOpenSearch;
    private static Drawable sIconCloseSearch;
    private static MenuItem mSearchAction;
    private static EditText mSearchEt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        if(savedInstanceState == null) {
            sEpandableListAdapter = new
                    GenericExpandableListAdapter(getContext(), creatingCountryData());
        }
        else{
            sPostSearchList = savedInstanceState.getParcelableArrayList("FILTERED_COUTRIES");
            sCountryArrayList = savedInstanceState.getParcelableArrayList("COUNTRIES");
            sTabSearchOpened = savedInstanceState.getBoolean("SEARCH_OPENED");
            sSearchQuery = savedInstanceState.getString("SEARCH_QUERY");
            sEpandableListAdapter = new GenericExpandableListAdapter(getContext(),
                    sPostSearchList);}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_tab_fragment, container, false);
        sIconOpenSearch = getDrawable(getContext(), R.drawable.ic_menu_search);
        sIconCloseSearch = getDrawable(getContext(), R.drawable.ic_menu_close_clear_cancel);
        if(savedInstanceState == null){
            mExpandableListView = (ExpandableListView)
                    view.findViewById(R.id.location_expand_list);
            sCountryArrayList = new
                    JsonController().getStructure(getContext(),1);
            mExpandableListView.setAdapter(sEpandableListAdapter);
        }
        else {
              /*  sCountryArrayList = savedInstanceState.getParcelableArrayList("COUNTRIES");
                sPostSearchList = savedInstanceState.getParcelableArrayList("FILTERED_COUNTRIES");
                sTabSearchOpened = savedInstanceState.getBoolean("SEARCH_OPENED");
                sSearchQuery = savedInstanceState.getString("SEARCH_QUERY");
            */
            //onViewStateRestored(savedInstanceState);
            mExpandableListView = (ExpandableListView)
                    view.findViewById(R.id.location_expand_list);
            sEpandableListAdapter = new
                    GenericExpandableListAdapter(getContext(), sPostSearchList);
            mExpandableListView.setAdapter(sEpandableListAdapter);

            if(sTabSearchOpened){
                openSearchBar(sSearchQuery);
            }
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("COUTRIES", sCountryArrayList);
        outState.putParcelableArrayList("FILTERED_COUNTRIES", sPostSearchList);
        outState.putBoolean("SEARCH_OPENED", sTabSearchOpened);
        outState.putString("SEARCH_QUERY", sSearchQuery);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        sEpandableListAdapter.notifyDataSetChanged();
    }

    @Override
        public void onViewStateRestored(Bundle savedInstanceState) {
            super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            try {
                sCountryArrayList = savedInstanceState.getParcelableArrayList("COUNTRIES");
                sPostSearchList = savedInstanceState.getParcelableArrayList("FILTERED_COUNTRIES");
                sTabSearchOpened = savedInstanceState.getBoolean("SEARCH_OPENED");
                sSearchQuery = savedInstanceState.getString("SEARCH_QUERY");
                sEpandableListAdapter.notifyDataSetChanged();
            }catch (NullPointerException e){
                Log.e("Null Pointer Exception","Null pointer after attempting to restore data " +
                        "SecondFragment.(onViewStateRestored)");
                e.printStackTrace();
            }
        }
        }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            if (sTabSearchOpened) {
                closeSearchBar();
            } else {
                openSearchBar(sSearchQuery);
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

        mSearchAction.setIcon(sIconCloseSearch);
        sTabSearchOpened = true;
    }

    public void closeSearchBar(){
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(false);
        mSearchAction.setIcon(sIconOpenSearch);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        sTabSearchOpened = false;
    }

    public ArrayList<Country> creatingCountryData() {
        final ArrayList<Country> countries = JSON_CONTROLLER.getStructure(getContext());
        return countries;
    }
    public ArrayList<ArrayList<String>> creatingCountryData(ArrayList<Country> data) {
        final ArrayList<Country> countries = data;
        ArrayList<String> countriesNamesList = new ArrayList<>();

        for (int i = 0; i < countries.size(); i++) {
            countriesNamesList.add(countries.get(i).getCountry() + ", " +
                    countries.get(i).getCapital());
        }

        ArrayList<ArrayList<String>> mExpandableListViewGroups = new ArrayList<ArrayList<String>>();
        mExpandableListViewGroups.add(countriesNamesList);

        return mExpandableListViewGroups;
    }

    public class SearchWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            sSearchQuery = mSearchEt.getText().toString();
            sPostSearchList = performSearch(sCountryArrayList, sSearchQuery);
        }

        @Override
        public void afterTextChanged(Editable s) {
            //sEpandableListAdapter.update(getContext(), sPostSearchList);
            sEpandableListAdapter.notifyDataSetChanged();

        }

    }
    /**
     * Goes through the given list and filters it according to the given query.
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
