package com.example.hrh.testweatherinfo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.hrh.testweatherinfo.adapter.ViewPageFragmentAdapter;
import com.example.hrh.testweatherinfo.base.BaseViewPagerFragment;
import com.example.hrh.testweatherinfo.interf.OnTabReselectListener;

/**
 * Created by hrh on 2015/11/21.
 */
public class TestViewPagerFragment extends BaseViewPagerFragment implements OnTabReselectListener{
    @Override
    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
        adapter.addTab("test1", "news-1", TestListFragment.class, getBundle(12));
        adapter.addTab("test2", "news-2", TestListFragment.class, getBundle(12));
        adapter.addTab("test3", "news-3", TestListFragment.class, getBundle(12));
        adapter.addTab("test4", "news-4", TestListFragment.class, getBundle(12));
    }

    private Bundle getBundle(int newType) {
        Bundle bundle = new Bundle();
        bundle.putInt("", newType);
        return bundle;
    }

    @Override
    protected void setScreenPageLimit() {
        mViewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onTabReselect() {
        try {
            int currentIndex = mViewPager.getCurrentItem();
            Fragment currentFragment = getChildFragmentManager().getFragments().get(currentIndex);
            if(currentFragment != null
                    && currentFragment instanceof  OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
            }
        } catch (NullPointerException e) {

        }
    }

}
