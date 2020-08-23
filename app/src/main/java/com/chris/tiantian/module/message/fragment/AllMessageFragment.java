package com.chris.tiantian.module.message.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anima.componentlib.paginglistview.PagingRecycleAdapter;
import com.anima.componentlib.paginglistview.PagingRecycleView;
import com.anima.componentlib.paginglistview.viewholder.PagingRecycleItemViewHolder;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.MarketTick;
import com.chris.tiantian.entity.PolicySignal;
import com.chris.tiantian.entity.RankData;
import com.chris.tiantian.entity.eventmessage.MarketTIcksMessage;
import com.chris.tiantian.entity.eventmessage.PolicySignalMessage;
import com.chris.tiantian.module.main.activity.fragment.NewTeachingFragment;
import com.chris.tiantian.module.signal.activity.PolicySignalActionView;
import com.chris.tiantian.module.signal.presenter.PolicySignalPresenter;
import com.chris.tiantian.module.signal.presenter.PolicySignalPresenterImpl;
import com.chris.tiantian.util.CommonAdapter;
import com.chris.tiantian.util.CommonItemViewHolder;
import com.chris.tiantian.util.LocationLog;
import com.chris.tiantian.util.PreferencesUtil;
import com.chris.tiantian.util.UIAdapter;
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
public class AllMessageFragment extends Fragment implements PolicySignalActionView {
    private View rootView;
    private MultipleStatusView statusView;
    private PolicySignalPresenter presenter;

    private PagingRecycleView recycleView;
    private RecyclerView marketTickView;

    private CommonAdapter<MarketTick> marketTickAdapter;
    private List<MarketTick> marketTickList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("ddddddd", "AllMessageFragment onCreateView");
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_message_all, container, false);
            statusView = rootView.findViewById(R.id.messageFragment_status_view);

            presenter = new PolicySignalPresenterImpl(getContext(), this);
            marketTickView = rootView.findViewById(R.id.currency_listView);
            recycleView = rootView.findViewById(R.id.allMessage_listView);
            intiRecycleView();
        }
        return rootView;
    }

    private void intiRecycleView() {
        recycleView.setItemViewHolderCreator(new PagingRecycleAdapter.OnItemViewHolderCreator() {
            @Override
            public PagingRecycleItemViewHolder create(View itemView) {
                return new MessageItemViewHolder(getContext(), itemView);
            }
        });

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
            statusView.showLoading();
            boolean isLocalRefresh = PreferencesUtil.getUserInfoPreference().getBooleanValue(Constant.SP_LOADING_POLICY_SIGNAL_DATABASE, false);
            if(isLocalRefresh) {
                recycleView.bindDataSource((startNo, endNo, page, callback) -> {
                    presenter.requestDataByLocal(callback, startNo, recycleView.getPageSize());
                });
            }else {
                showLoading();
                presenter.refreshPolicySignal(false);
            }
            presenter.monitorMarketTicks();
        }else {
            statusView.showError("请先登录！");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void policySignalEventBus(PolicySignalMessage message) {
        LocationLog.getInstance().i("PolicyMonitorService show data");
        statusView.showContent();
        if(message.isReload()) {
            recycleView.bindDataSource((startNo, endNo, page, callback) -> {
                Log.i("ddddd", startNo+","+endNo+","+page);
                presenter.requestDataByLocal(callback, startNo, recycleView.getPageSize());
            });
        }
        //presenter.requestDataByLocal();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void marketTickEventBus(MarketTIcksMessage message) {
        LocationLog.getInstance().i("PolicyMonitorService show data");
        marketTickAdapter.setData(message.getMarketTicks());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("ddddddd", "AllMessageFragment onDestroyView");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showLoading() {
        statusView.showLoading();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData(PagingRecycleView.LoadCallback loadCallback, List<PolicySignal> list) {
        statusView.showContent();
        loadCallback.onResult(list);
    }


    class MarketTickItemViewHolder extends CommonItemViewHolder<MarketTick> {
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
}
