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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class PriceChecker extends Activity implements SwipeRefreshLayout.OnRefreshListener  {
    private String TAG = PriceChecker.class.getSimpleName();
    protected GetJSON getjson;
    protected List<CryptoAsset> assets;
    private RVAdapter rvadapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ExpandableLinearLayout expandableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.price_checker);
        assets = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        rvadapter = new RVAdapter(this, this.assets);
        recyclerView.setAdapter(rvadapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CryptoAsset asset = assets.get(position);
                Toast.makeText(getApplicationContext(), asset.getName() + " updated!", Toast.LENGTH_SHORT).show();
                updateData(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "Refreshing all items");
                fetchData();
            }
        });

        fetchData();

    }

    private void fetchData() {
        // Signal SwipeRefreshLayout to start the progress indicator
        mSwipeRefreshLayout.setRefreshing(true);
        getjson = new GetJSON();
        getjson.execute();
    }

    private void updateData(int position) {
        updateID u = new updateID(position);
        u.execute();
    }

    /*
     * Listen for option item selections so that we receive a notification
     * when the user requests a refresh by selecting the refresh action bar item.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Check if user triggered a refresh:
            case R.id.swipe_container:
                Log.i(TAG, "Refresh menu item selected");

                return true;
        }

        // User didn't trigger a refresh, let the superclass handle this action
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onRefresh() {

    }

    private class GetJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(PriceChecker.this,"Fetching data",Toast.LENGTH_LONG).show();
            assets.clear();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String pageText;
            try {
                URL url = new URL("https://api.coinmarketcap.com/v1/ticker/?limit=100");
                URLConnection conn = url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                pageText = reader.lines().collect(Collectors.joining("\n"));
                Gson gson = new GsonBuilder().create();
                assets.addAll(Arrays.asList(gson.fromJson(pageText, CryptoAsset[].class)));
                for(CryptoAsset c : assets){
                    c.setImage("https://chasing-coins.com/api/v1/std/logo/"+c.getSymbol());
                }
                Log.i(TAG, assets.toString());
            } catch (IOException | NetworkOnMainThreadException  e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            rvadapter.notifyDataSetChanged();
            if(mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    private class updateID extends AsyncTask<Void, Void, Void> {
        Integer index;

        updateID(Integer i){
            this.index = i;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String pageText;
            try {
                URL url = new URL("https://api.coinmarketcap.com/v1/ticker/"+assets.get(index).getId());
                URLConnection conn = url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                pageText = reader.lines().collect(Collectors.joining("\n"));
                Gson gson = new GsonBuilder().create();
                List<CryptoAsset> c;
                c = Arrays.asList(gson.fromJson(pageText, CryptoAsset[].class));
                CryptoAsset b = c.get(0);
                assets.get(index)
                        .setPrice_usd(b.getPrice_usd())
                        .setPrice_btc(b.getPrice_btc())
                        .setPercent_change_1h(b.getPercent_change_1h())
                        .setPercent_change_24h(b.getPercent_change_24h())
                        .setVolume_usd(b.getVolume_usd())
                        .setLast_updated(b.getLast_updated());
                Log.i(TAG, assets.get(index).toString());
            } catch (IOException | NetworkOnMainThreadException  e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            rvadapter.notifyDataSetChanged();
            if(mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    }
}
