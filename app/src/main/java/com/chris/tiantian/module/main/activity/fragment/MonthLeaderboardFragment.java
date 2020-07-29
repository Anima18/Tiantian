package com.chris.tiantian.module.main.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chris.tiantian.R;
import com.chris.tiantian.util.UIAdapter;
import com.chris.tiantian.view.DividerItemDecoration;

/**
 * Created by jianjianhong on 20-3-25
 */
public class MonthLeaderboardFragment extends Fragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_leader_board, container, false);
            RecyclerView listView = rootView.findViewById(R.id.testListView);
            listView.setNestedScrollingEnabled(false);
            UIAdapter adapter = new UIAdapter(getContext(), R.layout.listview_leader_board_item, 5);
            listView.setAdapter(adapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            listView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation(), DividerItemDecoration.DIVIDER_TYPE_INSET, layoutManager.getOrientation()));
            listView.setLayoutManager(layoutManager);
        }
        return rootView;

    }
}
