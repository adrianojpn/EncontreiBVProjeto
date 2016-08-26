package com.app.encontreibvrr.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.encontreibvrr.R;
import com.app.encontreibvrr.adapter.TabsAdapterFragment;
import com.app.encontreibvrr.util.SlidingTabLayout;

/**
 * Created by adria on 22/07/2016.
 */

public class Fragment_01Inicial extends Fragment {

    public Fragment_01Inicial() {

    }

    public static Fragment_01Inicial newInstance() {
        Fragment_01Inicial fragment = new Fragment_01Inicial();
        return fragment;
    }

    private String mTitle;

    public static Fragment_01Inicial getInstance(String title) {
        Fragment_01Inicial sf = new Fragment_01Inicial();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_01, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.viewpagerFragment);
        //mViewPager.setAdapter(new TabsAdapterManager(getContext(), getChildFragmentManager()));
        mViewPager.setAdapter(new TabsAdapterFragment(getContext().getApplicationContext(), getChildFragmentManager()));
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabsFragment);
        mSlidingTabLayout.setCustomTabView(R.layout.tab_layout, R.id.tabText);
        mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        //mSlidingTabLayout.setBackgroundColor(getResources().getColor(R.color.md_green_600));
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int i) {

                return getResources().getColor(R.color.md_amber_900);
            }
        });
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);

        return view;
    }

}
