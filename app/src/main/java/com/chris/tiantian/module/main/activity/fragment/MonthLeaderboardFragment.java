package com.chris.tiantian.module.main.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chris.tiantian.MainActivity;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.RankData;
import com.chris.tiantian.util.UIAdapter;
import com.chris.tiantian.view.DividerItemDecoration;

import java.util.List;

/**
 * Created by jianjianhong on 20-3-25
 */
public class MonthLeaderboardFragment extends Fragment {
    public static final String DATA = "DATA";

    private View rootView;
    private  RecyclerView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_leader_board, container, false);

            listView = rootView.findViewById(R.id.testListView);
            listView.setNestedScrollingEnabled(false);
            List<RankData.WeeklyRankBean>rankBeans = getArguments().getParcelableArrayList(DATA);
            WeekLeaderboardAdapter adapter = new WeekLeaderboardAdapter(getContext(), rankBeans);
            listView.setAdapter(adapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            listView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation(), DividerItemDecoration.DIVIDER_TYPE_INSET, layoutManager.getOrientation()));
            listView.setLayoutManager(layoutManager);
        }

        return rootView;

    }

}
