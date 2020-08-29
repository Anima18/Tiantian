package com.chris.tiantian.module.message.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chris.tiantian.R;
import com.chris.tiantian.entity.MarketTick;
import com.chris.tiantian.util.CommonItemViewHolder;

/**
 * Created by jianjianhong on 20-8-24
 */
public class MarketTickItemViewHolder extends CommonItemViewHolder<MarketTick> {
    TextView nameView;
    TextView priceView;
    public MarketTickItemViewHolder(View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.market_name);
        priceView = itemView.findViewById(R.id.market_price);
    }

    @Override
    public void bindto(@NonNull MarketTick data) {

        nameView.setText(data.getInstrument_id());
        priceView.setText(data.getLast());
    }
}