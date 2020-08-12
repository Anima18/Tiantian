package com.chris.tiantian.module.me.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.PointData;
import com.chris.tiantian.util.UIAdapter;
import com.chris.tiantian.util.UIViewHolder;

import java.util.List;

/**
 * Created by Admin on 2015/8/19.
 */
public class FreedomPointAdapter extends RecyclerView.Adapter<FreedomPointAdapter.FreedomPointViewHolder> {

    private Context context;
    private List<PointData> pointList;

    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Pass in the context and users array into the constructor
    public FreedomPointAdapter(Context context, List<PointData> pointList) {
        this.context = context;
        this.pointList = pointList;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public FreedomPointViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(context).inflate( R.layout.listview_point_buy_item, parent, false);
        // Return a new holder instance
        return new FreedomPointViewHolder(itemView, listener);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(FreedomPointViewHolder holder, int position) {
        PointData pointData = pointList.get(position);
        holder.pointValue.setText(pointData.point+"");
        holder.itemView.setSelected(pointData.selected);
        if(pointData.selected) {
            holder.pointImage.setColorFilter(context.getResources().getColor(R.color.white));
            holder.pointValue.setTextColor(context.getResources().getColor(R.color.white));
        }else {
            holder.pointImage.setColorFilter(context.getResources().getColor(R.color.secondary_text_dark_color));
            holder.pointValue.setTextColor(context.getResources().getColor(R.color.secondary_text_dark_color));
        }
        holder.itemView.setEnabled(pointData.enabled);

    }

    public void setPointList(List<PointData> pointList) {
        this.pointList = pointList;
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return pointList.size();
    }

    class FreedomPointViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView pointValue;
        ImageView pointImage;

        public FreedomPointViewHolder(final View itemView, FreedomPointAdapter.OnItemClickListener listener) {
            super(itemView);

            this.itemView = itemView;
            pointImage = itemView.findViewById(R.id.pointImage);
            pointValue = itemView.findViewById(R.id.pointValue);
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