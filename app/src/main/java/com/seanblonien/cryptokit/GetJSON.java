package com.seanblonien.cryptokit;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GetJSON extends AsyncTask<Void, Void, List<CryptoAsset>> {
    private Exception exception;

    @Override
    protected List<CryptoAsset> doInBackground(Void... voids) {
        String pageText;
        List<CryptoAsset> assets = null;
        try {
            URL url = new URL("https://api.coinmarketcap.com/v1/ticker/");
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            pageText = reader.lines().collect(Collectors.joining("\n"));
            Gson gson = new GsonBuilder().create();
            gson.fieldNamingStrategy().translateName(CryptoAsset.class.getField("volume_usd"));
            assets = Arrays.asList(gson.fromJson(pageText, CryptoAsset[].class));
            System.out.println(assets);
        } catch (IOException | NetworkOnMainThreadException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return assets;
    }

    protected void onPreExecute() {

    }

    protected void onProgressUpdate() {

    }



    protected void onPostExecute(String response) {

    }
}
