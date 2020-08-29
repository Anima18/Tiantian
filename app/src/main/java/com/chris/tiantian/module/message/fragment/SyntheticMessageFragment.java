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

import com.anima.componentlib.paginglistview.PagingRecycleAdapter;
import com.anima.componentlib.paginglistview.PagingRecycleView;
import com.anima.componentlib.paginglistview.viewholder.PagingRecycleItemViewHolder;
import com.bumptech.glide.Glide;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.MarketTick;
import com.chris.tiantian.entity.MessageItemViewHolder;
import com.chris.tiantian.entity.PolicySignal;
import com.chris.tiantian.entity.eventmessage.MarketTIcksMessage;
import com.chris.tiantian.entity.eventmessage.PolicySignalMessage;
import com.chris.tiantian.module.message.activityview.SyntheticMessageActionView;
import com.chris.tiantian.module.message.presenter.SyntheticMessagePresenter;
import com.chris.tiantian.module.message.presenter.SyntheticMessagePresenterImpl;
import com.chris.tiantian.util.CommonAdapter;
import com.chris.tiantian.util.CommonItemViewHolder;
import com.chris.tiantian.util.LocationLog;
import com.chris.tiantian.util.PreferencesUtil;
import com.chris.tiantian.util.UserUtil;
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

    private PagingRecycleView recycleView;

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
            intiRecycleView();
        }
        return rootView;
    }

    private void intiRecycleView() {
        recycleView.setPageable(false);
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
        recycleView.bindDataSource((startNo, endNo, page, callback) -> {
            if(UserUtil.isLogin()) {
                presenter.requestStrategyDataByLocal();
            }else {
                callback.onError("请先登录！");
            }

        });

        presenter.monitorMarketTicks();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void policySignalEventBus(PolicySignalMessage message) {
        LocationLog.getInstance().i("PolicyMonitorService request localData");
        recycleView.bindDataSource((startNo, endNo, page, callback) -> {
            presenter.requestStrategyDataByLocal();
        });
        //presenter.requestDataByLocal();
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

    }

    @Override
    public void showError(String message) {
        recycleView.getCallback().onError(message);
    }

    @Override
    public void showData(List<PolicySignal> list) {
        recycleView.getCallback().onResult(list);
    }
}
