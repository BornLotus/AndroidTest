package com.example.v_xuyan.androidtest.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.example.v_xuyan.androidtest.R;
import com.example.v_xuyan.androidtest.utils.ViewUtils;
import com.example.v_xuyan.androidtest.view.DynamicHeightViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v-xuyan on 2017/12/14.
 */

public class ContentFragment extends Fragment {

    private View mRootView;
    private TabLayout mTabLayout;
    private DynamicHeightViewPager mViewPager;
    private List<MyContentFragment> mFragments;
    private List<String> mTitles;
    private TabAdapter mAdapter;
    private View mHeader;
    private TabLayout mTabHeader;
    private ScrollView mScrollView;
    private boolean isHeaderShow;
    private int mHeaderTop;
    int toTopDistance = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.content_item, container, false);
        mTabLayout = mRootView.findViewById(R.id.tab_indicator);
        mViewPager = mRootView.findViewById(R.id.content_view_pager);
        mHeader = mRootView.findViewById(R.id.head_view);

        initData();

        mAdapter = new TabAdapter(getChildFragmentManager());

        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

        //to measure height realize dynamic height
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position > mAdapter.getCount() || mViewPager == null || mHeader == null)
                    return;
                int num;
                if (position == 0) {
                    num = 50;
                } else {
                    num = 20;
                }
                MyContentFragment fragment = (MyContentFragment) mAdapter.getItem(position);
                mViewPager.measuredCurrentView(fragment.getContainer());
                ViewGroup.LayoutParams params = mHeader.getLayoutParams();
                params.height = (int) ViewUtils.dp2px(getActivity(), num);
                mHeader.setLayoutParams(params);

                // to scroll top let the ContentFragment's View
                int distance = mHeaderTop - mScrollView.getScrollY();
                mScrollView.scrollBy(0,distance);

                //if ContentFragment's View is in the top then
                //mScrollView.fullScroll(View.FOCUS_UP);

            }
        });

        //coordinate the onPageSelected,the first show time is called
        mViewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                MyContentFragment fragment = (MyContentFragment) mAdapter.getItem(0);
                if (mViewPager != null && fragment != null && mHeader != null) {
                    mViewPager.measuredCurrentView(fragment.getContainer());
                    ViewGroup.LayoutParams params = mHeader.getLayoutParams();
                    params.height = (int) ViewUtils.dp2px(getActivity(), 50);
                    mHeader.setLayoutParams(params);
                }
                if (mViewPager != null && mViewPager.getViewTreeObserver() != null) {
                    mViewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

        setupTabHeader();

        return mRootView;
    }

    private void setupTabHeader() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            mTabHeader = getActivity().findViewById(R.id.main_tab_layout);

            if (mTabHeader != null && mViewPager != null) {
                mTabHeader.setupWithViewPager(mViewPager);
            }

            mScrollView = getActivity().findViewById(R.id.main_scroller);
            if (mScrollView != null) {
                mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        updateHeaderView();
                    }
                });
            }
        }
    }

    // onScrollChange is before onCreateView
    private void updateHeaderView() {
        getTopDistance();
        int start = toTopDistance + mTabLayout.getTop();
        int end = toTopDistance + mViewPager.getBottom() - mTabHeader.getMeasuredHeight();//to dismiss | subtract mTabHeader's Height
        if (mScrollView.getScrollY() > start && mScrollView.getScrollY() < end) {
            mTabHeader.setVisibility(View.VISIBLE);
            isHeaderShow = true;
        }
        if (mScrollView.getScrollY() > end || mScrollView.getScrollY() < start) {
            mTabHeader.setVisibility(View.GONE);
            isHeaderShow = false;
        }
    }

    private void getTopDistance() {
        //to get mRootView top
        if (getActivity() != null && !getActivity().isFinishing())
            toTopDistance = getActivity().findViewById(R.id.main_content).getTop();
    }

    private void initData() {
        mTitles = new ArrayList<>();
        mTitles.add("解锋镝");
        mTitles.add("有生之莲");

        mFragments = new ArrayList<>();
        MyContentFragment fragment1 = new MyContentFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("num", 1);
        fragment1.setArguments(bundle1);
        mFragments.add(fragment1);
        MyContentFragment fragment2 = new MyContentFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("num", 2);
        fragment2.setArguments(bundle2);
        mFragments.add(fragment2);
    }


    class TabAdapter extends FragmentPagerAdapter {

        TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

}
