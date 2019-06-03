package com.example.ocwiejaj20.finalproject;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CarSelectionActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Car>>, SearchView.OnQueryTextListener {

    private static final int CAR_LOADER_ID = 1;
    private static final String CAR_REQUEST_URL_START = "https://vpic.nhtsa.dot.gov/api/vehicles/getmodelsformake/jeep?format=json";
    private static final String CAR_REQUEST_URL_MAX_RESULTS = "?format=json";
    private String CAR_REQUEST_QUERY = "";

    private RecyclerView mRecyclerView;
    private CarAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View mProgressBar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_selection_screen);
        final Intent i = new Intent(this, HomeScreenActivity.class);

        Button homeScreen = (Button) findViewById(R.id.BackToHomeScreen);
        homeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);

            }
        });
        final Intent j = new Intent(this, ShowInfoOnCarSlectedScreen.class);

        Button nextScreen = (Button) findViewById(R.id.ToInfoScreenOnCarSelected);
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(j);

            }
        });
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new CarAdapter(new ArrayList<Car>());
        mRecyclerView.setAdapter(mAdapter);

        mEmptyStateTextView = findViewById(R.id.empty_view);
        mProgressBar = findViewById(R.id.loading_spinner);
        mProgressBar.setVisibility(View.GONE);

        searchView = findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public Loader<List<Car>> onCreateLoader(int i, Bundle bundle) {
        String query = CAR_REQUEST_URL_START;
               // + CAR_REQUEST_QUERY + CAR_REQUEST_URL_MAX_RESULTS;
        return new CarLoader(this, query);
    }

    @Override
    public void onLoadFinished(Loader<List<Car>> loader, List<Car> cars) {
        mProgressBar.setVisibility(View.GONE);

        if (cars != null && !cars.isEmpty()) {
            mEmptyStateTextView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter.setData(cars);
            mAdapter.notifyDataSetChanged();
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyStateTextView.setText("No Cars");
            mEmptyStateTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Car>> loader) {
        mAdapter.setData(new ArrayList<Car>());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        CAR_REQUEST_QUERY = newText;
        kickOffNetworking();
        return false;
    }

    private void kickOffNetworking() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            mProgressBar.setVisibility(View.VISIBLE);
            if (getLoaderManager().getLoader(CAR_LOADER_ID) != null) {
                getLoaderManager().restartLoader(0, null, this);
            } else {
                getLoaderManager().initLoader(CAR_LOADER_ID, null, this);

            }
        } else {
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            mEmptyStateTextView.setText("No Internet");
            mEmptyStateTextView.setVisibility(View.VISIBLE);
        }
    }
}