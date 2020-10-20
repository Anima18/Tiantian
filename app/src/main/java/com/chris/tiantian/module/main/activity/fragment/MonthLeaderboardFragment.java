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
            CommonAdapter<RankData.WeeklyRankBean> adapter = new CommonAdapter(getContext(), R.layout.listview_leader_board_item);
            adapter.setItemViewHolderCreator(new CommonAdapter.OnItemViewHolderCreator() {
                @Override
                public CommonItemViewHolder create(View itemView) {
                    return new MonthLeaderItemViewHolder(itemView);
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

    class MonthLeaderItemViewHolder extends CommonItemViewHolder<RankData.WeeklyRankBean> {
        TextView indexTv;
        TextView titleTv;
        TextView timeTv;
        TextView countTv;
        TextView percentTv;
        TextView countLabel;
        TextView percentLabel;
        public MonthLeaderItemViewHolder(View itemView) {
            super(itemView);
            indexTv = itemView.findViewById(R.id.rank_index_tv);
            titleTv = itemView.findViewById(R.id.rank_title_tv);
            timeTv = itemView.findViewById(R.id.rank_time_tv);
            countTv = itemView.findViewById(R.id.rank_count_tv);
            percentTv = itemView.findViewById(R.id.rank_percent_tv);
            countLabel = itemView.findViewById(R.id.rank_count_label);
            percentLabel = itemView.findViewById(R.id.rank_percent_label);
            countLabel.setText("一月内出现次数: ");
            percentLabel.setText("月盈利率: ");
        }

        @Override
        public void bindto(@NonNull RankData.WeeklyRankBean rankBean) {

            indexTv.setText(rankBean.getRank());
            if("1".equals(rankBean.getRank())) {
                indexTv.setBackground(getContext().getDrawable(R.drawable.rank_index1_background));
                indexTv.setTextColor(getContext().getResources().getColor(R.color.index1_textColor));
            }else if("2".equals(rankBean.getRank())) {
                indexTv.setBackground(getContext().getDrawable(R.drawable.rank_index2_background));
                indexTv.setTextColor(getContext().getResources().getColor(R.color.index2_textColor));
            }else {
                indexTv.setBackground(getContext().getDrawable(R.drawable.rank_index3_background));
                indexTv.setTextColor(getContext().getResources().getColor(R.color.index3_textColor));
            }
            titleTv.setText(rankBean.getName());
            timeTv.setText(rankBean.getTimeLevel());
            countTv.setText(rankBean.getTimes());
            percentTv.setText(rankBean.getProfit());
        }
    }

}
