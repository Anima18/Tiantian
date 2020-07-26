package com.chris.tiantian.module.plaza.activity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.chris.tiantian.module.main.activity.WeekLeaderboardFragment;
import com.chris.tiantian.module.plaza.adapter.ViewPagerAdapter;

import com.chris.tiantian.R;
import com.chris.tiantian.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jianjianh on 2019/4/10.
 */
public class PlazaFragment extends Fragment {
    private View rootView;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

    private List<Fragment> fragmentList;
    private List<String> fragmetNameList;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_plaza, container, false);
            mSlidingTabLayout = rootView.findViewById(R.id.messageFrm_slidingTabLayout);
            mViewPager = rootView.findViewById(R.id.messageFrm_viewpager);
        }
        initTabView();
        return rootView;
    }

    public void initTabView() {
        if(fragmentList == null) {
            fragmentList = new ArrayList<>();
            fragmetNameList = new ArrayList<>();
            fragmentList.add(new PolicyFragment());
            fragmentList.add(new WeekLeaderboardFragment());
            fragmentList.add(new AuctionFragment());
            fragmetNameList.add("广场");
            fragmetNameList.add("排行榜");
            fragmetNameList.add("竞拍");
        }

        mViewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragmentList, fragmetNameList));
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimary);
            }
        });

        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) { }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        //mSlidingTabLayout.setDividerColors(R.color.primary);
    }
}
