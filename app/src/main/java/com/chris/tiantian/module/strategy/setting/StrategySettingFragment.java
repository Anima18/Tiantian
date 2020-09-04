package com.chris.tiantian.module.strategy.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anima.componentlib.toolbar.Toolbar;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.StrategyDetailItem;
import com.chris.tiantian.entity.StrategyTimeLevelGroup;
import com.chris.tiantian.util.GroupUtil;
import com.chris.tiantian.util.UIAdapter;
import com.chris.tiantian.view.DividerItemDecoration;
import com.chris.tiantian.view.MultipleStatusView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jianjianhong on 20-3-25
 */
public class StrategySettingFragment extends Fragment {
    private View rootView;
    private MultipleStatusView statusView;
    private StrategySettingAdapter adapter;
    private List<StrategyTimeLevelGroup> groupList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_leader_board, container, false);
            statusView = rootView.findViewById(R.id.status_view);
            statusView.showLoading();

            RecyclerView listView = rootView.findViewById(R.id.testListView);
            //listView.setNestedScrollingEnabled(false);
            adapter= new StrategySettingAdapter(getContext(), groupList);
            /*adapter.setOnItemClickListener(new StrategySettingAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    startActivity(new Intent(getContext(), StrategyDetailActivity.class));
                }
            });*/
            listView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            listView.setLayoutManager(layoutManager);
        }
        return rootView;
    }

    public StrategyTimeLevelGroup.TimeLevel getSetting() {
        StrategyTimeLevelGroup.TimeLevel selectItem = null;
        if(this.groupList != null) {
            for (StrategyTimeLevelGroup group : this.groupList) {
                if(group.getChoosed()) {
                    for (StrategyTimeLevelGroup.TimeLevel timeLevel : group.getTimeLevels()) {
                        if(timeLevel.isChoosed()) {
                            selectItem = timeLevel;
                            break;
                        }
                    }
                    break;
                }

            }
        }
        return selectItem;
    }

    public void showSetting(List<StrategyDetailItem> itemList) {
        if (itemList.size() == 0) {
            statusView.showEmpty();
        } else {
            statusView.showContent();
            Map<String, List<StrategyDetailItem>> nameGroupMap= GroupUtil.group(itemList, new GroupUtil.GroupBy<String>() {
                @Override
                public String groupBy(Object obj) {
                    return ((StrategyDetailItem)obj).getName();
                }
            });

            groupList.clear();;
            Iterator<String> iterator = nameGroupMap.keySet().iterator();
            while (iterator.hasNext()) {
                List<StrategyDetailItem> items = nameGroupMap.get(iterator.next());
                StrategyTimeLevelGroup group = new StrategyTimeLevelGroup(items.get(0));
                for(StrategyDetailItem item : items) {
                    StrategyTimeLevelGroup.TimeLevel timeLevel = new StrategyTimeLevelGroup.TimeLevel(item.getId(), item.getTimeLevel(), item.isChoosed());
                    if(item.isChoosed()) {
                        group.setChoosed(true);
                    }
                    group.addTimeLevel(timeLevel);
                }
                groupList.add(group);
            }
            Collections.sort(groupList);
            adapter.notifyDataSetChanged();
        }
    }

    static class StrategySettingAdapter extends RecyclerView.Adapter<StrategySettingAdapter.ViewHolder> {

        private Context context;
        private List<StrategyTimeLevelGroup> strategyList;

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
        public StrategySettingAdapter(Context context, List<StrategyTimeLevelGroup> strategyList) {
            this.context = context;
            this.strategyList = strategyList;
        }

        // Usually involves inflating a layout from XML and returning the holder
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Inflate the custom layout
            View itemView = LayoutInflater.from(context).inflate(R.layout.listview_strategy_setting_item, parent, false);
            // Return a new holder instance
            return new ViewHolder(itemView, listener);
        }

        // Involves populating data into the item through holder
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            StrategyTimeLevelGroup group = strategyList.get(position);
            holder.choosedRadio.setChecked(group.getChoosed());
            holder.nameView.setText(group.getName());

            holder.timeLevel1View.setBackground(null);
            holder.timeLevel1View.setTextColor(context.getResources().getColor(R.color.disabled_text_dark_color));
            //holder.timeLevel1View.setOnClickListener(null);
            holder.timeLevel2View.setBackground(null);
            holder.timeLevel2View.setTextColor(context.getResources().getColor(R.color.disabled_text_dark_color));
            //holder.timeLevel2View.setOnClickListener(null);
            holder.timeLevel3View.setBackground(null);
            holder.timeLevel3View.setTextColor(context.getResources().getColor(R.color.disabled_text_dark_color));
            //holder.timeLevel3View.setOnClickListener(null);

            for(StrategyTimeLevelGroup.TimeLevel timeLevel : group.getTimeLevels()) {
                if(timeLevel.getTimeLevel().equals("15min")) {
                    if(timeLevel.isChoosed()) {
                        holder.timeLevel1View.setTextColor(context.getResources().getColor(R.color.white));
                        holder.timeLevel1View.setBackground(context.getDrawable(R.drawable.time_background));
                        holder.timeLevel1View.setSelected(true);
                    }else {
                        holder.timeLevel1View.setBackground(null);
                        holder.timeLevel1View.setTextColor(context.getResources().getColor(R.color.primary_text_dark_color));
                        holder.timeLevel1View.setSelected(false);
                    }
                    holder.timeLevel1View.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateTimeLevel(group, timeLevel, holder.timeLevel1View);
                        }
                    });
                }else if(timeLevel.getTimeLevel().equals("1hour")) {
                    if(timeLevel.isChoosed()) {
                        holder.timeLevel2View.setTextColor(context.getResources().getColor(R.color.white));
                        holder.timeLevel2View.setBackground(context.getDrawable(R.drawable.time_background));
                        holder.timeLevel2View.setSelected(true);
                    }else {
                        holder.timeLevel2View.setBackground(null);
                        holder.timeLevel2View.setTextColor(context.getResources().getColor(R.color.primary_text_dark_color));
                        holder.timeLevel2View.setSelected(false);
                    }
                    holder.timeLevel2View.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateTimeLevel(group, timeLevel, holder.timeLevel2View);
                        }
                    });
                }else if(timeLevel.getTimeLevel().equals("4hour")) {
                    if(timeLevel.isChoosed()) {
                        holder.timeLevel3View.setTextColor(context.getResources().getColor(R.color.white));
                        holder.timeLevel3View.setBackground(context.getDrawable(R.drawable.time_background));
                        holder.timeLevel3View.setSelected(true);
                    }else {
                        holder.timeLevel3View.setBackground(null);
                        holder.timeLevel3View.setTextColor(context.getResources().getColor(R.color.primary_text_dark_color));
                        holder.timeLevel3View.setSelected(false);
                    }
                    holder.timeLevel3View.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateTimeLevel(group, timeLevel, holder.timeLevel3View);
                        }
                    });
                }

                /*holder.choosedRadio.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(group.getChoosed()) {
                            group.setChoosed(false);
                            for (StrategyTimeLevelGroup.TimeLevel timeLevel : group.getTimeLevels()) {
                                timeLevel.setChoosed(false);
                            }
                            notifyItemChanged(position);
                            return false;
                        }else {
                            Toast.makeText(context, "请选择策略时间段！", Toast.LENGTH_SHORT).show();
                            return true;
                        }

                    }
                });*/
            }
        }

        private void updateTimeLevel(StrategyTimeLevelGroup selectGroup, StrategyTimeLevelGroup.TimeLevel selectedTimeLevel, View view) {
            for(StrategyTimeLevelGroup group : strategyList) {
                group.setChoosed(false);
                for (StrategyTimeLevelGroup.TimeLevel timeLevel : group.getTimeLevels()) {
                    timeLevel.setChoosed(false);
                }
            }
            if(!view.isSelected()) {
                selectGroup.setChoosed(true);
                selectedTimeLevel.setChoosed(true);
                view.setSelected(true);
            }else {
                view.setSelected(false);
            }

            notifyDataSetChanged();
        }

        // Return the total count of items
        @Override
        public int getItemCount() {
            return strategyList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            RadioButton choosedRadio;
            TextView nameView;
            TextView timeLevel1View;
            TextView timeLevel2View;
            TextView timeLevel3View;

            public ViewHolder(final View itemView, OnItemClickListener listener) {
                super(itemView);
                choosedRadio = itemView.findViewById(R.id.strategy_setting_item_radio);
                nameView = itemView.findViewById(R.id.strategy_setting_name);
                timeLevel1View = itemView.findViewById(R.id.strategy_setting_time1);
                timeLevel2View = itemView.findViewById(R.id.strategy_setting_time2);
                timeLevel3View = itemView.findViewById(R.id.strategy_setting_time3);
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
