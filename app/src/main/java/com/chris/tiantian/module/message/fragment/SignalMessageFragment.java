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
import com.chris.tiantian.entity.User;
import com.chris.tiantian.util.UIAdapter;
import com.chris.tiantian.util.UserUtil;
import com.chris.tiantian.view.MultipleStatusView;

/**
 * Created by jianjianhong on 20-7-30
 */
public class SignalMessageFragment extends Fragment {
    private View rootView;
    private MultipleStatusView statusView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_message_signal, container, false);
            statusView = rootView.findViewById(R.id.signalMessageFragment_status_view);

            RecyclerView listView = rootView.findViewById(R.id.signalMessage_listView);
            listView.setNestedScrollingEnabled(false);
            UIAdapter adapter = new UIAdapter(getContext(), R.layout.listview_message_item, 5);
            listView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            listView.setLayoutManager(layoutManager);
        }
        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        /*if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }*/
        requestData();
    }

    private void requestData() {
        if(UserUtil.isLogin()) {
            statusView.showEmpty();
        }else {
            statusView.showError("请先登录！");
        }
    }

}
