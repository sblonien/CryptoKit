package com.seanblonien.cryptokit;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PriceChecker extends Activity {
    private String TAG = PriceChecker.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.price_checker);

        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(PriceChecker.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
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
                //gson.fieldNamingStrategy().translateName(CryptoAsset.class.getField("volume_usd"));
                assets = Arrays.asList(gson.fromJson(pageText, CryptoAsset[].class));
                Log.i(TAG, assets.toString());
            } catch (IOException | NetworkOnMainThreadException  e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
