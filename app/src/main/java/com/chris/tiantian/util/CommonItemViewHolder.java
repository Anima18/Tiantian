package com.chris.tiantian.util;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by jianjianhong on 18-12-25
 */
abstract public class CommonItemViewHolder<T> extends RecyclerView.ViewHolder {
    public CommonItemViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindto(@NonNull T data);
}
