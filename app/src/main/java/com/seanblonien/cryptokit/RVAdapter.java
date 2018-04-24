package com.seanblonien.cryptokit;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.GlideContext;
import com.bumptech.glide.request.RequestOptions;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.text.NumberFormat;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.AssetViewHolder> {
    private static final String TAG = "RVAdapter";
    private Context context;
    private List<CryptoAsset> assets;
    private SparseBooleanArray expandState = new SparseBooleanArray();

    RVAdapter(Context context, List<CryptoAsset> a){
        this.context = context;
        this.assets = a;
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
        final CryptoAsset a = assets.get(position);
        holder.assetRank.setText(a.getRank().toString());
        holder.assetName.setText(a.getName());
        holder.assetSymbol.setText(a.getSymbol());
        holder.assetPrice.setText("$ " + NumberFormat.getInstance().format(a.getPrice_usd() * 100.0 / 100.0));
        holder.assetPrice.setTextColor(Color.parseColor(a.getPercent_change_24h() < 0 ? "#cc0000" : "#009933"));
        holder.assetPercentChange24h.setText(NumberFormat.getInstance().format(Math.round(a.getPercent_change_24h() * 100.0) / 100.0)+"%");
        holder.assetPercentChange24h.setTextColor(Color.parseColor(a.getPercent_change_24h() < 0 ? "#cc0000" : "#009933"));
        RequestOptions myOptions = new RequestOptions()
                .fitCenter()
                .centerCrop();
        Glide.with(context)
                .load(a.getImage())
                .apply(myOptions)
                .into(holder.assetImage);
        holder.expandableLayout.setInRecyclerView(true);
        holder.expandableLayout.setInterpolator(Utils.createInterpolator(Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR));
        holder.expandableLayout.setExpanded(expandState.get(position));
        holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                expandState.put(position, true);
            }

            @Override
            public void onPreClose() {
                expandState.put(position, false);
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
    public int getItemCount() { return assets.size(); }

    static class AssetViewHolder extends RecyclerView.ViewHolder {
        ImageView assetImage;
        TextView assetSymbol;
        TextView assetRank;
        TextView assetName;
        TextView assetPrice;
        TextView assetPercentChange24h;
        ExpandableLinearLayout expandableLayout;
        RelativeLayout relativeLayout;

        AssetViewHolder(View itemView) {
            super(itemView);
            assetRank = itemView.findViewById(R.id.asset_rank);
            assetImage = itemView.findViewById(R.id.asset_image);
            assetName = itemView.findViewById(R.id.asset_name);
            assetPrice = itemView.findViewById(R.id.asset_price);
            assetSymbol = itemView.findViewById(R.id.asset_symbol);
            assetPercentChange24h = itemView.findViewById(R.id.asset_percent_change_24h);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            relativeLayout = itemView.findViewById(R.id.relative_card);
        }
    }
}