package com.seanblonien.cryptokit;

import android.os.NetworkOnMainThreadException;

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

public class CryptoAsset {
    private String id;
    private String name;
    private String symbol;
    private Long rank;
    private Double priceUSD;
    private Double priceBTC;
    private Double dayVolume;
    private Double marketCap;
    private Long availableSupply;
    private Long totalSupply;
    private Float percentChange1h;
    private Float percentChange24h;
    private Float percentChange7d;
    private Long lastUpdated;

    @Override
    public String toString() {
        return "CryptoAsset{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", rank=" + rank +
                ", priceUSD=" + priceUSD +
                ", priceBTC=" + priceBTC +
                ", dayVolume=" + dayVolume +
                ", marketCap=" + marketCap +
                ", availableSupply=" + availableSupply +
                ", totalSupply=" + totalSupply +
                ", percentChange1h=" + percentChange1h +
                ", percentChange24h=" + percentChange24h +
                ", percentChange7d=" + percentChange7d +
                ", lastUpdated=" + lastUpdated +
                '}';
    }


}
