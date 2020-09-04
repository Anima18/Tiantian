package com.chris.tiantian.module.message.fragment;

import android.content.Context;
import android.os.Bundle;
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

import com.anima.componentlib.paginglistview.PagingRecycleAdapter;
import com.anima.componentlib.paginglistview.PagingRecycleView;
import com.anima.componentlib.paginglistview.viewholder.PagingRecycleItemViewHolder;
import com.bumptech.glide.Glide;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.MarketTick;
import com.chris.tiantian.entity.MessageItemViewHolder;
import com.chris.tiantian.entity.PolicySignal;
import com.chris.tiantian.entity.RankData;
import com.chris.tiantian.entity.eventmessage.MarketTIcksMessage;
import com.chris.tiantian.entity.eventmessage.PolicySignalMessage;
import com.chris.tiantian.module.main.activity.fragment.MonthLeaderboardFragment;
import com.chris.tiantian.module.message.activityview.SyntheticMessageActionView;
import com.chris.tiantian.module.message.presenter.SyntheticMessagePresenter;
import com.chris.tiantian.module.message.presenter.SyntheticMessagePresenterImpl;
import com.chris.tiantian.util.CommonAdapter;
import com.chris.tiantian.util.CommonItemViewHolder;
import com.chris.tiantian.util.LocationLog;
import com.chris.tiantian.util.PreferencesUtil;
import com.chris.tiantian.util.UserUtil;
import com.chris.tiantian.view.DividerItemDecoration;
import com.chris.tiantian.view.MultipleStatusView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjianhong on 20-7-30
 */
public class SyntheticMessageFragment extends Fragment implements SyntheticMessageActionView {
    private View rootView;
    private SyntheticMessagePresenter presenter;
    private SwipeRefreshLayout refreshLayout;
    private MultipleStatusView statusView;
    private RecyclerView recycleView;
    private List<PolicySignal> policySignals = new ArrayList<>();
    private CommonAdapter<PolicySignal> signalCommonAdapter;

    private RecyclerView marketTickView;
    private CommonAdapter<MarketTick> marketTickAdapter;
    private List<MarketTick> marketTickList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("ddddddd", "SyntheticMessageFragment onCreateView");
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_message_all, container, false);
            presenter = new SyntheticMessagePresenterImpl(getContext(), this);
            marketTickView = rootView.findViewById(R.id.currency_listView);
            recycleView = rootView.findViewById(R.id.allMessage_listView);
            statusView = rootView.findViewById(R.id.strategyFragment_status_view);
            refreshLayout = rootView.findViewById(R.id.view_swipe_refresh_layout);
            refreshLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_orange_light),
                    getResources().getColor(android.R.color.holo_red_light),
                    getResources().getColor(android.R.color.holo_blue_light),
                    getResources().getColor(android.R.color.holo_green_light));

            intiRecycleView();
        }
        return rootView;
    }

    private void intiRecycleView() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(UserUtil.isLogin()) {
                    showLoading();
                    presenter.refreshPolicySignal();
                }else {
                    showError("请先登录！");
                }

            }
        });
        signalCommonAdapter = new CommonAdapter(getContext(), R.layout.listview_message_item);
        signalCommonAdapter.setItemViewHolderCreator(new CommonAdapter.OnItemViewHolderCreator() {
            @Override
            public CommonItemViewHolder create(View itemView) {
                return new SignalItemViewHolder(getContext(), itemView);
            }
        });
        signalCommonAdapter.setData(policySignals);
        recycleView.setAdapter(signalCommonAdapter);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        recycleView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager1.getOrientation(), DividerItemDecoration.DIVIDER_TYPE_INSET, layoutManager1.getOrientation()));
        recycleView.setLayoutManager(layoutManager1);

        marketTickAdapter = new CommonAdapter(getContext(), R.layout.listview_currency_item);
        marketTickAdapter.setItemViewHolderCreator(new CommonAdapter.OnItemViewHolderCreator() {
            @Override
            public CommonItemViewHolder create(View itemView) {
                return new MarketTickItemViewHolder(itemView);
            }
        });
        marketTickAdapter.setData(marketTickList);
        marketTickView.setAdapter(marketTickAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        marketTickView.setLayoutManager(layoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        requestData();
    }

    private void requestData() {
        if(UserUtil.isLogin()) {
            presenter.requestStrategyDataByLocal();
        }else {
            showError("请先登录！");
        }
        presenter.monitorMarketTicks();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void policySignalEventBus(PolicySignalMessage message) {
        LocationLog.getInstance().i("PolicyMonitorService request localData");
        presenter.requestStrategyDataByLocal();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void marketTickEventBus(MarketTIcksMessage message) {
        marketTickAdapter.setData(message.getMarketTicks());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //EventBus.getDefault().unregister(this);
    }

    @Override
    public void showLoading() {
        statusView.showLoading();
    }

    @Override
    public void showError(String message) {
        refreshLayout.setRefreshing(false);
        statusView.showError(message);
    }

    @Override
    public void showData(List<PolicySignal> list) {
        refreshLayout.setRefreshing(false);

        if(list.size() == 0) {
            statusView.showEmpty();
        }else {
            statusView.showContent();
            signalCommonAdapter.setData(list);
        }

    }

    class SignalItemViewHolder extends CommonItemViewHolder<PolicySignal> {

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

        public SignalItemViewHolder(Context context, View itemView) {
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

    }
}
