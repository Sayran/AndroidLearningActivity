package com.example.andrejssileckis.fragmenttestactivity;

/**
 * Created by andrejs.sileckis on 10/27/2015.
 */

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.getDrawable;

public class SecondTabFragment extends Fragment {

    private static final JsonController JSON_CONTROLLER = new JsonController();
    private GenericExpandableListAdapter mEpandableListAdapter;
    private static ArrayList<Continent> sContinentArrayList  = new ArrayList<>();
    private ExpandableListView mExpandableListView;
    private static boolean sTabSearchOpened = false;
    private static Drawable sIconOpenSearch;
    private static Drawable sIconCloseSearch;
    private static MenuItem mSearchAction;
    private String mSearchQuery;
    private EditText mEditText;


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

        sContinentArrayList = JSON_CONTROLLER.getStructure(getContext());
        this.mEpandableListAdapter = new GenericExpandableListAdapter(getContext()
                , sContinentArrayList);
        mExpandableListView = (ExpandableListView) view
                .findViewById(R.id.location_expand_list_unique);

        mExpandableListView.setAdapter(mEpandableListAdapter);
        expandAll();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        mSearchAction = menu.findItem(R.id.action_search);
        MenuItemCompat.setShowAsAction(mSearchAction, MenuItemCompat
                .SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);


    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.second_fragment_menu, menu);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            if (sTabSearchOpened) {
                onClose();
            } else {
                openSearchBar(mSearchQuery);
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
        mEditText = (EditText) actionBar.getCustomView().findViewById(R.id.etSearch);
        mSearchAction.setIcon(sIconCloseSearch);
        mEditText.addTextChangedListener(new SearchWatcher());
        mEditText.setText(queryText);
        mEditText.requestFocusFromTouch();
        sTabSearchOpened = true;

    }

    private void expandAll(){
        int count = mEpandableListAdapter.getGroupCount();
        for(int i = 0; i < count; i++){
            mExpandableListView.expandGroup(i);
        }
    }

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

    public boolean onQueryTextSubmit(String query) {
        mEpandableListAdapter.filterData(query);
        expandAll();
        return false;
    }

    public boolean onQueryTextChange(String newText) {
        mEpandableListAdapter.filterData(newText);
        expandAll();
        return false;
    }

    public class SearchWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mSearchQuery = mEditText.getText().toString();
            /*Toast.makeText(getContext(),mSearchQuery + " this is current data in search",
                    Toast.LENGTH_SHORT).show();*/
            mEpandableListAdapter.filterData(mSearchQuery);
            expandAll();

        }

    }
}
