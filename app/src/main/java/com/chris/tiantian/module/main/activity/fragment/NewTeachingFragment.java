package com.chris.tiantian.module.main.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.RankData;
import com.chris.tiantian.util.CommonAdapter;
import com.chris.tiantian.util.CommonItemViewHolder;
import com.chris.tiantian.view.DividerItemDecoration;

import java.util.List;

/**
 * Created by jianjianhong on 20-3-25
 */
public class NewTeachingFragment extends Fragment {
    public static final String DATA = "DATA";

    private View rootView;
    private  RecyclerView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_leader_board, container, false);
            listView = rootView.findViewById(R.id.testListView);
            listView.setNestedScrollingEnabled(false);

            List<RankData.NewBooksBean>rankBeans = getArguments().getParcelableArrayList(DATA);
            CommonAdapter<RankData.NewBooksBean> adapter = new CommonAdapter(getContext(), R.layout.listview_new_teaching_item);
            adapter.setItemViewHolderCreator(new CommonAdapter.OnItemViewHolderCreator() {
                @Override
                public CommonItemViewHolder create(View itemView) {
                    return new BookItemViewHolder(itemView);
                }
            });
            adapter.setData(rankBeans);
            listView.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            listView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation(), DividerItemDecoration.DIVIDER_TYPE_INSET, layoutManager.getOrientation()));
            listView.setLayoutManager(layoutManager);
        }

        return rootView;

    }

    class BookItemViewHolder extends CommonItemViewHolder<RankData.NewBooksBean> {
        TextView titleTv;
        TextView authorTv;
        ImageView imageView;
        public BookItemViewHolder(View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.book_title_tv);
            authorTv = itemView.findViewById(R.id.book_author_tv);
            imageView = itemView.findViewById(R.id.book_imageView);
        }

        @Override
        public void bindto(@NonNull RankData.NewBooksBean data) {

            titleTv.setText(data.getName());
            authorTv.setText(data.getAuthor());
            Glide.with(getContext()).load(data.getImage()).placeholder(R.drawable.ic_broken_image_black_24dp).into(imageView);
        }
    }
}
