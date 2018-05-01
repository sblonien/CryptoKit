package com.seanblonien.cryptokit;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * The type Crypto asset.
 */
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
    private String description;

    // Factory method
    static CryptoAsset createCryptoAsset(String name, Double price, Float percent, String image) {
        return new CryptoAsset(name, price, percent, image);
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
                ", description='" + description + '\'' +
                '}';
    }


    /**
     * Instantiates a new Crypto asset.
     *
     * @param name    the name
     * @param price   the price
     * @param percent the percent
     * @param image   the image
     */
    private CryptoAsset(String name, Double price, Float percent, String image) {
        this.name = name;
        this.price_usd = price;
        this.percent_change_1h = percent;
        this.image = image;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets symbol.
     *
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets symbol.
     *
     * @param symbol the symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets rank.
     *
     * @return the rank
     */
    public Long getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CryptoAsset)) return false;
        CryptoAsset that = (CryptoAsset) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(symbol, that.symbol) &&
                Objects.equals(rank, that.rank) &&
                Objects.equals(price_usd, that.price_usd) &&
                Objects.equals(price_btc, that.price_btc) &&
                Objects.equals(volume_usd, that.volume_usd) &&
                Objects.equals(market_cap_usd, that.market_cap_usd) &&
                Objects.equals(available_supply, that.available_supply) &&
                Objects.equals(total_supply, that.total_supply) &&
                Objects.equals(max_supply, that.max_supply) &&
                Objects.equals(percent_change_1h, that.percent_change_1h) &&
                Objects.equals(percent_change_24h, that.percent_change_24h) &&
                Objects.equals(percent_change_7d, that.percent_change_7d) &&
                Objects.equals(last_updated, that.last_updated) &&
                Objects.equals(image, that.image) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, symbol, rank, price_usd, price_btc, volume_usd, market_cap_usd, available_supply, total_supply, max_supply, percent_change_1h, percent_change_24h, percent_change_7d, last_updated, image, description);
    }

    /**
     * Sets rank.
     *
     * @param rank the rank
     * @return the rank
     */
    public CryptoAsset setRank(Long rank) {
        this.rank = rank;
        return this;

    }

    /**
     * Gets price usd.
     *
     * @return the price usd
     */
    public Double getPrice_usd() {
        return price_usd;
    }

    /**
     * Sets price usd.
     *
     * @param price_usd the price usd
     * @return the price usd
     */
    public CryptoAsset setPrice_usd(Double price_usd) {
        this.price_usd = price_usd;
        return this;
    }

    /**
     * Gets price btc.
     *
     * @return the price btc
     */
    public Double getPrice_btc() {
        return price_btc;
    }

    /**
     * Sets price btc.
     *
     * @param price_btc the price btc
     * @return the price btc
     */
    public CryptoAsset setPrice_btc(Double price_btc) {
        this.price_btc = price_btc;
        return this;
    }

    /**
     * Gets volume usd.
     *
     * @return the volume usd
     */
    public Double getVolume_usd() {
        return volume_usd;
    }

    /**
     * Sets volume usd.
     *
     * @param volume_usd the volume usd
     * @return the volume usd
     */
    public CryptoAsset setVolume_usd(Double volume_usd) {
        this.volume_usd = volume_usd;
        return this;
    }

    /**
     * Gets market cap usd.
     *
     * @return the market cap usd
     */
    public Double getMarket_cap_usd() {
        return market_cap_usd;
    }

    /**
     * Sets market cap usd.
     *
     * @param market_cap_usd the market cap usd
     * @return the market cap usd
     */
    public CryptoAsset setMarket_cap_usd(Double market_cap_usd) {
        this.market_cap_usd = market_cap_usd;
        return this;
    }

    /**
     * Gets available supply.
     *
     * @return the available supply
     */
    public Long getAvailable_supply() {
        return available_supply;
    }

    /**
     * Sets available supply.
     *
     * @param available_supply the available supply
     */
    public void setAvailable_supply(Long available_supply) {
        this.available_supply = available_supply;
    }

    /**
     * Gets total supply.
     *
     * @return the total supply
     */
    public Long getTotal_supply() {
        return total_supply;
    }

    /**
     * Sets total supply.
     *
     * @param total_supply the total supply
     */
    public void setTotal_supply(Long total_supply) {
        this.total_supply = total_supply;
    }

    /**
     * Gets max supply.
     *
     * @return the max supply
     */
    public Long getMax_supply() {
        return max_supply;
    }

    /**
     * Sets max supply.
     *
     * @param max_supply the max supply
     */
    public void setMax_supply(Long max_supply) {
        this.max_supply = max_supply;
    }

    /**
     * Gets percent change 1 h.
     *
     * @return the percent change 1 h
     */
    public Float getPercent_change_1h() {
        return percent_change_1h;
    }

    /**
     * Sets percent change 1 h.
     *
     * @param percent_change_1h the percent change 1 h
     * @return the percent change 1 h
     */
    public CryptoAsset setPercent_change_1h(Float percent_change_1h) {
        this.percent_change_1h = percent_change_1h;
        return this;
    }

    /**
     * Gets percent change 24 h.
     *
     * @return the percent change 24 h
     */
    public Float getPercent_change_24h() {
        return percent_change_24h;
    }

    /**
     * Sets percent change 24 h.
     *
     * @param percent_change_24h the percent change 24 h
     * @return the percent change 24 h
     */
    public CryptoAsset setPercent_change_24h(Float percent_change_24h) {
        this.percent_change_24h = percent_change_24h;
        return this;
    }

    /**
     * Gets percent change 7 d.
     *
     * @return the percent change 7 d
     */
    public Float getPercent_change_7d() {
        return percent_change_7d;
    }

    /**
     * Sets percent change 7 d.
     *
     * @param percent_change_7d the percent change 7 d
     * @return the percent change 7 d
     */
    public CryptoAsset setPercent_change_7d(Float percent_change_7d) {
        this.percent_change_7d = percent_change_7d;
        return this;
    }

    /**
     * Gets last updated.
     *
     * @return the last updated
     */
    public Long getLast_updated() {
        return last_updated;
    }

    /**
     * Sets last updated.
     *
     * @param last_updated the last updated
     * @return the last updated
     */
    public CryptoAsset setLast_updated(Long last_updated) {
        this.last_updated = last_updated;
        return this;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
