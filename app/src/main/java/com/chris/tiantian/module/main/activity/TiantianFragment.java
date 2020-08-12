package com.chris.tiantian.module.main.activity;

import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.anima.networkrequest.NetworkRequest;
import com.anima.networkrequest.RequestStream;
import com.anima.networkrequest.entity.RequestParam;
import com.chris.tiantian.R;
import com.chris.tiantian.entity.Advertise;
import com.chris.tiantian.entity.New;
import com.chris.tiantian.entity.RankData;
import com.chris.tiantian.entity.dataparser.ListDataParser;
import com.chris.tiantian.entity.dataparser.ObjectStatusDataParser;
import com.chris.tiantian.module.main.activity.fragment.MonthLeaderboardFragment;
import com.chris.tiantian.module.main.activity.fragment.NewStrategyFragment;
import com.chris.tiantian.module.main.activity.fragment.NewTeachingFragment;
import com.chris.tiantian.module.main.activity.fragment.WeekLeaderboardFragment;
import com.chris.tiantian.module.plaza.adapter.ViewPagerAdapter;
import com.chris.tiantian.util.CommonItemViewHolder;
import com.chris.tiantian.util.CommonUtil;
import com.chris.tiantian.util.GlideImageLoader;
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
    private NewHotView newHotView;

    private TabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

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
            newHotView = rootView.findViewById(R.id.ttFragment_hot_new);

            mSlidingTabLayout = rootView.findViewById(R.id.mainFrm_slidingTabLayout);
            mViewPager = rootView.findViewById(R.id.mainFrm_viewpager);

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

    public void initTabView(RankData rankData) {
        if(fragmentList == null) {
            fragmentList = new ArrayList<>();
            fragmetNameList = new ArrayList<>();
            WeekLeaderboardFragment weekLeaderboardFragment = new WeekLeaderboardFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(WeekLeaderboardFragment.DATA, rankData.getWeeklyRank());
            weekLeaderboardFragment.setArguments(bundle);

            MonthLeaderboardFragment monthLeaderboardFragment = new MonthLeaderboardFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putParcelableArrayList(MonthLeaderboardFragment.DATA, rankData.getMonthlyRank());
            monthLeaderboardFragment.setArguments(bundle2);

            NewStrategyFragment newStrategyFragment = new NewStrategyFragment();
            Bundle bundle3 = new Bundle();
            bundle3.putParcelableArrayList(NewStrategyFragment.DATA, rankData.getNewPolicy());
            newStrategyFragment.setArguments(bundle3);

            NewTeachingFragment newTeachingFragment = new NewTeachingFragment();
            Bundle bundle4 = new Bundle();
            bundle4.putParcelableArrayList(NewTeachingFragment.DATA, rankData.getNewBooks());
            newTeachingFragment.setArguments(bundle4);

            fragmentList.add(weekLeaderboardFragment);
            fragmentList.add(monthLeaderboardFragment);
            fragmentList.add(newStrategyFragment);
            fragmentList.add(newTeachingFragment);
            fragmetNameList.add("周排行");
            fragmetNameList.add("月排行");
            fragmetNameList.add("新策略");
            fragmetNameList.add("新教材");
        }

        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragmentList, fragmetNameList));
        mSlidingTabLayout.setupWithViewPager(mViewPager);

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

        String bankUrl = String.format("%s/comment/apiv2/home/policyRank", CommonUtil.getBaseUrl());
        NetworkRequest bankRequest = new NetworkRequest<RankData>(getContext())
                .url(bankUrl)
                .method(RequestParam.Method.GET)
                .dataClass(RankData.class)
                .dataParser(new ObjectStatusDataParser<RankData>())
                .create();

        RequestStream.Companion.create(getContext()).parallel(adRequest, newRequest, bankRequest).collect(new RequestStream.OnCollectListener() {
            @Override
            public void onFailure(@NotNull String s) {
                statusView.showError();
            }

            @Override
            public void onSuccess(@NotNull List<?> list) {
                statusView.showContent();
                List<Advertise> advertises = (List<Advertise>) list.get(0);
                List<New> news = (List<New>)list.get(1);
                RankData rankData = (RankData) list.get(2);
                showBanner(advertises);
                showNews(news);
                showRankData(rankData);
            }
        });
    }

    private void showRankData(RankData rankData) {
        initTabView(rankData);
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
        newHotView.setDataList(news);
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
}
