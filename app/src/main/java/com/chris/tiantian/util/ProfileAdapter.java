package com.chris.tiantian.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chris.tiantian.R;
import com.chris.tiantian.entity.ActionMenuItem;

import java.util.List;


/**
 * Created by chris on 2015/11/18
 */
public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    // Store the context for later use
    private Context context;
    private List<ActionMenuItem> dList;

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
    public ProfileAdapter(Context context, List<ActionMenuItem> dList) {
        this.context = context;
        this.dList = dList;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(context).inflate(R.layout.listview_profile_item, parent, false);
        // Return a new holder instance
        return new ViewHolder(context, itemView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        ActionMenuItem data = dList.get(position);
        holder.titleTv.setText(data.getTitle());
        Integer imageResId = data.getIconResId();
        if(imageResId != null && imageResId != 0) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageResource(data.getIconResId());

            Integer imageColor = data.getIconColor();
            if(imageColor != null && imageColor != 0) {
                Drawable tintIcon = DrawableCompat.wrap(holder.imageView.getDrawable());
                DrawableCompat.setTintList(tintIcon, ColorStateList.valueOf(context.getResources().getColor(imageColor)));
                holder.imageView.setImageDrawable(tintIcon);
            }

            if(TextUtils.isEmpty(data.getValue())) {
                holder.indicatorView.setVisibility(View.VISIBLE);
                holder.valueTv.setVisibility(View.GONE);
            }else {
                holder.valueTv.setVisibility(View.VISIBLE);
                holder.valueTv.setText(data.getValue());
                holder.indicatorView.setVisibility(View.GONE);
            }
        }else {
            holder.imageView.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(data.getValue())) {
            holder.valueTv.setVisibility(View.VISIBLE);
            holder.valueTv.setText(data.getValue());
        }else {
            holder.valueTv.setVisibility(View.GONE);
        }
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return dList.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView titleTv;
        public ImageView imageView;
        public TextView valueTv;
        public ImageView indicatorView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(Context context, final View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.profileLv_title);
            imageView = itemView.findViewById(R.id.profileLv_image);
            valueTv = itemView.findViewById(R.id.profileLv_value);
            indicatorView = itemView.findViewById(R.id.profileLv_indicator);

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