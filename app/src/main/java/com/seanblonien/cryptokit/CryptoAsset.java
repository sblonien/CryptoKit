package com.seanblonien.cryptokit;

import com.google.gson.annotations.SerializedName;

public class CryptoAsset {
    private String id;
    private String name;
    private String symbol;
    private Long rank;
    private Double price_usd;
    private Double price_btc;
    @SerializedName("24h_volume_usd") private Double volume_usd;
    private Double market_cap_usd;
    private Long available_supply;
    private Long total_supply;
    private Long max_supply;
    private Float percent_change_1h;
    private Float percent_change_24h;
    private Float percent_change_7d;
    private Long last_updated;
    private String image;

    CryptoAsset(String name, Double price, Float percent, String image) {
        this.name = name;
        this.price_usd = price;
        this.percent_change_1h = percent;
        this.image = image;
    }

    @Override
    public String toString() {
        return "CryptoAsset{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", rank=" + rank +
                ", price_usd=" + price_usd +
                ", price_btc=" + price_btc +
                ", volume_usd=" + volume_usd +
                ", market_cap_usd=" + market_cap_usd +
                ", available_supply=" + available_supply +
                ", total_supply=" + total_supply +
                ", max_supply=" + max_supply +
                ", percent_change_1h=" + percent_change_1h +
                ", percent_change_24h=" + percent_change_24h +
                ", percent_change_7d=" + percent_change_7d +
                ", last_updated=" + last_updated +
                ", image='" + image + '\'' +
                '}';
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getRank() {
        return rank;
    }

    public CryptoAsset setRank(Long rank) {
        this.rank = rank;
        return this;
    }

    public Double getPrice_usd() {
        return price_usd;
    }

    public CryptoAsset setPrice_usd(Double price_usd) {
        this.price_usd = price_usd;
        return this;
    }

    public Double getPrice_btc() {
        return price_btc;
    }

    public CryptoAsset setPrice_btc(Double price_btc) {
        this.price_btc = price_btc;
        return this;
    }

    public Double getVolume_usd() {
        return volume_usd;
    }

    public CryptoAsset setVolume_usd(Double volume_usd) {
        this.volume_usd = volume_usd;
        return this;
    }

    public Double getMarket_cap_usd() {
        return market_cap_usd;
    }

    public CryptoAsset setMarket_cap_usd(Double market_cap_usd) {
        this.market_cap_usd = market_cap_usd;
        return this;
    }

    public Long getAvailable_supply() {
        return available_supply;
    }

    public void setAvailable_supply(Long available_supply) {
        this.available_supply = available_supply;
    }

    public Long getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(Long total_supply) {
        this.total_supply = total_supply;
    }

    public Long getMax_supply() {
        return max_supply;
    }

    public void setMax_supply(Long max_supply) {
        this.max_supply = max_supply;
    }

    public Float getPercent_change_1h() {
        return percent_change_1h;
    }

    public CryptoAsset setPercent_change_1h(Float percent_change_1h) {
        this.percent_change_1h = percent_change_1h;
        return this;
    }

    public Float getPercent_change_24h() {
        return percent_change_24h;
    }

    public CryptoAsset setPercent_change_24h(Float percent_change_24h) {
        this.percent_change_24h = percent_change_24h;
        return this;
    }

    public Float getPercent_change_7d() {
        return percent_change_7d;
    }

    public CryptoAsset setPercent_change_7d(Float percent_change_7d) {
        this.percent_change_7d = percent_change_7d;
        return this;
    }

    public Long getLast_updated() {
        return last_updated;
    }

    public CryptoAsset setLast_updated(Long last_updated) {
        this.last_updated = last_updated;
        return this;
    }
}
