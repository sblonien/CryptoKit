package com.seanblonien.cryptokit;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.AssetViewHolder>{
    private Context context;
    private List<CryptoAsset> assets;

    RVAdapter(Context context, List<CryptoAsset> a){
        this.context = context;
        this.assets = a;
    }

    @Override
    public AssetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.price_checker, parent, false);
        return new AssetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssetViewHolder holder, int position) {
        holder.assetName.setText(assets.get(position).getName());
        holder.assetPrice.setText(assets.get(position).getPrice_usd().toString());
        holder.assetPercentChange1h.setText(assets.get(position).getPercent_change_1h().toString());
        Glide.with(context).load(assets.get(position).getImage()).into(holder.assetImage);
    }

    @Override
    public int getItemCount() {
        return assets == null ? 0 : assets.size();
    }

    public static class AssetViewHolder extends RecyclerView.ViewHolder {
        ImageView assetImage;
        TextView assetName;
        TextView assetPrice;
        TextView assetPercentChange1h;

        AssetViewHolder(View itemView) {
            super(itemView);
            assetImage = itemView.findViewById(R.id.asset_image);
            assetName = itemView.findViewById(R.id.asset_name);
            assetPrice = itemView.findViewById(R.id.asset_price);
            assetPercentChange1h = itemView.findViewById(R.id.asset_percent_change_1h);
        }
    }

}