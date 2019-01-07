package com.example.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthQuake>> {


    private static final String LOG_TAG = EarthquakeLoader.class.getName();


    private String mUrl;


    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<EarthQuake> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<EarthQuake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }
}