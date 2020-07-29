package com.chris.tiantian.module.main.activity;

import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.RequestStream;
import com.anima.networkrequest.entity.RequestParam;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Advertise;
import com.chris.tiantian.entity.New;
import com.chris.tiantian.entity.dataparser.ListDataParser;
import com.chris.tiantian.module.main.activity.fragment.MonthLeaderboardFragment;
import com.chris.tiantian.module.main.activity.fragment.NewStrategyFragment;
import com.chris.tiantian.module.main.activity.fragment.NewTeachingFragment;
import com.chris.tiantian.module.main.activity.fragment.WeekLeaderboardFragment;
import com.chris.tiantian.module.plaza.activity.AuctionFragment;
import com.chris.tiantian.module.plaza.adapter.ViewPagerAdapter;
import com.chris.tiantian.util.CommonAdapter;
import com.chris.tiantian.util.CommonItemViewHolder;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.GlideImageLoader;
import com.chris.tiantian.view.DividerItemDecoration;
import com.chris.tiantian.view.MultipleStatusView;
import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjianhong on 19-12-18
 */
public class TiantianFragment extends Fragment implements OnBannerListener {
    private View rootView;
    private Banner banner;
    private MultipleStatusView statusView;
    private RecyclerView recyclerView;

    private TabLayout mSlidingTabLayout;
    private TestViewPager mViewPager;
    private List<Fragment> fragmentList;
    private List<String> fragmetNameList;

    private List<Advertise> advertises = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_tiantian, container, false);
            View toolbar = rootView.findViewById(R.id.activity_toolBar);
            toolbar.setVisibility(View.GONE);
            banner = rootView.findViewById(R.id.ttFragment_banner);
            statusView = rootView.findViewById(R.id.ttFragment_status_view);

            mSlidingTabLayout = rootView.findViewById(R.id.mainFrm_slidingTabLayout);
            mViewPager = rootView.findViewById(R.id.mainFrm_viewpager);
            initTabView();

            //recyclerView = rootView.findViewById(R.id.content_listView);
            //initNewsListView();
            requestData();
            statusView.setOnRetryClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestData();
                }
            });
            statusView.setOnViewStatusChangeListener(new MultipleStatusView.OnViewStatusChangeListener() {
                @Override
                public void onChange(int oldViewStatus, int newViewStatus) {

                }
            });
        }

        return rootView;
    }

    public void initTabView() {
        if(fragmentList == null) {
            fragmentList = new ArrayList<>();
            fragmetNameList = new ArrayList<>();
            fragmentList.add(new WeekLeaderboardFragment());
            fragmentList.add(new MonthLeaderboardFragment());
            fragmentList.add(new NewStrategyFragment());
            fragmentList.add(new NewTeachingFragment());
            fragmetNameList.add("周排行");
            fragmetNameList.add("月排行");
            fragmetNameList.add("新策略");
            fragmetNameList.add("新教材");
        }

        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragmentList, fragmetNameList));
        mSlidingTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*mScrollView.setOnScrollBottomListener(new TestScrollView.OnScrollBottomListener() {
            @Override
            public void onScrollBottom() {
                if (mTabLayout.getSelectedTabPosition() == 0) {
                    ((PersonalCourseFragment) fragmentList.get(0)).loadData();
                } else if (mTabLayout.getSelectedTabPosition() == 1) {
                    ((PersonalNewsFragment) fragmentList.get(1)).loadData();
                } else if (mTabLayout.getSelectedTabPosition() == 2) {
                    ((PersonalIssueAnswerFragment) fragmentList.get(2)).loadData();
                }
            }
        });*/

    }

    private void initNewsListView() {
        CommonAdapter<New> adapter = new CommonAdapter<>(getContext(), R.layout.listview_new_item);
        adapter.setItemViewHolderCreator(new CommonAdapter.OnItemViewHolderCreator() {
            @Override
            public CommonItemViewHolder create(View itemView) {
                return new NewItemViewHolder(itemView);
            }
        });

        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object item) {
                Intent intent = new Intent(getContext(), TiantianDetailActivity.class);
                intent.putExtra(TiantianDetailActivity.NEW_DATA, (New)item);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation(), DividerItemDecoration.DIVIDER_TYPE_INSET, layoutManager.getOrientation()));
        recyclerView.setLayoutManager(layoutManager);
    }

    private void requestData() {
        statusView.showLoading();
        String adUrl = String.format("%s/comment/apiv2/homead", CommonUtil.getBaseUrl());
        NetworkRequest adRequest = new NetworkRequest<Advertise>(getActivity())
                .url(adUrl)
                .method(RequestParam.Method.GET)
                .dataClass(Advertise.class)
                .dataParser(new ListDataParser<Advertise>())
                .dataFormat(RequestParam.DataFormat.LIST)
                .create();

        String newUrl = String.format("%s/comment/apiv2/homenewslist", CommonUtil.getBaseUrl());
        NetworkRequest newRequest = new NetworkRequest<New>(getActivity())
                .url(newUrl)
                .method(RequestParam.Method.GET)
                .dataClass(New.class)
                .dataParser(new ListDataParser<New>())
                .dataFormat(RequestParam.DataFormat.LIST)
                .create();

        RequestStream.Companion.create(getContext()).parallel(adRequest, newRequest).collect(new RequestStream.OnCollectListener() {
            @Override
            public void onFailure(@NotNull String s) {
                statusView.showError();
            }

            @Override
            public void onSuccess(@NotNull List<?> list) {
                statusView.showContent();
                List<Advertise> advertises = (List<Advertise>) list.get(0);
                List<New> news = (List<New>)list.get(1);
                showBanner(advertises);
                //showNews(news);
            }
        });
    }

    private void showBanner(List<Advertise> advertises) {
        this.advertises = advertises;
        List<String> urls = new ArrayList<>();
        for(Advertise advertise : advertises) {
            urls.add(advertise.getImg());
        }

        banner.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 20);
            }
        });
        banner.setClipToOutline(true);

        banner.setImages(urls)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(TiantianFragment.this)
                .start();
        banner.setFocusable(true);

        banner.setFocusableInTouchMode(true);
        banner.requestFocus();
    }

    private void showNews(List<New> news) {
        ((CommonAdapter)recyclerView.getAdapter()).setData(news);
    }

    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(getContext(), AdvertiseDetailActivity.class);
        intent.putExtra(AdvertiseDetailActivity.ADVERTISE_DETAIL, advertises.get(position));
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(banner != null) {
            banner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(banner != null) {
            banner.stopAutoPlay();
        }
    }

    class NewItemViewHolder extends CommonItemViewHolder<New> {
        TextView titleView;
        TextView comeFormView;
        TextView timeView;

        public NewItemViewHolder(View itemView) {
            super(itemView);
            timeView = itemView.findViewById(R.id.list_time_tv);
            titleView = itemView.findViewById(R.id.list_title_tv);
            comeFormView = itemView.findViewById(R.id.list_comefrom_tv);
        }

        @Override
        public void bindto(@NonNull New data) {
            titleView.setText(data.getTitle());
            timeView.setText(data.getTime());
            comeFormView.setText(data.getComefrom());
        }
    }
}
