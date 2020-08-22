package com.chris.tiantian.module.main.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.New;

import java.util.List;

public class NewHotView extends LinearLayout {

    private View emptyView;
    private View newListView;
    private View firstNewLayout;
    private ImageView firstNewImageView;
    private TextView firstNewTitle;
    private View secondNewLayout;
    private TextView secondNewTitle;
    private View threeNewLayout;
    private TextView threeNewTitle;
    private View fourNewLayout;
    private TextView fourNewTitle;

    public NewHotView(Context context) {
        this(context, null);
    }

    public NewHotView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewHotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View rootView = LayoutInflater.from(context).inflate(R.layout.view_new_hot, this, true);
        emptyView = rootView.findViewById(R.id.empty_title_view);
        newListView = rootView.findViewById(R.id.new_List_view);
        firstNewLayout = rootView.findViewById(R.id.firstNew_layout);
        firstNewImageView = rootView.findViewById(R.id.firstNew_imageView);
        firstNewTitle = rootView.findViewById(R.id.firstNew_title);
        secondNewLayout = rootView.findViewById(R.id.secondNew_layout);
        secondNewTitle = rootView.findViewById(R.id.secondNew_title);
        threeNewLayout = rootView.findViewById(R.id.threeNew_layout);
        threeNewTitle = rootView.findViewById(R.id.threeNew_title);
        fourNewLayout = rootView.findViewById(R.id.fourNew_layout);
        fourNewTitle = rootView.findViewById(R.id.fourNew_title);
    }

    public void setDataList(List<New> dataList) {
        if(dataList == null || dataList.size() == 0) {
            emptyView.setVisibility(VISIBLE);
            newListView.setVisibility(GONE);
        }else {
            emptyView.setVisibility(GONE);
            newListView.setVisibility(VISIBLE);

            int size = dataList.size();
            if(size > 0) {
                New firstNew = dataList.get(0);
                firstNewLayout.setVisibility(VISIBLE);
                firstNewTitle.setText(firstNew.getTitle());
                Glide.with(getContext()).load(firstNew.getImg()).placeholder(R.drawable.ic_broken_image_black_24dp).into(firstNewImageView);
                firstNewLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toDetailPage(firstNew);
                    }
                });
            }else {
                firstNewLayout.setVisibility(GONE);
            }

            if(size > 1) {
                secondNewLayout.setVisibility(VISIBLE);
                secondNewTitle.setText("       "+dataList.get(1).getTitle());
                secondNewLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toDetailPage(dataList.get(1));
                    }
                });
            }else {
                secondNewLayout.setVisibility(GONE);
            }

            if(size > 2) {
                threeNewTitle.setVisibility(VISIBLE);
                threeNewTitle.setText("       "+dataList.get(2).getTitle());
                threeNewLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toDetailPage(dataList.get(2));
                    }
                });
            }else {
                threeNewLayout.setVisibility(GONE);
            }

            if(size > 3) {
                fourNewLayout.setVisibility(VISIBLE);
                fourNewTitle.setText("       "+dataList.get(3).getTitle());
                fourNewLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toDetailPage(dataList.get(3));
                    }
                });
            }else {
                fourNewLayout.setVisibility(GONE);
            }

        }
    }

    private void toDetailPage(New data) {
        if(TextUtils.isEmpty(data.getDetail())) {
            Intent intent = new Intent(getContext(), TiantianDetailActivity.class);
            intent.putExtra(TiantianDetailActivity.NEW_DATA, data);
            getContext().startActivity(intent);
        }else {
            Intent intent = new Intent(getContext(), AdvertiseDetailActivity.class);
            intent.putExtra(AdvertiseDetailActivity.ADVERTISE_DETAIL, data);
            getContext().startActivity(intent);
        }

    }
}
