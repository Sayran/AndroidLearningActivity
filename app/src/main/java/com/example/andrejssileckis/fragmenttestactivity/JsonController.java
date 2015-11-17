package com.example.andrejssileckis.fragmenttestactivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.BundleCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by andrejs.sileckis on 11/5/2015.
 */
public class JsonController {

    public LinkedList<String> data;
    public Country countryStructure;
    private ArrayList<Country> countryList;

    public ArrayList<Country> getStructure (Context context,Integer integer){
        this.countryList = new ArrayList<>();
        ArrayList<Country> worldlist = new ArrayList<>();
        BufferedReader jsonReader = new BufferedReader(
                new InputStreamReader(context.getResources().openRawResource(R.raw.json_countries)));
        // Bundle bundle = new Bundle();
        try {
            //BufferedReader jsonReader = bf;//new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.localjsonfile)));
            StringBuilder jsonBuilder = new StringBuilder();
            for (String line = null; (line = jsonReader.readLine()) != null;) {
                jsonBuilder.append(line).append("\n");
            }
            JSONTokener tokener = new JSONTokener(jsonBuilder.toString());
            JSONArray jsonArray = new JSONArray(tokener);

            for(int i = 0; i < jsonArray.length();i++){
                JSONObject jsonExploreObject = jsonArray.getJSONObject(i);
                countryStructure = new Country();
                countryStructure = new Country(jsonExploreObject.getString("CountryName"),
                        jsonExploreObject.getString("CapitalName"),jsonExploreObject.getString("CapitalLatitude"),
                        jsonExploreObject.getString("CapitalLongitude"),jsonExploreObject.getString("CountryCode"),
                        jsonExploreObject.getString("ContinentName"));

                /*countryStructure.setCountry(jsonExploreObject.getString("CountryName"));
                countryStructure.setCapital(jsonExploreObject.getString("CapitalName"));
                countryStructure.setLatitude(jsonExploreObject.getString("CapitalLatitude"));
                countryStructure.setLongitude(jsonExploreObject.getString("CapitalLongitude"));
                if(!jsonExploreObject.getString("CountryCode").equals("NULL")){
                    countryStructure.setCountryCode(jsonExploreObject.getString("CountryCode"));
                }
                countryStructure.setContinetName(jsonExploreObject.getString("ContinentName"));*/

                //countryStructure.dataPrint(context, this.countryStructure);
                countryList.add(countryStructure);
                worldlist.add(countryStructure);
            }

        }catch (Exception e) {
            Log.e("json builder","error");
            e.printStackTrace();
        }
        return countryList;

    }
    public ArrayList getStructure(Context context){
        this.countryList = new ArrayList<>();
        ArrayList<Country> worldlist = new ArrayList<>();
        BufferedReader jsonReader = new BufferedReader(
                new InputStreamReader(context.getResources().openRawResource(R.raw.json_countries)));
       // Bundle bundle = new Bundle();
        try {
            //BufferedReader jsonReader = bf;//new BufferedReader(new InputStreamReader(this.getResources().openRawResource(R.raw.localjsonfile)));
            StringBuilder jsonBuilder = new StringBuilder();
            for (String line = null; (line = jsonReader.readLine()) != null;) {
                jsonBuilder.append(line).append("\n");
            }
            JSONTokener tokener = new JSONTokener(jsonBuilder.toString());
            JSONArray jsonArray = new JSONArray(tokener);

            for(int i = 0; i < jsonArray.length();i++){
                JSONObject jsonExploreObject = jsonArray.getJSONObject(i);
                countryStructure = new Country();
                countryStructure = new Country(jsonExploreObject.getString("CountryName"),
                        jsonExploreObject.getString("CapitalName"),jsonExploreObject.getString("CapitalLatitude"),
                        jsonExploreObject.getString("CapitalLongitude"),jsonExploreObject.getString("CountryCode"),
                        jsonExploreObject.getString("ContinentName"));

                /*countryStructure.setCountry(jsonExploreObject.getString("CountryName"));
                countryStructure.setCapital(jsonExploreObject.getString("CapitalName"));
                countryStructure.setLatitude(jsonExploreObject.getString("CapitalLatitude"));
                countryStructure.setLongitude(jsonExploreObject.getString("CapitalLongitude"));
                if(!jsonExploreObject.getString("CountryCode").equals("NULL")){
                    countryStructure.setCountryCode(jsonExploreObject.getString("CountryCode"));
                }
                countryStructure.setContinetName(jsonExploreObject.getString("ContinentName"));*/

                //countryStructure.dataPrint(context, this.countryStructure);
                countryList.add(countryStructure);
                worldlist.add(countryStructure);

            }

        }catch (Exception e) {
            Log.e("json builder","error");
            e.printStackTrace();
        }
        return worldlist;

    }
    public boolean isJSONValid(String test,Context context) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {

            // e.g. in case JSONArray is valid as well...
            Toast.makeText(context,"Cant create JSON Object",Toast.LENGTH_SHORT).show();
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                Toast.makeText(context, "Cant create Json array, json invalid,sorry",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        Toast.makeText(context, "Json valid",Toast.LENGTH_SHORT).show();
        return true;
    }
    public ArrayList<Country> countryArrayList(){
        return this.countryList;
    }

}
