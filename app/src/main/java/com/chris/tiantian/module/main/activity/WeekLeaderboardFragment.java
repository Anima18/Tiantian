package com.chris.tiantian.module.main.activity;

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

/**
 * Created by jianjianhong on 20-3-25
 */
public class WeekLeaderboardFragment extends Fragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_leader_board, container, false);
            RecyclerView listView = rootView.findViewById(R.id.testListView);
            listView.setNestedScrollingEnabled(false);
            UIAdapter adapter = new UIAdapter(getContext(), R.layout.listview_leader_board_item, 10);
            listView.setAdapter(adapter);
            listView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        return rootView;

    }
}
