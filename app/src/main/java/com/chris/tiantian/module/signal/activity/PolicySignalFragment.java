package com.chris.tiantian.module.signal.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import com.chris.tiantian.module.signal.presenter.PolicySignalPresenter;
import com.chris.tiantian.module.signal.presenter.PolicySignalPresenterImpl;
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
public class PolicySignalFragment extends Fragment implements PolicySignalActionView {
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

        boolean isLocalRefresh = preferences.getBooleanValue(Constant.SP_LOADING_POLICY_SIGNAL_DATABASE, false);
        if(isLocalRefresh) {
            presenter.requestDataByLocal();
        }else {
            presenter.requestDataByNetwork();
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
        new Handler().postDelayed(new Runnable(){
            public void run() {
                refreshHintView.setVisibility(View.GONE);
            }
        }, 5000);
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
        recycleView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation(), DividerItemDecoration.DIVIDER_TYPE_INSET, layoutManager.getOrientation()));
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

        Context context;
        TextView directionView;
        TextView markView;
        TextView policyTimeLevelView;
        TextView kindView;
        TextView descriptionView;
        TextView signalNameView;
        TextView timeView;
        TextView stopPriceView;
        TextView currentPriceView;


        public SubItemViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            this.markView = itemView.findViewById(R.id.signal_policyMarket);
            this.policyTimeLevelView = itemView.findViewById(R.id.signal_policyTimeLevel);
            this.signalNameView = itemView.findViewById(R.id.signal_policyName);
            this.timeView = itemView.findViewById(R.id.signal_time);
            this.kindView = itemView.findViewById(R.id.signal_policyKind);
            this.descriptionView = itemView.findViewById(R.id.signal_description);
            this.directionView = itemView.findViewById(R.id.signal_direction);
            this.stopPriceView = itemView.findViewById(R.id.signal_stopPrice);
            this.currentPriceView = itemView.findViewById(R.id.signal_currPrice);
        }

        @Override
        public void bindto(@NonNull PolicySignal signal) {
            if(!TextUtils.isEmpty(signal.getDirection())) {
                directionView.setVisibility(View.VISIBLE);
                if(signal.getDirection().contains("做多") || signal.getDirection().contains("平空")) {
                    directionView.setBackground(context.getResources().getDrawable(R.drawable.signal_direction_duo_back));
                }else {
                    directionView.setBackground(context.getResources().getDrawable(R.drawable.signal_direction_kong_back));
                }
                directionView.setText(signal.getDirection());
            }else {
                directionView.setVisibility(View.INVISIBLE);
            }

            signalNameView.setText(signal.getPolicyName());
            timeView.setText(signal.getTime());
            policyTimeLevelView.setText(signal.getPolicyTimeLevel());
            markView.setText(signal.getPolicyMarket());
            kindView.setText(signal.getPolicyKind());
            descriptionView.setText(signal.getDescription());
            stopPriceView.setText(signal.getStopPrice()+"");
            currentPriceView.setText(signal.getCurrPrice()+"");
        }

    }

}
