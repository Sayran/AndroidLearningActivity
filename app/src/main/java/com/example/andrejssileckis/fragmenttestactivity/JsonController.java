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
    private ArrayList<Continent> mContinentsList;
    private Continent mContinents;

    public ArrayList<Country> getStructure (Context context,Integer integer){
        this.mCountryList = new ArrayList<>();
        this.mContinentsList = new ArrayList<>();
        ArrayList<Country> worldlist = new ArrayList<>();
        BufferedReader jsonReader = new BufferedReader(
                new InputStreamReader(context.getResources().openRawResource(R.raw.json_countries)));

        try {
            StringBuilder jsonBuilder = new StringBuilder();
            for (String line = null; (line = jsonReader.readLine()) != null;) {
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
                        jsonExploreObject.getString("CountryCode"));

                mCountryList.add(mCountryStructure);
                worldlist.add(mCountryStructure);
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
    public ArrayList getStructure(Context context){

        BufferedReader jsonReader = new BufferedReader(
                new InputStreamReader(context.getResources().openRawResource(R.raw.json_countries)));
            StringBuilder jsonBuilder = new StringBuilder();
        
        try {
            for (String line = null; (line = jsonReader.readLine()) != null;) {
                jsonBuilder.append(line).append("\n");
            }
            JSONTokener jsonTokener = new JSONTokener(jsonBuilder.toString());
            JSONArray jsonArray = null;
            jsonArray = new JSONArray(jsonTokener);
            mContinentsList= getContinentCountries(jsonArray);
        } catch (IOException e) {
            Log.e("Read attempt Error","IOException");
            e.printStackTrace();
        }catch (JSONException j) {
            Log.e("JSON builder", "Failed to create JSON structure.");
            j.printStackTrace();
        }

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

    public ArrayList<Continent> getContinentCountries(JSONArray jsonArr){
        mCountryList = new ArrayList<>();

        ArrayList<Country> africaList = new ArrayList<>();
        ArrayList<Country> asiaList = new ArrayList<>();
        ArrayList<Country> australiaList = new ArrayList<>();
        ArrayList<Country> europeList = new ArrayList<>();
        ArrayList<Country> centAmericaList = new ArrayList<>();
        ArrayList<Country> northAmericaaList = new ArrayList<>();
        ArrayList<Country> southAmericaList = new ArrayList<>();

        try {

            JSONArray jsonArray = jsonArr;

            for(int i = 0; i < jsonArray.length();i++){
                JSONObject jsonExploreObject = jsonArray.getJSONObject(i);
                mCountryStructure = new Country();
                mCountryStructure = new Country(jsonExploreObject.getString("CountryName"),
                        jsonExploreObject.getString("CapitalName"),
                        jsonExploreObject.getString("CapitalLatitude"),
                        jsonExploreObject.getString("CapitalLongitude"),
                        jsonExploreObject.getString("CountryCode"));
                switch (jsonExploreObject.getString("Continent")){
                    case ("Africa"):
                        africaList.add(mCountryStructure);
                        break;
                    case ("Asia"):
                        asiaList.add(mCountryStructure);
                        break;
                    case ("Australia"):
                        europeList.add(mCountryStructure);
                        break;
                    case ("Europe"):
                        centAmericaList.add(mCountryStructure);
                        break;
                    case ("Central America"):
                        australiaList.add(mCountryStructure);
                        break;
                    case ("North America"):
                        northAmericaaList.add(mCountryStructure);
                        break;
                    case ("South America"):
                        southAmericaList.add(mCountryStructure);
                        break;
                }

            }
            mContinents = new Continent("Africa", africaList);
            mContinentsList.add(mContinents);
            mContinents = new Continent("Asia", asiaList);
            mContinentsList.add(mContinents);
            mContinents = new Continent("Australia", australiaList);
            mContinents = new Continent("Europe", europeList);
            mContinentsList.add(mContinents);
            mContinents = new Continent("Central America", centAmericaList);
            mContinentsList.add(mContinents);
            mContinents = new Continent("North America", northAmericaaList);
            mContinentsList.add(mContinents);
            mContinents = new Continent("South America", southAmericaList);
            mContinentsList.add(mContinents);

        }catch (JSONException e) {
            Log.e("JSON Exception","Exception while  attempting to add Country class " +
                    "to List in JSONController class ");
            e.printStackTrace();
        }
        return mContinentsList;
    }

    public ArrayList<Continent> getContinentList(){
        return mContinentsList;
    }


}
