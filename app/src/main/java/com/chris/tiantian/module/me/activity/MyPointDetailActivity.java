package com.chris.tiantian.module.me.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anima.componentlib.toolbar.Toolbar;
import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.callback.DataListCallback;
import com.anima.networkrequest.callback.DataObjectCallback;
import com.anima.networkrequest.entity.RequestParam;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Order;
import com.chris.tiantian.entity.UserPoint;
import com.chris.tiantian.entity.UserPointLog;
import com.chris.tiantian.entity.dataparser.ListStatusDataParser;
import com.chris.tiantian.entity.dataparser.ObjectDataParser;
import com.chris.tiantian.entity.dataparser.ObjectStatusDataParser;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.UIAdapter;
import com.chris.tiantian.util.UserUtil;
import com.chris.tiantian.view.DividerItemDecoration;
import com.chris.tiantian.view.MultipleStatusView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by jianjianhong on 20-8-9
 */
public class MyPointDetailActivity extends AppCompatActivity {

    private MultipleStatusView statusView;
    private RecyclerView listView;

    private MyPointDetailAdapter adapter;
    private List<UserPointLog> userPointLogs = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_point_detail);

        Toolbar toolbar = findViewById(R.id.activity_toolBar);
        toolbar.setTitle("积分明细");

        statusView = findViewById(R.id.myPoint_status_view);
        listView = findViewById(R.id.myPointsDetail);

        initView();
        requestData();
    }

    private void initView() {
        adapter = new MyPointDetailAdapter(this, userPointLogs);
        listView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation(), DividerItemDecoration.DIVIDER_TYPE_INSET, layoutManager.getOrientation()));
        listView.setLayoutManager(layoutManager);
    }

    private void requestData() {
        statusView.showLoading();
        String url = String.format("%s/comment/apiv2/pointsUsage/%s", CommonUtil.getBaseUrl(), UserUtil.getUserId());
        new NetworkRequest<UserPointLog>(this)
                .url(url)
                .method(RequestParam.Method.GET)
                .asJson(true)
                .dataClass(UserPointLog.class)
                .dataParser(new ListStatusDataParser<UserPointLog>())
                .getList(new DataListCallback<UserPointLog>() {
                    @Override
                    public void onFailure(@NotNull String s) {
                        statusView.showError(s);
                    }

                    @Override
                    public void onSuccess(@org.jetbrains.annotations.Nullable List<? extends UserPointLog> list) {
                        if(list == null || list.size() == 0) {
                            statusView.showEmpty();
                        }else {
                            Collections.sort(list);
                            statusView.showContent();
                            userPointLogs.clear();
                            userPointLogs.addAll(list);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    static class MyPointDetailAdapter extends RecyclerView.Adapter<MyPointDetailAdapter.ViewHolder> {

        private Context context;
        private List<UserPointLog> userPointLogs;

        private OnItemClickListener listener;
        // Define the listener interface
        interface OnItemClickListener {
            void onItemClick(View itemView, int position);
        }

        // Define the method that allows the parent activity or fragment to define the listener
        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        // Pass in the context and users array into the constructor
        public MyPointDetailAdapter(Context context, List<UserPointLog> userPointLogs) {
            this.context = context;
            this.userPointLogs = userPointLogs;
        }

        // Usually involves inflating a layout from XML and returning the holder
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Inflate the custom layout
            View itemView = LayoutInflater.from(context).inflate(R.layout.listview_mypoint_detail_item, parent, false);
            // Return a new holder instance
            return new ViewHolder(itemView, listener);
        }

        // Involves populating data into the item through holder
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            UserPointLog log = userPointLogs.get(position);
            holder.contentView.setText(String.format("%s - %s", log.getBody(), log.getTarget()));
            if(log.getCount() > 0) {
                holder.countView.setText("+"+log.getCount());
            }else {
                holder.countView.setText(log.getCount()+"");
            }

            holder.timeView.setText(log.getTime());
        }

        // Return the total count of items
        @Override
        public int getItemCount() {
            return userPointLogs.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView contentView;
            TextView countView;
            TextView timeView;

            public ViewHolder(final View itemView, OnItemClickListener listener) {
                super(itemView);
                contentView = itemView.findViewById(R.id.user_point_detail_content);
                countView = itemView.findViewById(R.id.user_point_detail_count);
                timeView = itemView.findViewById(R.id.user_point_detail_time);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Triggers click upwards to the adapter on click
                        if (listener != null)
                            listener.onItemClick(itemView, getLayoutPosition());
                    }
                });
            }
        }
    }
}
