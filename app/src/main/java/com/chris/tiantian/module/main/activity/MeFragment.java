package com.chris.tiantian.module.main.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.tiantian.R;
import com.chris.tiantian.entity.ActionMenuItem;
import com.chris.tiantian.util.ProfileAdapter;
import com.chris.tiantian.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjianhong on 19-12-18
 */
public class MeFragment extends Fragment {
    private View rootView;
    private TextView userNameTv;
    private TextView userTypeTv;
    private RecyclerView dataRv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_me, container, false);
            initView(rootView);
            initData();
        }
        return rootView;
    }

    public void initView(View view) {
        dataRv = view.findViewById(R.id.profileAct_menu_rv);
        userNameTv = view.findViewById(R.id.profileAct_user_name);
        userTypeTv = view.findViewById(R.id.profileAct_user_department);
    }


    public void initData() {
        userNameTv.setText("张三");
        userTypeTv.setText("游客");

        List<ActionMenuItem> itemList = getActionMenuItems();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dataRv.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        dataRv.setLayoutManager(layoutManager);
        ProfileAdapter adapter = new ProfileAdapter(getContext(), itemList);
        adapter.setOnItemClickListener(new ProfileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Toast.makeText(getContext(), "暂未实现", Toast.LENGTH_SHORT).show();
            }
        });
        dataRv.setAdapter(adapter);

    }

    private List<ActionMenuItem> getActionMenuItems() {
        List<ActionMenuItem> itemList = new ArrayList<>();
        itemList.add(new ActionMenuItem("了解天天"));
        itemList.add(new ActionMenuItem("价格详情"));
        itemList.add(new ActionMenuItem("帮助"));
        itemList.add(new ActionMenuItem("版本更新(当前版本:1.0.1)", "有新版本啦"));
        itemList.add(new ActionMenuItem("意见反馈"));
        return itemList;
    }
}
