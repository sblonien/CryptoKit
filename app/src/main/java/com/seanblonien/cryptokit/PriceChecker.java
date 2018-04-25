package com.seanblonien.cryptokit;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PriceChecker extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    private String TAG = PriceChecker.class.getSimpleName();

    private List<CryptoAsset> myAssets;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    ExpandableLinearLayout expandableLayout;
    private RVAdapter mRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.price_checker);

        // Allocate the ArrayList to store the crypto assets
        myAssets = new ArrayList<>();
        // Get a reference to the Recycler View and initialize it
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // Initialize an adapter for the RecyclerView and the CryptoAsset class
        mRVAdapter = new RVAdapter(this, this.myAssets, mSwipeRefreshLayout);
        // Set it to the view
        recyclerView.setAdapter(mRVAdapter);
        // Get a reference to the Swipe Refresh Layout and create a swipe listener
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "Loading...");
                fetchData();
            }
        });

        // Fetch the initial data
        fetchData();

        // Initialize the Recycler View layout such that it can be interacted with
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CryptoAsset asset = myAssets.get(position);
                updateData(asset);
                Toast.makeText(getApplicationContext(), asset.getName() + " updated!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                CryptoAsset asset = myAssets.get(position);
                updateData(asset);
                Toast.makeText(getApplicationContext(), asset.getName() + " refreshed!", Toast.LENGTH_SHORT).show();
            }
        }));

    }


    /**
     * Fetch all of the data at once. Called in onCreate and whenever the user swipe refreshes.
     */
    private void fetchData() {
        GetJSON getjson = new GetJSON(myAssets, mRVAdapter, mSwipeRefreshLayout);
        Toast.makeText(PriceChecker.this, "Loading...", Toast.LENGTH_LONG).show();
        getjson.execute();
    }


    /**
     * Update the specific crypto asset given asynchronously and load its description.
     *
     * @param a the crypto asset to be updated
     */
    private void updateData(CryptoAsset a) {
        updateThisAsset u = new updateThisAsset(a, mRVAdapter, mSwipeRefreshLayout);
        u.execute();
    }

    /**
     * Asynchronous task to update one crypto asset's values.
     */
    private static class updateThisAsset extends AsyncTask<Void, Void, Void> {
        CryptoAsset mAsset;
        RVAdapter mAdapter;
        SwipeRefreshLayout mSwipeRefreshLayout;

        updateThisAsset(CryptoAsset a, RVAdapter r, SwipeRefreshLayout s) {
            this.mAsset = a;
            this.mAdapter = r;
            this.mSwipeRefreshLayout = s;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String dataText, descripText;
            BufferedReader dataReader = null, descripReader = null;
            try {
                // Query the API to get this specific asset's data in JSON format
                URL dataURL = new URL("https://api.coinmarketcap.com/v1/ticker/" + mAsset.getId());
                URLConnection dataConnection = dataURL.openConnection();
                dataReader = new BufferedReader(new InputStreamReader(dataConnection.getInputStream(), StandardCharsets.UTF_8));
                // Extract the reader to a string
                dataText = dataReader.lines().collect(Collectors.joining("\n"));
                Gson dataGson = new GsonBuilder().create();
                // Parse the JSON file into a CryptoAssset
                List<CryptoAsset> c = Arrays.asList(dataGson.fromJson(dataText, CryptoAsset[].class));
                CryptoAsset newAsset = c.get(0);
                // Change and update the values that need to be updated
                mAsset
                        .setPrice_usd(newAsset.getPrice_usd())
                        .setPrice_btc(newAsset.getPrice_btc())
                        .setPercent_change_1h(newAsset.getPercent_change_1h())
                        .setPercent_change_24h(newAsset.getPercent_change_24h())
                        .setVolume_usd(newAsset.getVolume_usd())
                        .setLast_updated(newAsset.getLast_updated());

                // Query to get this asset's wikipedia description (will not be present for most myAssets)
                URL descripURL = new URL("https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=" + newAsset.getName());
                URLConnection descripConnection = descripURL.openConnection();
                descripReader = new BufferedReader(new InputStreamReader(descripConnection.getInputStream(), StandardCharsets.UTF_8));
                descripText = descripReader.lines().collect(Collectors.joining("\n"));
                Gson descripGson = new GsonBuilder().create();
                JsonObject obj = descripGson.fromJson(descripText, JsonObject.class);
                // Get the key withn the JSON response because the key varies by query and asset
                String key = obj.getAsJsonObject("query").getAsJsonObject("pages").keySet().toString().replace("[", "").replace("]", "");
                JsonElement jsonElement = obj.getAsJsonObject("query")
                        .getAsJsonObject("pages")
                        .getAsJsonObject(key)
                        .get("extract");
                // If there was a problem with the json element or the element was not found, set the
                // default description
                if (jsonElement == null || key.compareTo("-1") == 0) {
                    mAsset.setDescription(mAsset.getName()+" description.");
                } else {
                    // Otherwise set the appropriate description for this asset
                    String myString = jsonElement.getAsString();
                    if (myString.contains(".")) {
                        mAsset.setDescription(jsonElement.getAsString());
                    }
                }
                //Log.i(TAG, mAsset.toString());
            } catch (IOException | NetworkOnMainThreadException e) {
                e.printStackTrace();
            } finally {
                try {
                    dataReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    descripReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Notify the layout adapter that one of the values has changed
            mAdapter.notifyDataSetChanged();
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    private static class GetJSON extends AsyncTask<Void, Void, Void> {
        List<CryptoAsset> myAssets;
        RVAdapter mAdapter;
        SwipeRefreshLayout mSwipeRefreshLayout;

        GetJSON(List<CryptoAsset> mAssets, RVAdapter mAdapter, SwipeRefreshLayout mSwipeRefreshLayout) {
            this.myAssets = mAssets;
            this.mAdapter = mAdapter;
            this.mSwipeRefreshLayout = mSwipeRefreshLayout;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myAssets.clear();
            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String dataText;
            BufferedReader dataReader = null;
            try {
                // Query the API to get the top 100 crypto assets
                URL dataURL = new URL("https://api.coinmarketcap.com/v1/ticker/?limit=100");
                URLConnection dataConnection = dataURL.openConnection();
                dataReader = new BufferedReader(new InputStreamReader(dataConnection.getInputStream(), StandardCharsets.UTF_8));
                // Parse the JSON
                dataText = dataReader.lines().collect(Collectors.joining("\n"));
                Gson datagson = new GsonBuilder().create();
                // Add the parsed objects to the list
                myAssets.addAll(Arrays.asList(datagson.fromJson(dataText, CryptoAsset[].class)));

                // Add the image URL for all the assets
                for (CryptoAsset c : myAssets) {
                    c.setImage("https://chasing-coins.com/api/v1/std/logo/" + c.getSymbol());
                }
                // Log.i(TAG, "Updated all assets: " + myAssets.toString());
            } catch (IOException | NetworkOnMainThreadException e) {
                e.printStackTrace();
            } finally {
                try {
                    dataReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Notify the layout adapter that one of the values has changed
            if(mAdapter != null) mAdapter.notifyDataSetChanged();
            if (mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    /**
     * Called within the listener attached to mSwipeRefreshLayout
     */
    @Override
    public void onRefresh() {

    }
}
