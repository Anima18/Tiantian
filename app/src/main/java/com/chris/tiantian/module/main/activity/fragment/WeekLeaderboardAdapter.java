package com.chris.tiantian.module.main.activity.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chris.tiantian.R;
import com.chris.tiantian.entity.RankData;

import java.util.List;

/**
 * Created by jianjianhong on 20-8-12
 */
public class WeekLeaderboardAdapter extends RecyclerView.Adapter<WeekLeaderboardAdapter.ViewHolder> {

    private Context context;
    private List<RankData.WeeklyRankBean> weeklyRankBeans;

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
    public WeekLeaderboardAdapter(Context context, List<RankData.WeeklyRankBean> weeklyRankBeans) {
        this.context = context;
        this.weeklyRankBeans = weeklyRankBeans;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(context).inflate(R.layout.listview_leader_board_item, parent, false);
        // Return a new holder instance
        return new ViewHolder(itemView, listener);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RankData.WeeklyRankBean rankBean = weeklyRankBeans.get(position);
        holder.indexTv.setText(rankBean.getRank());
        if("1".equals(rankBean.getRank())) {
            holder.indexTv.setBackground(context.getDrawable(R.drawable.rank_index1_background));
            holder.indexTv.setTextColor(context.getResources().getColor(R.color.index1_textColor));
        }else if("2".equals(rankBean.getRank())) {
            holder.indexTv.setBackground(context.getDrawable(R.drawable.rank_index2_background));
            holder.indexTv.setTextColor(context.getResources().getColor(R.color.index2_textColor));
        }else {
            holder.indexTv.setBackground(context.getDrawable(R.drawable.rank_index3_background));
            holder.indexTv.setTextColor(context.getResources().getColor(R.color.index3_textColor));
        }
        holder.titleTv.setText(rankBean.getName());
        holder.timeTv.setText(rankBean.getTimeLevel());
        holder.countTv.setText(rankBean.getTimes());
        holder.percentTv.setText(rankBean.getProfit());
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return weeklyRankBeans.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView indexTv;
        TextView titleTv;
        TextView timeTv;
        TextView countTv;
        TextView percentTv;

        public ViewHolder(final View itemView, OnItemClickListener listener) {
            super(itemView);
            indexTv = itemView.findViewById(R.id.rank_index_tv);
            titleTv = itemView.findViewById(R.id.rank_title_tv);
            timeTv = itemView.findViewById(R.id.rank_time_tv);
            countTv = itemView.findViewById(R.id.rank_count_tv);
            percentTv = itemView.findViewById(R.id.rank_percent_tv);
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