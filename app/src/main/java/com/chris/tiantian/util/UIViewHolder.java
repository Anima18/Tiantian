package com.chris.tiantian.util;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chris.tiantian.R;

/**
 * Created by jianjianhong on 19-12-23
 */
public class UIViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    public UIViewHolder(final View itemView, UIAdapter.OnItemClickListener listener) {
        super(itemView);
        //imageView = itemView.findViewById(R.id.list_imageView);
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
