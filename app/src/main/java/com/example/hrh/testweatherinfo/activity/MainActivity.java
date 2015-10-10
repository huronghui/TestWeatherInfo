package com.example.hrh.testweatherinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.Define;
import com.example.hrh.testweatherinfo.HandlerUtil.MyHandler;
import com.example.hrh.testweatherinfo.interf.MyTimeTask;
import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.adapter.CityItemAdaper;
import com.example.hrh.testweatherinfo.base.BaseApplication;
import com.example.hrh.testweatherinfo.data.CityData;
import com.example.hrh.testweatherinfo.fragment.NavigationDrawerFragment;
import com.example.hrh.testweatherinfo.network.ProvincesHttpRequest;
import com.example.hrh.testweatherinfo.datamanager.DataManager;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcAbsListView;
import zrc.widget.ZrcListView;

public class MainActivity extends ActionBarActivity  implements
        NavigationDrawerFragment.NavigationDrawerCallbacks{

    private static final String TAG = MainActivity.class.getSimpleName();
    private ZrcListView mListView;
    private CityItemAdaper mAdapter;
    private DataManager mDataManager;
    private ApplicationData mAppData;
    private ProvincesHttpRequest provincesHttpRequest;
    private int mListViewItemPosition = 0;
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
        setContentView(R.layout.activity_main);

        initView();
        mAppData = (ApplicationData)getApplication();
        mAppData.initial(this);
        mDataManager = ((ApplicationData) this.getApplication()).getDataManager();
        initZrclistview();
        provincesHttpRequest = new ProvincesHttpRequest(this);
        provincesHttpRequest.setmProvincesStringHttpRequestListener(mHttpResultListener);
        mListView.refresh();
        mListView.setOnScrollListener(mOnScrollListener);
        myHandler = new MyHandler(MainActivity.this, TIME * 10,  myTimeTask);
        myHandler.startTimeTask();
    }

    private void initView() {
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // 设置抽屉
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(String title) {
        onSectionAttached(title);
    }

    public void onSectionAttached(String title) {
        mTitle = title;
    }

    private void initZrclistview() {
        mListView = (ZrcListView)findViewById(R.id.zListView);
        mListView.setFirstTopOffset((int) this.getResources().getDimension(R.dimen.top_offset));

        SimpleHeader header = new SimpleHeader(this);
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        mListView.setHeadable(header);

        SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(0xff33bbee);
        mListView.setFootable(footer);

        mListView.setItemAnimForTopIn(R.anim.topitem_in);
        mListView.setItemAnimForBottomIn(R.anim.bottomitem_in);

        mListView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        mListView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });

//        mDataManager.clearCityItem();
        mListView.refresh();
        mAdapter = new CityItemAdaper(this, mDataManager);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    private ZrcListView.OnItemClickListener mOnItemClickListener = new ZrcListView.OnItemClickListener() {

        @Override
        public void onItemClick(ZrcListView parent, View view, int position, long id) {

            Intent intent = new Intent(MainActivity.this, ProvinceCity.class);
            intent.putExtra("remoteId", mDataManager.getCityItemIdList().get(position));
            startActivity(intent);
        }
    };

    private void refresh() {
        Log.e(TAG,"request = ProvincesHttpRequest");
        provincesHttpRequest
                .StringRequest(Define.WEATHER_LIST_PATH);
    }

    private void loadMore() {

    }
    private ZrcListView.OnScrollListener mOnScrollListener = new ZrcListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(ZrcAbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(ZrcAbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            Log.e(TAG, "firstVisibleItem = " + firstVisibleItem + " visibleItemCount = " + visibleItemCount);
                mListViewItemPosition = firstVisibleItem;
        }
    };
    private ProvincesHttpRequest.OnProvincesHttpStringRequestListener mHttpResultListener = new ProvincesHttpRequest
            .OnProvincesHttpStringRequestListener() {

        @Override
        public void onSucced(CityData[] response) {
            Log.e(TAG,"response = " + response);
            mDataManager.setCityItems(response);
            mAdapter.notifyDataSetChanged();
            mListView.setRefreshSuccess("");
        }

        @Override
        public void onError(int reason) {
            Log.e(TAG, "error = " + reason);
            mListView.setRefreshSuccess("");
            mListView.stopLoadMore();
        }
    };

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
}
