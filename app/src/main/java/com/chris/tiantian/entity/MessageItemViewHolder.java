package com.chris.tiantian.entity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.anima.componentlib.paginglistview.viewholder.PagingRecycleItemViewHolder;
import com.bumptech.glide.Glide;
import com.chris.tiantian.R;

/**
 * Created by jianjianhong on 20-8-26
 */
public class MessageItemViewHolder extends PagingRecycleItemViewHolder<PolicySignal> {

    Context context;
    TextView policyNameView;
    TextView policyTimeLevelView;
    ImageView marketIcon;
    TextView timeView;
    TextView stopPriceView;
    TextView currentPriceView;
    TextView optionView;
    TextView optionDetailView;
    TextView marketNameView;

    public MessageItemViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.marketIcon = itemView.findViewById(R.id.message_policyMarket);
        this.policyTimeLevelView = itemView.findViewById(R.id.message_policyTimeLevel);
        this.policyNameView = itemView.findViewById(R.id.message_policyName);
        this.timeView = itemView.findViewById(R.id.message_time);
        this.stopPriceView = itemView.findViewById(R.id.message_stopPrice);
        this.currentPriceView = itemView.findViewById(R.id.message_currPrice);
        this.optionView = itemView.findViewById(R.id.message_option);
        this.optionDetailView = itemView.findViewById(R.id.message_optionDetail);
        this.marketNameView = itemView.findViewById(R.id.message_market_name);
    }

    @Override
    public void bindto(@NonNull PolicySignal signal) {
        Glide.with(context).load(signal.getMarketIconUrl()).placeholder(R.drawable.ic_broken_image_black_24dp).into(marketIcon);
        policyNameView.setText(signal.getPolicyName());
        marketNameView.setText(signal.getPolicyMarket());
        timeView.setText(signal.getTime());
        policyTimeLevelView.setText(signal.getPolicyTimeLevel());
        stopPriceView.setText(signal.getStopPrice()+"");
        currentPriceView.setText(signal.getCurrPrice()+"");
        optionView.setText(signal.getDirection());
        optionDetailView.setText(signal.getDescription());
    }

    @Override
    public View itemView() {
        return null;
    }

    @Override
    public View backgroundView() {
        return null;
    }
}