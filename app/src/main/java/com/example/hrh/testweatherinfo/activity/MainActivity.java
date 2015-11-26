package com.example.hrh.testweatherinfo.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.HandlerUtil.MyHandler;
import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.base.BaseApplication;
import com.example.hrh.testweatherinfo.fragment.NavigationDrawerFragment;
import com.example.hrh.testweatherinfo.interf.MyTimeTask;
import com.example.hrh.testweatherinfo.interf.OnTabReselectListener;
import com.example.hrh.testweatherinfo.ui.MainTab;
import com.example.hrh.testweatherinfo.widget.MyFragmentTabHost;

import butterknife.Bind;
import butterknife.ButterKnife;


@SuppressLint("InflateParams")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends ActionBarActivity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks, TabHost.OnTabChangeListener,
        View.OnTouchListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Bind(android.R.id.tabhost)
    MyFragmentTabHost mTabHost;

    @Bind(R.id.quick_option_iv)
    ImageView quickOptionIv;

    private ApplicationData mAppData;
    private int TIME = 1000;
    private MyHandler myHandler;
    private CharSequence mTitle;
    private BaseApplication baseApplication;
    /**
     * 左侧划出抽屉内部fragment
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppBaseTheme_Light);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "TEST NAVIGATIONFRAGMENT111111111111");
        ButterKnife.bind(this);
        initView();
        mAppData = (ApplicationData) getApplication();
        mAppData.initial(this);
        myHandler = new MyHandler(MainActivity.this, TIME * 10, myTimeTask);
        myHandler.startTimeTask();
    }

    private void initView() {
        Log.e(TAG, "TEST NAVIGATIONFRAGMENT");
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // 设置抽屉
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setShowDividers(0);
        initTabs();

        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);

    }

    private void initTabs() {
        MainTab[] tabs = MainTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            Log.e(TAG, mainTab.getResName() + "");
            TabHost.TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());
            title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            if (i == 2) {
                indicator.setVisibility(View.INVISIBLE);
                mTabHost.setmNoTabChangedTag(getString(mainTab.getResName()));
            }
            title.setText(getString(mainTab.getResName()));
            tab.setIndicator(indicator);
            tab.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });
            mTabHost.addTab(tab, mainTab.getClz(), null);

            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }

    }

    public void onSectionAttached(String title) {
        mTitle = title;
    }

    @Override
    protected void onPause() {
        super.onPause();
        myHandler.stopTimeTask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);

        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    public MyTimeTask myTimeTask = new MyTimeTask() {
        @Override
        public void TimeTask() {
            //  Toast.makeText(MainActivity.this,"hello world",Toast.LENGTH_LONG).show();
            baseApplication.showToast("hello world!");
        }
    };

    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    @Override
    public void onTabChanged(String tabId) {
        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
            } else {
                v.setSelected(false);
            }
        }

        if (tabId.equals(getString(MainTab.ME.getResName()))) {

        }
        supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);
        boolean consumed = false;
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && v.equals(mTabHost.getCurrentTabView())) {
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment != null
                    && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
                consumed = true;
            }
        }
        return consumed;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }

}
