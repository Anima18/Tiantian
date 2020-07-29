package com.chris.tiantian.module.strategy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chris.tiantian.R;
import com.chris.tiantian.entity.Strategy;
import com.chris.tiantian.module.strategy.setting.StrategySettingActivity;

import java.util.ArrayList;
import java.util.List;

import static com.chris.tiantian.module.strategy.setting.StrategySettingActivity.strategy_data;

/**
 * Created by jianjianhong on 20-7-28
 */
public class StrategyFragment extends Fragment {
    private View rootView;

    private List<Strategy> strategyList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_strategy, container, false);

            strategyList = initData();
            RecyclerView listView = rootView.findViewById(R.id.strategyListView);
            StrategyAdapter adapter = new StrategyAdapter(getContext(), strategyList);
            adapter.setOnItemClickListener(new StrategyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    Strategy strategy = strategyList.get(position);
                    Toast.makeText(getContext(), strategy.getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), StrategySettingActivity.class);
                    intent.putExtra(strategy_data, strategy);
                    startActivity(intent);
                }
            });
            listView.setAdapter(adapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            listView.setLayoutManager(layoutManager);
        }
        return rootView;
    }

    private List<Strategy> initData() {
        List<Strategy> strategyList = new ArrayList<>();
        strategyList.add(new Strategy("BTC", "BTC", R.drawable.ic_btc));
        strategyList.add(new Strategy("LTC", "LTC", R.drawable.ic_ltc));
        strategyList.add(new Strategy("ETH", "ETH", R.drawable.ic_eth));
        strategyList.add(new Strategy("EOS", "EOS", R.drawable.ic_btc));
        strategyList.add(new Strategy("ETC", "ETC", R.drawable.ic_etc));
        strategyList.add(new Strategy("BCH", "BCH", R.drawable.ic_bch));
        strategyList.add(new Strategy("BSV", "BSV", R.drawable.ic_btc));
        strategyList.add(new Strategy("XPR", "XPR", R.drawable.ic_btc));
        return strategyList;
    }



    static class StrategyAdapter extends RecyclerView.Adapter<StrategyAdapter.ViewHolder> {

        private Context context;
        private List<Strategy> strategyList;

        private OnItemClickListener listener;
        // Define the listener interface
        interface OnItemClickListener {
            void onItemClick(View itemView, int position);
        }

        // Define the method that allows the parent activity or fragment to define the listener
        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        // Pass in the context and users array into the constructor
        public StrategyAdapter(Context context, List<Strategy> strategyList) {
            this.context = context;
            this.strategyList = strategyList;
        }

        // Usually involves inflating a layout from XML and returning the holder
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Inflate the custom layout
            View itemView = LayoutInflater.from(context).inflate(R.layout.listview_strategy_item, parent, false);
            // Return a new holder instance
            return new ViewHolder(itemView, listener);
        }

        // Involves populating data into the item through holder
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Strategy strategy = strategyList.get(position);
            holder.iconView.setImageResource(strategy.getImageRes());
            holder.nameView.setText(strategy.getName());
        }

        // Return the total count of items
        @Override
        public int getItemCount() {
            return strategyList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView iconView;
            TextView nameView;
            TextView typeView;

            public ViewHolder(final View itemView, OnItemClickListener listener) {
                super(itemView);
                iconView = itemView.findViewById(R.id.strategy_icon);
                nameView = itemView.findViewById(R.id.strategy_name);
                typeView = itemView.findViewById(R.id.strategy_type);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Triggers click upwards to the adapter on click
                        if (listener != null)
                            listener.onItemClick(itemView, getLayoutPosition());
                    }
                });
            }
        }
    }
}
