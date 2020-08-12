package com.chris.tiantian.module.main.activity.fragment;

import android.content.Context;
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

import com.chris.tiantian.R;
import com.chris.tiantian.entity.RankData;
import com.chris.tiantian.util.UIAdapter;
import com.chris.tiantian.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjianhong on 20-3-25
 */
public class WeekLeaderboardFragment extends Fragment {
    public static final String DATA = "DATA";
    private View rootView;
    private  RecyclerView listView;
    private WeekLeaderboardAdapter adapter;
    private List<RankData.WeeklyRankBean> rankBeans;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_leader_board, container, false);
            listView = rootView.findViewById(R.id.testListView);
            listView.setNestedScrollingEnabled(false);

            rankBeans = getArguments().getParcelableArrayList(DATA);
            adapter = new WeekLeaderboardAdapter(getContext(), rankBeans);
            listView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            listView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation(), DividerItemDecoration.DIVIDER_TYPE_INSET, layoutManager.getOrientation()));
            listView.setLayoutManager(layoutManager);
        }
        return rootView;

    }


}
