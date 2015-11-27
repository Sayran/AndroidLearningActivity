package com.example.andrejssileckis.fragmenttestactivity;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * Created by andrejs.sileckis on 11/5/2015.
 */
public class JsonController {

    public Country mCountryStructure;
    private ArrayList<Country> mCountryList;
    private ArrayList<Country> mCountryListTemp;
    private ArrayList<Continent> mContinentsList;
    private Continent mContinents;

    public ArrayList<Country> getStructure (Context context,Integer integer){
        this.mCountryList = new ArrayList<>();
        this.mContinentsList = new ArrayList<>();
        //ArrayList<Country> worldlist = new ArrayList<>();
        BufferedReader jsonReader = new BufferedReader(
                new InputStreamReader(context.getResources().openRawResource(R.raw.json_countries)));

        try {
            StringBuilder jsonBuilder = new StringBuilder();
            for (String line; (line = jsonReader.readLine()) != null;) {
                jsonBuilder.append(line).append("\n");
            }
            JSONTokener jsonTokener = new JSONTokener(jsonBuilder.toString());
            JSONArray jsonArray = new JSONArray(jsonTokener);

            for(int i = 0; i < jsonArray.length();i++){
                JSONObject jsonExploreObject = jsonArray.getJSONObject(i);
                mCountryStructure = new Country();
                mCountryStructure = new Country(jsonExploreObject.getString("CountryName"),
                        jsonExploreObject.getString("CapitalName"),
                        jsonExploreObject.getString("CapitalLatitude"),
                        jsonExploreObject.getString("CapitalLongitude"),
                        jsonExploreObject.getString("CountryCode"),
                        jsonExploreObject.getString("ContinentName"));

                mCountryList.add(mCountryStructure);
            }
        }catch (IOException e) {
            Log.e("Read attempt Error","IOException");
            e.printStackTrace();
        }catch (JSONException j){
            Log.e("JSON builder","Failed to create JSON structure.");
            j.printStackTrace();
        }
        return mCountryList;

    }
    public ArrayList<Continent> getStructure(Context context){
        this.mContinentsList = new ArrayList<>();
        BufferedReader jsonReader = new BufferedReader(
                new InputStreamReader(context.getResources().openRawResource(R.raw.json_countries)));
            StringBuilder jsonBuilder = new StringBuilder();
        
        try {
            for (String line; (line = jsonReader.readLine()) != null;) {
                jsonBuilder.append(line).append("\n");
            }
            JSONTokener jsonTokener = new JSONTokener(jsonBuilder.toString());
            JSONArray jsonArray = new JSONArray(jsonTokener);

            mContinentsList = getContinentCountries(jsonArray);
            if(mContinentsList == null){
                this.mCountryList = getStructure(context,1);
                mContinentsList = continentsBuilder(mCountryList);
                return mContinentsList;
            }
        } catch (IOException e) {
            Log.e("Read attempt Error","IOException");
            e.printStackTrace();
        }catch (JSONException j) {
            Log.e("JSON builder", "Failed to create JSON structure.");
            j.printStackTrace();
        }
        return mContinentsList;
    }

    public ArrayList<Continent> getContinentCountries(JSONArray jsonArr){
        ArrayList<Continent> continentsList;
        this.mCountryList = new ArrayList<>();
        this.mCountryListTemp = new ArrayList<>();

        try {

            JSONArray jsonArray = jsonArr;

            for(int i = 0; i < jsonArray.length();i++){
                JSONObject jsonExploreObject = jsonArray.getJSONObject(i);
                mCountryStructure = new Country(jsonExploreObject.getString("CountryName"),
                        jsonExploreObject.getString("CapitalName"),
                        jsonExploreObject.getString("CapitalLatitude"),
                        jsonExploreObject.getString("CapitalLongitude"),
                        jsonExploreObject.getString("CountryCode"),
                        jsonExploreObject.getString("ContinentName"));
                mCountryListTemp.add(mCountryStructure);
            }

        }catch (JSONException e) {
            Log.e("JSON Exception","Exception while  attempting to add Country class " +
                    "to List in JSONController class ");
            e.printStackTrace();
        }
        continentsList = continentsBuilder(mCountryListTemp);
        return continentsList;
    }

    public ArrayList<Continent> continentsBuilder(ArrayList<Country> countries){
        this.mContinentsList = new ArrayList<>();
        ArrayList<Country> africaList = new ArrayList<>();
        ArrayList<Country> asiaList = new ArrayList<>();
        ArrayList<Country> australiaList = new ArrayList<>();
        ArrayList<Country> europeList = new ArrayList<>();
        ArrayList<Country> centAmericaList = new ArrayList<>();
        ArrayList<Country> northAmericaList = new ArrayList<>();
        ArrayList<Country> southAmericaList = new ArrayList<>();
        ArrayList<Country> otherList = new ArrayList<>();

        for (Country country:countries) {
            switch (country.getContinent()) {
                case "Africa":
                    africaList.add(country);
                    break;
                case "Asia":
                    asiaList.add(country);
                    break;
                case "Australia":
                    australiaList.add(country);
                    break;
                case "Europe":
                    europeList.add(country);
                    break;
                case "Central America":
                    centAmericaList.add(country);
                    break;
                case "North America":
                    northAmericaList.add(country);
                    break;
                case "South America":
                    southAmericaList.add(country);
                    break;
                default:
                    otherList.add(country);
                    break;
            }
        }
        mContinents = new Continent("Africa", africaList);
        mContinentsList.add(mContinents);
        mContinents = new Continent("Asia", asiaList);
        mContinentsList.add(mContinents);
        mContinents = new Continent("Australia", australiaList);
        mContinentsList.add(mContinents);
        mContinents = new Continent("Europe", europeList);
        mContinentsList.add(mContinents);
        mContinents = new Continent("Central America", centAmericaList);
        mContinentsList.add(mContinents);
        mContinents = new Continent("North America", northAmericaList);
        mContinentsList.add(mContinents);
        mContinents = new Continent("South America", southAmericaList);
        mContinentsList.add(mContinents);
        mContinents = new Continent("Other", otherList);
        mContinentsList.add(mContinents);
        return mContinentsList;
    }

    public ArrayList<Continent> getContinentList(){
        return mContinentsList;
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
                Toast.makeText(context, "Cant create Json array, json invalid,sorry",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        Toast.makeText(context, "Json valid",Toast.LENGTH_SHORT).show();
        return true;
    }


}
