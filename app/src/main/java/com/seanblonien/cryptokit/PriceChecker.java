package com.seanblonien.cryptokit;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

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

public class PriceChecker extends Activity {
    private String TAG = PriceChecker.class.getSimpleName();
    protected GetJSON getjson;
    protected List<CryptoAsset> assets;
    private RVAdapter rvadapter;

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
                CryptoAsset movie = assets.get(position);
                Toast.makeText(getApplicationContext(), movie.getName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        getjson = new GetJSON();
        getjson.execute();
    }

    private class GetJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(PriceChecker.this,"Fetching data",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String pageText;
            try {
                URL url = new URL("https://api.coinmarketcap.com/v1/ticker/");
                URLConnection conn = url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                pageText = reader.lines().collect(Collectors.joining("\n"));
                Gson gson = new GsonBuilder().create();
                assets.addAll(Arrays.asList(gson.fromJson(pageText, CryptoAsset[].class)));
                for(CryptoAsset c : assets){
                    c.setImage("https://chasing-coins.com/api/v1/std/logo/"+c.getSymbol());
                }
                //Log.i(TAG, assets.toString());
            } catch (IOException | NetworkOnMainThreadException  e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            rvadapter.notifyDataSetChanged();
        }
    }
}
