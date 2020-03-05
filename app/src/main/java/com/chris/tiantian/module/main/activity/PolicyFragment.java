package com.chris.tiantian.module.main.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.chris.tiantian.entity.Policy;
import com.chris.tiantian.entity.PolicyMessage;
import com.chris.tiantian.module.main.presenter.PolicyPresenter;
import com.chris.tiantian.module.main.presenter.PolicyPresenterImpl;
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
public class PolicyFragment extends Fragment implements PolicyActionView {
    private View rootView;
    private MultipleStatusView statusView;
    private RecyclerView recycleView;
    private UserInfoSharedPreferences preferences;
    private PolicyPresenter presenter;
    private View refreshHintView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_policy, container, false);
            statusView = rootView.findViewById(R.id.policyFragment_status_view);
            recycleView = rootView.findViewById(R.id.policyFragment_listView);
            refreshHintView = rootView.findViewById(R.id.refresh_hint_view);

            preferences = UserInfoSharedPreferences.Companion.getInstance(getContext());
            presenter = new PolicyPresenterImpl(getContext(), this);
            initListView();
        }
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean isLocalRefresh = preferences.getBooleanValue(Constant.SP_LOADING_POLICY_DATABASE, false);
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


    private void initListView() {
        CommonAdapter<New> adapter = new CommonAdapter<>(getContext(), R.layout.listview_policy_item);
        adapter.setItemViewHolderCreator(new CommonAdapter.OnItemViewHolderCreator() {
            @Override
            public CommonItemViewHolder create(View itemView) {
                return new PolicyItemViewHolder(getContext(), itemView);
            }
        });
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object item) {
                Policy policy = (Policy)item;
                UserInfoSharedPreferences sharedPreferences = UserInfoSharedPreferences.Companion.getInstance(getContext());
                int currentPolicy = sharedPreferences.getIntValue(Constant.SP_CURRENT_POLICY, -1);
                if(currentPolicy != policy.getId()) {
                    if(currentPolicy == -1) {
                        changeCurrentPolicy(sharedPreferences, policy.getId());
                    }else {
                        new AlertDialog.Builder(getContext())
                                .setTitle("提示")
                                .setMessage("是否要切换策略?")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        changeCurrentPolicy(sharedPreferences, policy.getId());
                                    }
                                })
                                .show();
                    }

                }
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

    private void changeCurrentPolicy(UserInfoSharedPreferences sharedPreferences, int id) {
        sharedPreferences.putIntValue(Constant.SP_CURRENT_POLICY, id);
        sharedPreferences.putBooleanValue(Constant.SP_LOADING_POLICY_SIGNAL_DATABASE, false);
        recycleView.getAdapter().notifyDataSetChanged();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void policyEventBus(PolicyMessage policyMessage) {
        refreshHintView.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                refreshHintView.setVisibility(View.GONE);
            }
        }, 5000);
        presenter.requestDataByLocal();
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
    public void showData(List<Policy> list) {
        if(list != null && list.size() > 0) {
            statusView.showContent();
            ((CommonAdapter)recycleView.getAdapter()).setData(list);
        }else {
            statusView.showEmpty();
        }
    }

    class PolicyItemViewHolder extends CommonItemViewHolder<Policy> {
        View itemView;
        TextView nameView;
        TextView developerView;
        TextView marketView;
        TextView timeLevelView;
        TextView accuracyView;
        TextView typeView;
        TextView currentView;

        //UserInfoSharedPreferences preferences;

        public PolicyItemViewHolder(Context context, View itemView) {
            super(itemView);
            this.itemView = itemView;
            nameView = itemView.findViewById(R.id.policy_name);
            developerView = itemView.findViewById(R.id.policy_developerId);
            marketView = itemView.findViewById(R.id.policy_market);
            timeLevelView = itemView.findViewById(R.id.policy_timeLevel);
            accuracyView = itemView.findViewById(R.id.policy_accuracy);
            typeView = itemView.findViewById(R.id.policy_kind);
            currentView = itemView.findViewById(R.id.policy_current);

            //preferences = UserInfoSharedPreferences.Companion.getInstance(context);
        }

        @Override
        public void bindto(@NonNull Policy data) {
            int currentPolicy = preferences.getIntValue(Constant.SP_CURRENT_POLICY, -1);
            if(currentPolicy == data.getId()) {
                itemView.setBackground(getResources().getDrawable(R.drawable.item_selected_background));
                currentView.setVisibility(View.VISIBLE);
            }else {
                itemView.setBackground(getResources().getDrawable(R.drawable.item_clickable_background));
                currentView.setVisibility(View.INVISIBLE);
            }
            nameView.setText(data.getName());
            developerView.setText(data.getDeveloperId()+"");
            marketView.setText(data.getMarket()+"");
            timeLevelView.setText(data.getTimeLevel()+"");
            accuracyView.setText(data.getAccuracy()+"%");
        }
    }
}
