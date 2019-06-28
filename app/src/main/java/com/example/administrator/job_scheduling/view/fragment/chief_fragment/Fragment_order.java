package com.example.administrator.job_scheduling.view.fragment.chief_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.job_scheduling.R;
import com.example.administrator.job_scheduling.view.fragment.fragment_order_child.Fragment_order_finish;
import com.example.administrator.job_scheduling.view.fragment.fragment_order_child.Fragment_order_inPreparation;
import com.example.administrator.job_scheduling.view.fragment.fragment_order_child.Fragment_order_running;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Fragment_order extends Fragment {

    private View view;
    private List<String> family;
    private ViewPager view_pager;
    private SlidingTabLayout mSlidingTabLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i ( "fragment_home", "onCreateView: running " );
        view = inflater.inflate ( R.layout.fragment_order, container, false );
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated ( savedInstanceState );

        mSlidingTabLayout = Objects.requireNonNull ( this.getView () ).findViewById(R.id.tab_layout);
        view_pager = this.getView().findViewById(R.id.fragment_home_container);

        initFragment ();
    }

    @Override
    public void onStart() {
        super.onStart ();
        Log.i ( "fragment_home", "onStart: running " );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
    }

    public void initFragment(){
        family = new ArrayList<> (  );
        family.add ( "进行中" );
        family.add ( "已完成" );
        family.add ( "未进行" );

        final ArrayList<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add( Fragment_order_running.newInstance());
        fragmentList.add ( Fragment_order_finish.newInstance () );
        fragmentList.add ( Fragment_order_inPreparation.newInstance () );


        final MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList);
        view_pager.setAdapter(adapter);
        view_pager.setOverScrollMode( ViewPager.OVER_SCROLL_NEVER);
        view_pager.setOffscreenPageLimit(family.size());
        view_pager.setCurrentItem(0);
        mSlidingTabLayout.setViewPager(view_pager);

        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return family.get(position);
        }
    }
}
