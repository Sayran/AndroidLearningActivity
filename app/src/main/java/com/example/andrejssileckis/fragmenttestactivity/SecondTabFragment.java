package com.example.andrejssileckis.fragmenttestactivity;

/**
 * Created by andrejs.sileckis on 10/27/2015.
 */

import android.app.SearchManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.getDrawable;

public class SecondTabFragment extends Fragment implements
        SearchView.OnCloseListener, SearchView.OnQueryTextListener{

    private static final JsonController JSON_CONTROLLER = new JsonController();
    private GenericExpandableListAdapter mEpandableListAdapter;
    private static ArrayList<Continent> sContinentArrayList  = new ArrayList<>();
    private ExpandableListView mExpandableListView;
    private static ArrayList<Continent> sPostSearchContinetList = new ArrayList<>();
    private static boolean sTabSearchOpened = false;
    private static Drawable sIconOpenSearch;
    private static Drawable sIconCloseSearch;
    private static MenuItem mSearchAction;
    private SearchView mSearchView;
    private SearchManager mSearchManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_tab_fragment, container, false);
        sIconOpenSearch = getDrawable(getContext(), R.drawable.ic_menu_search);
        sIconCloseSearch = getDrawable(getContext(), R.drawable.ic_menu_close_clear_cancel);
/*        mSearchManager = (SearchManager) getActivity()
                .getSystemService(getContext().SEARCH_SERVICE);
        mSearchView.setSearchableInfo(mSearchManager.getSearchableInfo(getActivity()
                .getComponentName()));*/

        sContinentArrayList = JSON_CONTROLLER.getStructure(getContext());
        this.mEpandableListAdapter = new GenericExpandableListAdapter(getContext()
                , sContinentArrayList);
        mExpandableListView = (ExpandableListView) view
                .findViewById(R.id.location_expand_list_unique);

        mExpandableListView.setAdapter(mEpandableListAdapter);
        expandAll();
       /* if(savedInstanceState == null){
            mExpandableListView = (ExpandableListView)
                    view.findViewById(R.id.location_expand_list);

            mExpandableListView.setAdapter(mEpandableListAdapter);
        }
        else {
                sContinentArrayList = savedInstanceState.getParcelableArrayList("COUNTRIES");
                sPostSearchContinetList = savedInstanceState.getParcelableArrayList("FILTERED_COUNTRIES");
                sTabSearchOpened = savedInstanceState.getBoolean("SEARCH_OPENED");
                sSearchQuery = savedInstanceState.getString("SEARCH_QUERY");

            //onViewStateRestored(savedInstanceState);
            mExpandableListView = (ExpandableListView)
                    view.findViewById(R.id.location_expand_list);
            mEpandableListAdapter = new
                    GenericExpandableListAdapter(getContext(), sPostSearchContinetList);
            mExpandableListView.setAdapter(mEpandableListAdapter);

            if(sTabSearchOpened){
                openSearchBar(sSearchQuery);
            }
        }*/
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
/*      outState.putParcelableArrayList("COUTRIES", sContinentArrayList);
        outState.putParcelableArrayList("FILTERED_COUNTRIES", sPostSearchContinetList);
        outState.putBoolean("SEARCH_OPENED", sTabSearchOpened);
        outState.putString("SEARCH_QUERY", sSearchQuery);*/
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        mSearchAction = menu.findItem(R.id.action_search);
        mSearchView = new SearchView(((MainActivity) getActivity())
                .getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(mSearchAction, MenuItemCompat
                .SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        /*mSearchAction = menu.findItem(R.id.action_search);
        mSearchView = new SearchView(((MainActivity) getActivity())
                .getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(mSearchAction,MenuItemCompat
                .SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnCloseListener(this);*/
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            if (sTabSearchOpened) {
                onClose();
            } else {
                openSearchBar(mSearchView.getQuery().toString());
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
        mSearchView = (SearchView) actionBar.getCustomView().findViewById(R.id.action_search);
/*        mSearchView.setOnQueryTextListener(this);
        mSearchView.requestFocusFromTouch();
        mSearchView.setOnCloseListener(this);*/

/*        mSearchAction.setIcon(sIconCloseSearch);*/
        sTabSearchOpened = true;
    }

    /*public ArrayList<Country> creatingCountryData() {
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
*/
    private void expandAll(){
        int count = mEpandableListAdapter.getGroupCount();
        for(int i = 0; i < count; i++){
            mExpandableListView.expandGroup(i);
        }
    }

   /* private void populateList(){

    }*/

    @Override
    public boolean onClose() {
        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setDisplayShowCustomEnabled(false);
        mSearchAction.setIcon(sIconOpenSearch);
        ((AppCompatActivity)getActivity()).getSupportActionBar()
                .setDisplayShowTitleEnabled(true);
        mEpandableListAdapter.filterData("");
        sTabSearchOpened = false;
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mEpandableListAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mEpandableListAdapter.filterData(newText);
        expandAll();
        return false;
    }
}
