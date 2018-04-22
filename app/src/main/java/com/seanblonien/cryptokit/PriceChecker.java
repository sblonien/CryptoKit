package com.seanblonien.cryptokit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
    protected GetJSON getjson = new GetJSON();
    protected List<CryptoAsset> assets;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rv;
    private RVAdapter rvadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.price_checker);

        rv = findViewById(R.id.recycler_view);
        assets = new ArrayList<>();
        getjson.execute();

        linearLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        rv.setLayoutManager(linearLayoutManager);
        rvadapter = new RVAdapter(this, assets);
        rv.setAdapter(rvadapter);
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
            List<CryptoAsset> assets = null;
            try {
                URL url = new URL("https://api.coinmarketcap.com/v1/ticker/");
                URLConnection conn = url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                pageText = reader.lines().collect(Collectors.joining("\n"));
                Gson gson = new GsonBuilder().create();
                assets = Arrays.asList(gson.fromJson(pageText, CryptoAsset[].class));
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
        }
    }
}
