package com.example.quakereport;

import java.util.Date;

public class EarthQuake {

    private double mMagnitude;
    private String mLocation;
    private long mDateInmilisec;
    private String mUrl;

    public EarthQuake(double magnitude, String location, long dateInmilisec, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        mDateInmilisec = dateInmilisec;
        mUrl = url;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public void setmMagnitude(double mMagnitude) {
        this.mMagnitude = mMagnitude;
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public long getmDateInmilisec() {
        return mDateInmilisec;
    }

    public void setmDate(long mDateInmilisec) {
        this.mDateInmilisec = mDateInmilisec;
    }

    @Override
    public String toString() {
        return "Earthquake{" +
                "mMagnitude='" + mMagnitude + '\'' +
                ", mLocation='" + mLocation + '\'' +
                ", mDateInmilisec=" + mDateInmilisec +
                '}';
    }


}
