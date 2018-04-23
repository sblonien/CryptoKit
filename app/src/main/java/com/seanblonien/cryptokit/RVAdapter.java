package com.seanblonien.cryptokit;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.AssetViewHolder> {
    private static final String TAG = "RVAdapter";
    private Context context;
    private List<CryptoAsset> assets;

    static class AssetViewHolder extends RecyclerView.ViewHolder {
        ImageView assetImage;
        TextView assetSymbol;
        TextView assetRank;
        TextView assetName;
        TextView assetPrice;
        TextView assetPercentChange24h;

        AssetViewHolder(View itemView) {
            super(itemView);
            assetRank = itemView.findViewById(R.id.asset_rank);
            assetImage = itemView.findViewById(R.id.asset_image);
            assetName = itemView.findViewById(R.id.asset_name);
            assetPrice = itemView.findViewById(R.id.asset_price);
            assetSymbol = itemView.findViewById(R.id.asset_symbol);
            assetPercentChange24h = itemView.findViewById(R.id.asset_percent_change_24h);
        }
    }

    RVAdapter(Context context, List<CryptoAsset> a){ this.context = context; this.assets = a; }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public AssetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card, parent, false);
        return new AssetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssetViewHolder holder, int position) {
        CryptoAsset a = assets.get(position);
        holder.assetRank.setText(a.getRank().toString());
        holder.assetName.setText(a.getName());
        holder.assetSymbol.setText(a.getSymbol());
        holder.assetPrice.setText("$ " + NumberFormat.getInstance().format(Math.round(a.getPrice_usd() * 100.0) / 100.0));
        holder.assetPrice.setTextColor(Color.parseColor(a.getPercent_change_24h() < 0 ? "#cc0000" : "#009933"));
        holder.assetPercentChange24h.setText(NumberFormat.getInstance().format(Math.round(a.getPercent_change_1h() * 100.0) / 100.0)+"%");
        holder.assetPercentChange24h.setTextColor(Color.parseColor(a.getPercent_change_24h() < 0 ? "#cc0000" : "#009933"));
        Glide.with(context)
                .load(a.getImage())
                .fitCenter()
                .into(holder.assetImage);
    }

    @Override
    public int getItemCount() { return assets.size(); }
}