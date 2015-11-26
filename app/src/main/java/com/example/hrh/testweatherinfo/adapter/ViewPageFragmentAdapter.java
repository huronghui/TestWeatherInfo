package com.example.hrh.testweatherinfo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.widget.PagerSlidingTabStrip;

import java.util.ArrayList;

/**
 * Created by hrh on 2015/11/21.
 */
public class ViewPageFragmentAdapter extends FragmentStatePagerAdapter{

    private final Context context;
    protected PagerSlidingTabStrip mPagerStrip;
    private final ViewPager mViewPager;
    private final ArrayList<ViewPagerInfo> mTabs = new ArrayList<>();

   public ViewPageFragmentAdapter(FragmentManager fm, PagerSlidingTabStrip pageStrip, ViewPager pager) {
       super(fm);
       context = pageStrip.getContext();
       mPagerStrip = pageStrip;
       mViewPager = pager;
       mViewPager.setAdapter(this);
       mPagerStrip.setViewPager(mViewPager);
    }

    public void addTab(String Title, String tag, Class<?> clss, Bundle args) {
        ViewPagerInfo viewPagerInfo = new ViewPagerInfo(Title, tag, clss, args);
        addFragment(viewPagerInfo);
    }

    private void addFragment(ViewPagerInfo viewPagerInfo) {
        if(viewPagerInfo == null) {
            return;
        }

        //����tab title
        View v = LayoutInflater.from(context).inflate(
                R.layout.base_viewpage_fragment_tab_title, null, false);

        TextView title = (TextView)v.findViewById(R.id.tab_title);
        title.setText(viewPagerInfo.title);
        mPagerStrip.addTab(v);

        mTabs.add(viewPagerInfo);
        notifyDataSetChanged();
    }

    /**
     * �Ƴ���һ��
     */
    public void remove() {
        remove(0);
    }

    /**
     * �Ƴ�һ��tab
     *
     * @param index
     *            ��ע�����indexС��0����ӵ�һ����ʼɾ �������tab������ֵ������һ����ʼɾ��
     */
    public void remove(int index) {
        if (mTabs.isEmpty()) {
            return;
        }
        if (index < 0) {
            index = 0;
        }
        if (index >= mTabs.size()) {
            index = mTabs.size() - 1;
        }
        mTabs.remove(index);
        mPagerStrip.removeTab(index, 1);
        notifyDataSetChanged();
    }

    /**
     * �Ƴ����е�tab
     */
    public void removeAll() {
        if (mTabs.isEmpty()) {
            return;
        }
        mPagerStrip.removeAllTab();
        mTabs.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        ViewPagerInfo info = mTabs.get(position);
        return Fragment.instantiate(context, info.aClass.getName(), info.args);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).title;
    }
}
