package com.chris.tiantian.module.main.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.chris.tiantian.R;

public class NewHotView extends LinearLayout {
    public NewHotView(Context context) {
        this(context, null);
    }

    public NewHotView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewHotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.view_new_hot, this, true);
    }
}
