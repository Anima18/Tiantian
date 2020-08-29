package com.chris.tiantian.module.main.activity.fragment;

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
import com.chris.tiantian.util.CommonAdapter;
import com.chris.tiantian.util.CommonItemViewHolder;
import com.chris.tiantian.view.DividerItemDecoration;

import java.util.List;

/**
 * Created by jianjianhong on 20-3-25
 */
public class NewStrategyFragment extends Fragment {
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

            List<RankData.NewPolicyBean>rankBeans = getArguments().getParcelableArrayList(DATA);
            /*NewStrategyAdapter adapter = new NewStrategyAdapter(getContext(), rankBeans);
            listView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            listView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation(), DividerItemDecoration.DIVIDER_TYPE_INSET, layoutManager.getOrientation()));
            listView.setLayoutManager(layoutManager);*/
            CommonAdapter<RankData.NewPolicyBean> adapter = new CommonAdapter(getContext(), R.layout.listview_new_strategy_item);
            adapter.setItemViewHolderCreator(new CommonAdapter.OnItemViewHolderCreator() {
                @Override
                public CommonItemViewHolder create(View itemView) {
                    return new NewStrategyItemViewHolder(itemView);
                }
            });
            adapter.setData(rankBeans);
            listView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            listView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation(), DividerItemDecoration.DIVIDER_TYPE_INSET, layoutManager.getOrientation()));
            listView.setLayoutManager(layoutManager);
        }
        return rootView;
    }

    class NewStrategyItemViewHolder extends CommonItemViewHolder<RankData.NewPolicyBean> {
        TextView developerTv;
        TextView titleTv;
        TextView timeTv;
        TextView percentTv;
        public NewStrategyItemViewHolder(View itemView) {
            super(itemView);
            developerTv = itemView.findViewById(R.id.strategy_developer_tv);
            titleTv = itemView.findViewById(R.id.strategy_title_tv);
            timeTv = itemView.findViewById(R.id.strategy_launchTime_tv);
            percentTv = itemView.findViewById(R.id.strategy_percent_tv);
        }

        @Override
        public void bindto(@NonNull RankData.NewPolicyBean rankBean) {

            developerTv.setText(rankBean.getDeveloper());
            timeTv.setText(rankBean.getLaunchTime());
            percentTv.setText(rankBean.getBackAccuracy());
            titleTv.setText(rankBean.getName());
        }
    }

    /*static class NewStrategyAdapter extends RecyclerView.Adapter<NewStrategyAdapter.ViewHolder> {

        private Context context;
        private List<RankData.NewPolicyBean> weeklyRankBeans;

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
        public NewStrategyAdapter(Context context, List<RankData.NewPolicyBean> weeklyRankBeans) {
            this.context = context;
            this.weeklyRankBeans = weeklyRankBeans;
        }

        // Usually involves inflating a layout from XML and returning the holder
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Inflate the custom layout
            View itemView = LayoutInflater.from(context).inflate(R.layout.listview_new_strategy_item, parent, false);
            // Return a new holder instance
            return new ViewHolder(itemView, listener);
        }

        // Involves populating data into the item through holder
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            RankData.NewPolicyBean rankBean = weeklyRankBeans.get(position);
            developerTv.setText(rankBean.getDeveloper());
            timeTv.setText(rankBean.getLaunchTime());
            percentTv.setText(rankBean.getBackAccuracy());
            titleTv.setText(rankBean.getName());
        }

        // Return the total count of items
        @Override
        public int getItemCount() {
            return weeklyRankBeans.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView developerTv;
            TextView titleTv;
            TextView timeTv;
            TextView percentTv;

            public ViewHolder(final View itemView, OnItemClickListener listener) {
                super(itemView);
                developerTv = itemView.findViewById(R.id.strategy_developer_tv);
                titleTv = itemView.findViewById(R.id.strategy_title_tv);
                timeTv = itemView.findViewById(R.id.strategy_launchTime_tv);
                percentTv = itemView.findViewById(R.id.strategy_percent_tv);
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
    }*/

}
