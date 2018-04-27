package com.seanblonien.cryptokit;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.text.DecimalFormat;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.AssetViewHolder> {
    private static final String TAG = "RVAdapter";
    private Context context;
    private List<CryptoAsset> assets;
    private SwipeRefreshLayout layout;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private static DecimalFormat dollarFormat = new DecimalFormat("¤###,###,###,##0.00##");
    private static DecimalFormat smallDollarFormat = new DecimalFormat("¤0.0000");
    private static DecimalFormat percentFormat = new DecimalFormat("#0.00'%'");
    private static DecimalFormat bitcoinFormat = new DecimalFormat("#0.00######");
    private static DecimalFormat tokenFormat = new DecimalFormat("###,###,###,##0");

    RVAdapter(Context context, List<CryptoAsset> a, SwipeRefreshLayout l){
        this.context = context;
        this.assets = a;
        this.layout = l;
        for (int i = 0; i < assets.size(); i++) {
            expandState.append(i, false);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public AssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card, parent, false);
        return new AssetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final AssetViewHolder holder, final int position) {
            // Get a reference to this asset the holder will hold
            final CryptoAsset a = assets.get(holder.getLayoutPosition());
            // Set appropriate attributes
            holder.assetRank.setText(String.valueOf(a.getRank()));
            holder.assetName.setText(a.getName());
            holder.assetSymbol.setText(a.getSymbol());
            if(a.getPrice_usd() < 0.1) {
                holder.assetPriceUSD.setText(smallDollarFormat.format(a.getPrice_usd()));
            } else {
                holder.assetPriceUSD.setText(dollarFormat.format(a.getPrice_usd()));
            }
            holder.assetPriceUSD.setTextColor(Color.parseColor(a.getPercent_change_24h() < 0 ? "#cc0000" : "#009933"));
            holder.assetPercentChange24h.setText(percentFormat.format(a.getPercent_change_24h()));
            holder.assetPercentChange24h.setTextColor(Color.parseColor(a.getPercent_change_24h() < 0 ? "#cc0000" : "#009933"));
            holder.assetPriceBTC.setText(a.getSymbol()+ "/BTC: "+bitcoinFormat.format(a.getPrice_btc()));
            holder.assetVolume.setText("24/hr volume: "+dollarFormat.format(a.getVolume_usd()));
            holder.assetMarketCap.setText("Market cap: "+dollarFormat.format(a.getMarket_cap_usd()));
            holder.assetCirculatingVolume.setText("Available token supply: "+tokenFormat.format(a.getAvailable_supply().longValue()));
            if(a.getMax_supply() != null) {
                holder.assetMaxSupply.setText("Maximum token supply: "+tokenFormat.format(a.getMax_supply()));
            } else {
                holder.assetMaxSupply.setText("Maximum token supply: N/A");
            }
            holder.assetDescription.setText(a.getDescription());
            RequestOptions myOptions = new RequestOptions()
                    .fitCenter()
                    .centerCrop();
            Glide.with(context)
                    .load(a.getImage())
                    .apply(myOptions)
                    .into(holder.assetImage);
            holder.setIsRecyclable(false);
            holder.expandableLayout.setInRecyclerView(true);
            holder.expandableLayout.setInterpolator(Utils.createInterpolator(Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR));
            holder.expandableLayout.setExpanded(false);
            holder.expandableLayout.setBackgroundColor(context.getColor(R.color.m_white));
            holder.expandableLayout.setExpanded(expandState.get(holder.getLayoutPosition()));
            holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    expandState.put(holder.getLayoutPosition(), true);
                }

                @Override
                public void onPreClose() {
                    expandState.put(holder.getLayoutPosition(), false);
                }
            });

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                holder.expandableLayout.toggle();
            }
        });
    }

    @Override
    public int getItemCount() {
        return assets.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<CryptoAsset> getAssets() {
        return assets;
    }

    public void setAssets(List<CryptoAsset> assets) {
        this.assets = assets;
    }

    public SwipeRefreshLayout getLayout() {
        return layout;
    }

    public void setLayout(SwipeRefreshLayout layout) {
        this.layout = layout;
    }

    public SparseBooleanArray getExpandState() {
        return expandState;
    }

    public void setExpandState(SparseBooleanArray expandState) {
        this.expandState = expandState;
    }

    public static DecimalFormat getDollarFormat() {
        return dollarFormat;
    }

    public static void setDollarFormat(DecimalFormat dollarFormat) {
        RVAdapter.dollarFormat = dollarFormat;
    }

    static class AssetViewHolder extends RecyclerView.ViewHolder {
        ImageView assetImage;
        TextView assetSymbol;
        TextView assetRank;
        TextView assetName;
        TextView assetPriceUSD;
        TextView assetPriceBTC;
        TextView assetVolume;
        TextView assetMarketCap;
        TextView assetCirculatingVolume;
        TextView assetDescription;
        TextView assetPercentChange24h;
        TextView assetMaxSupply;
        ExpandableLinearLayout expandableLayout;
        RelativeLayout relativeLayout;

        AssetViewHolder(View itemView) {
            super(itemView);
            assetRank = itemView.findViewById(R.id.asset_rank);
            assetImage = itemView.findViewById(R.id.asset_image);
            assetName = itemView.findViewById(R.id.asset_name);
            assetPriceUSD = itemView.findViewById(R.id.asset_price);
            assetSymbol = itemView.findViewById(R.id.asset_symbol);
            assetPercentChange24h = itemView.findViewById(R.id.asset_percent_change_24h);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            relativeLayout = itemView.findViewById(R.id.relative_card);
            assetPriceBTC = itemView.findViewById(R.id.asset_price_btc);
            assetVolume = itemView.findViewById(R.id.asset_volume);
            assetMarketCap = itemView.findViewById(R.id.asset_market_cap);
            assetCirculatingVolume = itemView.findViewById(R.id.asset_supply);
            assetDescription = itemView.findViewById(R.id.asset_description);
            assetMaxSupply = itemView.findViewById(R.id.asset_max_supply);
        }
    }
}