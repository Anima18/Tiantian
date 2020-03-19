package com.chris.tiantian.module.main.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anima.networkrequest.DataListCallback;
import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.entity.RequestParam;
import com.anima.networkrequest.util.sharedprefs.UserInfoSharedPreferences;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.NetworkDataParser;
import com.chris.tiantian.entity.New;
import com.chris.tiantian.entity.Policy;
import com.chris.tiantian.util.CommonAdapter;
import com.chris.tiantian.util.CommonItemViewHolder;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.view.DividerItemDecoration;
import com.chris.tiantian.view.MultipleStatusView;
import com.ut.raw.paginglistview.PagingRecycleAdapter;
import com.ut.raw.paginglistview.PagingRecycleView;
import com.ut.raw.paginglistview.viewholder.PagingRecycleItemViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by jianjianhong on 19-12-18
 */
public class PolicyFragment extends Fragment{
    private View rootView;
    private PagingRecycleView recycleView;
    private UserInfoSharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_policy, container, false);
            recycleView = rootView.findViewById(R.id.policyFragment_listView);

            preferences = UserInfoSharedPreferences.Companion.getInstance(getContext());
            initListView();
            requestDataByNetwork();
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void requestDataByNetwork() {
        recycleView.bindDataSource(new PagingRecycleView.OnDataSource() {
            @Override
            public void loadData(int i, int i1, int i2, PagingRecycleView.LoadCallback loadCallback) {
                String url = String.format("%s/comment/apiv2/policylist", CommonUtil.getBaseUrl());
                new NetworkRequest<Policy>(getContext())
                        .url(url)
                        .method(RequestParam.Method.GET)
                        .dataClass(Policy.class)
                        .dataParser(new NetworkDataParser<Policy>())
                        .getList(new DataListCallback<Policy>() {
                            @Override
                            public void onFailure(@NotNull String s) {
                                loadCallback.onError(s);
                            }

                            @Override
                            public void onSuccess(@NotNull List<? extends Policy> list) {
                                loadCallback.onResult((List<Policy>)list);
                            }
                        });
            }
        });

    }

    private void initListView() {
        recycleView.setPageable(false);
        recycleView.setItemViewHolderCreator(new PagingRecycleAdapter.OnItemViewHolderCreator() {
            @Override
            public PagingRecycleItemViewHolder create(View itemView) {
                return new PolicyItemViewHolder(getContext(), itemView);
            }
        });

        recycleView.setOnItemClickListener(new PagingRecycleAdapter.OnItemClickListener() {
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
/*
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
        recycleView.setLayoutManager(layoutManager);*/

    }

    private void changeCurrentPolicy(UserInfoSharedPreferences sharedPreferences, int id) {
        sharedPreferences.putIntValue(Constant.SP_CURRENT_POLICY, id);
        sharedPreferences.putBooleanValue(Constant.SP_LOADING_POLICY_SIGNAL_DATABASE, false);
        recycleView.getAdapter().notifyDataSetChanged();
    }



    class PolicyItemViewHolder extends PagingRecycleItemViewHolder<Policy> {
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

        @Override
        public View itemView() {
            return null;
        }

        @Override
        public View backgroundView() {
            return null;
        }
    }
}
