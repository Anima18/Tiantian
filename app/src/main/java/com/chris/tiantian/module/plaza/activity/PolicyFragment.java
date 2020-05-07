package com.chris.tiantian.module.plaza.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.anima.componentlib.paginglistview.PagingRecycleAdapter;
import com.anima.componentlib.paginglistview.PagingRecycleView;
import com.anima.componentlib.paginglistview.viewholder.PagingRecycleItemViewHolder;
import com.anima.networkrequest.DataListCallback;
import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.entity.RequestParam;
import com.anima.networkrequest.util.sharedprefs.UserInfoSharedPreferences;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Constant;
import com.chris.tiantian.entity.Policy;
import com.chris.tiantian.entity.dataparser.ListDataParser;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.PreferencesUtil;

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

            preferences = PreferencesUtil.getUserInfoPreference();
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
                        .dataParser(new ListDataParser<Policy>())
                        .dataFormat(RequestParam.DataFormat.LIST)
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
                //UserInfoSharedPreferences sharedPreferences = UserInfoSharedPreferences.Companion.getInstance(getContext());
                int currentPolicy = preferences.getIntValue(Constant.SP_CURRENT_POLICY, -1);
                if(currentPolicy != policy.getId()) {
                    if(currentPolicy == -1) {
                        changeCurrentPolicy(policy.getId());
                    }else {
                        new AlertDialog.Builder(getContext())
                                .setTitle("提示")
                                .setMessage("是否要切换策略?")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        changeCurrentPolicy(policy.getId());
                                    }
                                })
                                .show();
                    }

                }
            }
        });
    }

    private void changeCurrentPolicy(int id) {
        preferences.putIntValue(Constant.SP_CURRENT_POLICY, id);
        preferences.putBooleanValue(Constant.SP_LOADING_POLICY_SIGNAL_DATABASE, false);
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
