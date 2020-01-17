package com.chris.tiantian.util;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

/**
 * Created by Admin on 2015/8/19.
 */
public class UIAdapter extends RecyclerView.Adapter<UIViewHolder> {

    private Context context;
    private int layoutId;
    private int itemSize;

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
    public UIAdapter(Context context, int layoutId, int itemSize) {
        this.context = context;
        this.layoutId = layoutId;
        this.itemSize = itemSize;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public UIViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        // Return a new holder instance
        return new UIViewHolder(itemView, listener);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(UIViewHolder holder, int position) {
        if(holder.imageView != null) {
            Glide.with(context.getApplicationContext())
                    .load("https://img.36krcdn.com/20191219/v2_d11ac4ec7f984cb29cb1583bf8e4139f_img_png")
                    .centerCrop()
                    .into(holder.imageView);
        }

    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return itemSize;
    }


}