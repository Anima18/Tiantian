package com.chris.tiantian.module.message.fragment;

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
 * Created by jianjianhong on 20-7-30
 */
public class StrategyMessageFragment extends Fragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_message_strategy, container, false);
            RecyclerView listView = rootView.findViewById(R.id.strategyMessage_listView);
            listView.setNestedScrollingEnabled(false);
            UIAdapter adapter = new UIAdapter(getContext(), R.layout.listview_message_item, 5);
            listView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            listView.setLayoutManager(layoutManager);
        }
        return rootView;

    }
}
