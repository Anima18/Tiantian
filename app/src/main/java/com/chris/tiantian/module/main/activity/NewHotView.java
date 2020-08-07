package com.chris.tiantian.module.main.activity;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chris.tiantian.R;
import com.chris.tiantian.entity.New;

import java.util.List;

public class NewHotView extends LinearLayout {

    private List<New> dataList;
    private View firstNewLayout;
    private ImageView firstNewImageView;
    private TextView firstNewTitle;
    private View secondNewLayout;
    private TextView secondNewTitle;
    private View threeNewLayout;
    private TextView threeNewTitle;

    public NewHotView(Context context) {
        this(context, null);
    }

    public NewHotView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewHotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View rootView = LayoutInflater.from(context).inflate(R.layout.view_new_hot, this, true);
        firstNewLayout = rootView.findViewById(R.id.firstNew_layout);
        firstNewImageView = rootView.findViewById(R.id.firstNew_imageView);
        firstNewTitle = rootView.findViewById(R.id.firstNew_title);
        secondNewLayout = rootView.findViewById(R.id.secondNew_layout);
        secondNewTitle = rootView.findViewById(R.id.secondNew_title);
        threeNewLayout = rootView.findViewById(R.id.threeNew_layout);
        threeNewTitle = rootView.findViewById(R.id.threeNew_title);
    }

    public void setDataList(List<New> dataList) {
        this.dataList = dataList;
        firstNewTitle.setText(dataList.get(0).getTitle());
        secondNewTitle.setText("       "+dataList.get(1).getTitle());
        threeNewTitle.setText("       "+dataList.get(2).getTitle());

        firstNewLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TiantianDetailActivity.class);
                intent.putExtra(TiantianDetailActivity.NEW_DATA, dataList.get(0));
                getContext().startActivity(intent);
            }
        });

        secondNewLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TiantianDetailActivity.class);
                intent.putExtra(TiantianDetailActivity.NEW_DATA, dataList.get(1));
                getContext().startActivity(intent);
            }
        });

        threeNewLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TiantianDetailActivity.class);
                intent.putExtra(TiantianDetailActivity.NEW_DATA, dataList.get(2));
                getContext().startActivity(intent);
            }
        });
    }
}
