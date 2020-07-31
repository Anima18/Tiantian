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
public class AllMessageFragment extends Fragment {
    private View rootView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_message_all, container, false);
            RecyclerView currencyListView = rootView.findViewById(R.id.currency_listView);
            UIAdapter adapter = new UIAdapter(getContext(), R.layout.listview_currency_item, 5);
            currencyListView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            currencyListView.setLayoutManager(layoutManager);

            RecyclerView listView = rootView.findViewById(R.id.allMessage_listView);
            listView.setNestedScrollingEnabled(false);
            UIAdapter adapter1 = new UIAdapter(getContext(), R.layout.listview_message_item, 5);
            listView.setAdapter(adapter1);
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
            listView.setLayoutManager(layoutManager1);
        }
        return rootView;

    }
}
