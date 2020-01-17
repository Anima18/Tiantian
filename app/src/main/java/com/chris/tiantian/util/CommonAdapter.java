package com.chris.tiantian.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2015/8/19.
 */
public class CommonAdapter<T> extends RecyclerView.Adapter {

    private Context context;
    private int layoutId;
    private List<T> list = new ArrayList();

    private OnItemClickListener listener;
    private CommonAdapter.OnItemViewHolderCreator itemViewHolderCreator;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position, Object item);
    }

    public interface OnItemViewHolderCreator {

        CommonItemViewHolder create(View itemView);

    }

    public void setItemViewHolderCreator(OnItemViewHolderCreator itemViewHolderCreator) {
        this.itemViewHolderCreator = itemViewHolderCreator;
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Pass in the context and users array into the constructor
    public CommonAdapter(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        // Return a new holder instance
        return itemViewHolderCreator.create(itemView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CommonItemViewHolder) holder).bindto(list.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null) {
                    listener.onItemClick(v, position, list.get(position));
                }
            }
        });

    }

    public void setData(List<? extends T> data) {
        this.list.clear();
        this.list.addAll(data);
        this.notifyDataSetChanged();
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return list.size();
    }


}