package com.example.andrejssileckis.fragmenttestactivity;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Toast;

/**
 * Created by andrejs.sileckis on 11/5/2015.
 */
public class Country implements Parcelable {
    private String mCountry;
    private String mCapital;
    private String mLatitude;
    private String mLongitude;
    private String mCountryCode;
    private String mContinent;


    Country(){
    }
    Country(String mCountry, String mCapital, String mLatitude, String mLongitude){
        this.mCountry = mCountry;
        this.mCapital = mCapital;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    Country(String mCountry, String mCapital, String mLatitude,
            String mLongitude , String mCountryCode, String mContinent){
        this(mCountry,mCapital,mLatitude,mLongitude);
        this.mCountryCode = mCountryCode;
        this.mContinent = mContinent;


    }


    public String getCountry() {
        return mCountry;
    }

    public String getCapital() {
        return mCapital;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

    public String getContinent() {
        return mContinent;
    }


    public void dataPrint(Context context,Country object){
        Toast.makeText(context,object.getCountry()+", "+object.getCapital()+
                ", "+object.getLongitude()+":"+object.getLatitude(),Toast.LENGTH_LONG).show();
    }

    protected Country(Parcel in) {
        this.mCountry = in.readString();
        this.mCapital = in.readString();
        this.mLatitude = in.readString();
        this.mLongitude = in.readString();
        this.mCountryCode = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCountry);
        dest.writeString(mCapital);
        dest.writeString(mLatitude);
        dest.writeString(mLongitude);
        dest.writeString(mCountryCode);
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
