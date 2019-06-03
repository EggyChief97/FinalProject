package com.example.ocwiejaj20.finalproject;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by ocwiejaj20 on 5/24/2019.
 */

public class CarLoader extends AsyncTaskLoader<List<Car>> {
    private String mUrl;

    public CarLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Car> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<Car> books = QueryUtils.fetchBookData(mUrl);
        return books;
    }
}
