package com.chris.tiantian.module.main.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anima.networkrequest.util.sharedprefs.UserInfoSharedPreferences;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.New;
import com.chris.tiantian.entity.PolicySignal;
import com.chris.tiantian.entity.PolicySignalMessage;
import com.chris.tiantian.module.main.presenter.PolicySignalPresenter;
import com.chris.tiantian.module.main.presenter.PolicySignalPresenterImpl;
import com.chris.tiantian.util.CommonAdapter;
import com.chris.tiantian.util.CommonItemViewHolder;
import com.chris.tiantian.view.DividerItemDecoration;
import com.chris.tiantian.view.MultipleStatusView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by jianjianhong on 19-12-18
 */
public class PolicySignalFragment2 extends Fragment implements PolicySignalActionView {
    private static final String TAG = "PolicySignal";
    private View rootView;
    private MultipleStatusView statusView;
    private RecyclerView recycleView;
    private UserInfoSharedPreferences preferences;
    private View refreshHintView;

    private PolicySignalPresenter presenter;
    private int currentPolicy = -1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_policy_signal2, container, false);
            statusView = rootView.findViewById(R.id.policySignalFragment_status_view);
            recycleView = rootView.findViewById(R.id.subFragment_listView);
            refreshHintView = rootView.findViewById(R.id.refresh_hint_view);
            preferences = UserInfoSharedPreferences.Companion.getInstance(getContext());
            presenter = new PolicySignalPresenterImpl(getContext(), this);

            intiRecycleView();
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        currentPolicy = preferences.getIntValue(Constant.SP_CURRENT_POLICY, -1);
        if(currentPolicy != -1 && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        boolean isNetworkRefresh = preferences.getBooleanValue(Constant.SP_LOADING_POLICY_SIGNAL_NETWORK, false);
        boolean isLocalRefresh = preferences.getBooleanValue(Constant.SP_LOADING_POLICY_SIGNAL_DATABASE, true);
        if(isNetworkRefresh) {
            presenter.requestDataByNetwork();
        }else if(isLocalRefresh) {
            presenter.requestDataByLocal();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void policySignalEventBus(PolicySignalMessage message) {
        refreshHintView.setVisibility(View.VISIBLE);
        presenter.requestDataByLocal();
    }

    private void intiRecycleView() {
        CommonAdapter<New> adapter = new CommonAdapter<>(getContext(), R.layout.listview_policy_signal_item);
        adapter.setItemViewHolderCreator(new CommonAdapter.OnItemViewHolderCreator() {
            @Override
            public CommonItemViewHolder create(View itemView) {
                return new SubItemViewHolder(getContext(), itemView);
            }
        });

        recycleView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //recycleView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation(), DividerItemDecoration.DIVIDER_TYPE_INSET, layoutManager.getOrientation()));
        recycleView.setLayoutManager(layoutManager);

        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(refreshHintView.getVisibility() == View.VISIBLE) {
                    refreshHintView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void showLoading() {
        statusView.showLoading();
    }

    @Override
    public void showError(String message) {
        statusView.showError(message);
    }

    @Override
    public void showData(List<PolicySignal> list) {
        if(list != null && list.size() > 0) {
            statusView.showContent();
            ((CommonAdapter)recycleView.getAdapter()).setData(list);
        }else {
            statusView.showEmpty();
        }
    }

    private class SubItemViewHolder extends CommonItemViewHolder<PolicySignal> {
        View markView;
        Context context;
        TextView actionView;
        TextView descriptionView;
        TextView signalNameView;
        TextView timeView;
        TextView stopPriceView;


        public SubItemViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            this.markView = itemView.findViewById(R.id.subItem_mark);
            this.signalNameView = itemView.findViewById(R.id.subList_title_tv);
            this.timeView = itemView.findViewById(R.id.subList_summary_tv);
            this.descriptionView = itemView.findViewById(R.id.subList_description_tv);
            this.actionView = itemView.findViewById(R.id.subList_action_bt);
            this.stopPriceView = itemView.findViewById(R.id.subList_stop_price_bt);
        }

        @Override
        public void bindto(@NonNull PolicySignal signal) {
            markView.setBackgroundColor(context.getResources().getColor(R.color.colorStatusSuccessLight));
            signalNameView.setText(signal.getDirection());
            timeView.setText(String.format("%s 当前策略: (%s)", signal.getTime(), signal.getPolicyName()));
            descriptionView.setText(signal.getDescription());
            actionView.setText("建议操作: "+signal.getDirection());
            stopPriceView.setText("建议止损: "+signal.getStopPrice());
        }

    }

}
