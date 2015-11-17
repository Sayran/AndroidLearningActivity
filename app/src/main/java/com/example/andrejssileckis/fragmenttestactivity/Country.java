package com.example.andrejssileckis.fragmenttestactivity;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

/**
 * Created by andrejs.sileckis on 11/5/2015.
 */
public class Country implements Parcelable {
    private String country;
    private String capital;
    private String latitude;
    private String longitude;
    private String countryCode;
    private String continetName;

    Country(){
    }
    Country(String country, String capital, String latitude, String longitude , String countryCode,
            String continetName){
        this.country = country;
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
        this.countryCode = countryCode;
        this.continetName = continetName;
    }


    public String getCountry() {
        return country;
    }

    public String getCapital() {
        return capital;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getContinetName() {
        return continetName;
    }

    public void dataPrint(Context context,Country object){
        Toast.makeText(context,object.getCountry()+", "+object.getCapital()+
                ", "+object.getLongitude()+":"+object.getLatitude(),Toast.LENGTH_LONG).show();
    }

    protected Country(Parcel in) {
        this.country = in.readString();
        this.capital = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.countryCode = in.readString();
        this.continetName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(country);
        dest.writeString(capital);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(countryCode);
        dest.writeString(continetName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };
}
