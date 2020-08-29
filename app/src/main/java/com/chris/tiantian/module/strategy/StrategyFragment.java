package com.chris.tiantian.module.strategy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.callback.DataListCallback;
import com.anima.networkrequest.entity.RequestParam;
import com.anima.networkrequest.util.sharedprefs.ConfigSharedPreferences;
import com.bumptech.glide.Glide;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.Strategy;
import com.chris.tiantian.entity.UserPointLog;
import com.chris.tiantian.entity.dataparser.ListStatusDataParser;
import com.chris.tiantian.module.strategy.setting.StrategySettingActivity;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.StringUtil;
import com.chris.tiantian.util.UserUtil;
import com.chris.tiantian.view.MultipleStatusView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.chris.tiantian.module.strategy.setting.StrategySettingActivity.strategy_data;

/**
 * Created by jianjianhong on 20-7-28
 */
public class StrategyFragment extends Fragment {
    private View rootView;
    private MultipleStatusView statusView;
    private SwipeRefreshLayout refreshLayout;
    private StrategyAdapter adapter;
    private List<Strategy> strategyList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_strategy, container, false);
            statusView = rootView.findViewById(R.id.strategyFragment_status_view);
            refreshLayout = rootView.findViewById(R.id.view_swipe_refresh_layout);
            refreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_orange_light),
                    getResources().getColor(android.R.color.holo_red_light),
                    getResources().getColor(android.R.color.holo_blue_light),
                    getResources().getColor(android.R.color.holo_green_light));

            RecyclerView listView = rootView.findViewById(R.id.strategyListView);
            adapter = new StrategyAdapter(getContext(), strategyList);
            adapter.setOnItemClickListener(new StrategyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    Strategy strategy = strategyList.get(position);
                    //Toast.makeText(getContext(), strategy.getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), StrategySettingActivity.class);
                    intent.putExtra(strategy_data, strategy);
                    startActivity(intent);
                }
            });
            listView.setAdapter(adapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            listView.setLayoutManager(layoutManager);

            statusView.setOnRetryClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initData();
                }
            });
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    initData();
                }
            });
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("cccccccc", "onResume");
        /*boolean loadingStrategy = ConfigSharedPreferences.Companion.getInstance(getContext()).getBooleanValue(Constant.SP_STRATEGY_LOADED, true);
        if (loadingStrategy) {
            initData();
        }*/
        initData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("cccccccc", "StrategyFragment onDestroy");
    }

    private void initData() {
        refreshLayout.setRefreshing(false);
        statusView.showLoading();
        String url = String.format("%s/comment/apiv2/policySquare/%s", CommonUtil.getBaseUrl(), UserUtil.getUserId());
        new NetworkRequest<Strategy>(getContext())
                .url(url)
                .method(RequestParam.Method.GET)
                .asJson(true)
                .dataClass(Strategy.class)
                .dataParser(new ListStatusDataParser<Strategy>())
                .getList(new DataListCallback<Strategy>() {
                    @Override
                    public void onFailure(@NotNull String s) {

                        statusView.showError(s);
                    }

                    @Override
                    public void onSuccess(@org.jetbrains.annotations.Nullable List<? extends Strategy> list) {
                        ConfigSharedPreferences.Companion.getInstance(getContext()).putBooleanValue(Constant.SP_STRATEGY_LOADED, false);
                        if(list == null || list.size() == 0) {
                            statusView.showEmpty();
                        }else {
                            statusView.showContent();
                            strategyList.clear();
                            strategyList.addAll(list);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
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
            Glide.with(context).load(strategy.getMarketIconUrl()).placeholder(R.drawable.ic_broken_image_black_24dp).into(holder.iconView);
            holder.nameView.setText(strategy.getMarket());

            String type = "";
            if(!TextUtils.isEmpty(strategy.getPolicyChoosed())) {
                type += strategy.getPolicyChoosed();
            }
            if(!TextUtils.isEmpty(strategy.getSignalChoosed())) {
                type += "、";
                type += strategy.getSignalChoosed();
            }
            holder.typeView.setText(type);
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
