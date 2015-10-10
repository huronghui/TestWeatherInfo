package com.example.hrh.testweatherinfo.base;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.activity.DistrictCityActivity;
import com.example.hrh.testweatherinfo.adapter.PrivinceCityItemAdaper;
import com.example.hrh.testweatherinfo.data.PrivinceCityData;
import com.example.hrh.testweatherinfo.datamanager.DataManager;
import com.example.hrh.testweatherinfo.network.HttpRequest;
import com.example.hrh.testweatherinfo.network.PrivinceCityHttpRequest;

import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcAbsListView;
import zrc.widget.ZrcListView;

/**
 * Created by hrh on 2015/10/9.
 */
public abstract class BaseListViewActivity extends BaseActivity{
    public static final String TAG = BaseListViewActivity.class.getSimpleName();
    private ApplicationData mAppData;
    public ZrcListView mListView;
    public DataManager mDataManager;
    public BaseAdapter mAdapter;
    public Long remoteId;
    private HttpRequest httprequest;
    private int mListViewItemPosition = 0;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_province;
    }

    @Override
    public void initView() {
        mAppData = (ApplicationData)getApplication();
        remoteId  = getIntent().getLongExtra("remoteId", -1);
        mDataManager = mAppData.getDataManager();
        initZrcvlistview();

    }

    @Override
    public void initData() {
        mListView.refresh();
        mListView.setOnScrollListener(mOnScrollListener);
        InitHttpRequest();
    }

    @Override
    protected boolean haseBackButton() {
        return true;
    }

    protected abstract void InitHttpRequest();

    private void initZrcvlistview() {
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

//        mDataManager.clearPrivinceItem();
        mListView.refresh();
        mAdapter = getListAdapter();
       // mAdapter =
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    protected abstract BaseAdapter getListAdapter();

    public ZrcListView.OnItemClickListener mOnItemClickListener  = new ZrcListView.OnItemClickListener() {
        @Override
        public void onItemClick(ZrcListView parent, View view, int position, long id) {
                ItemClick(position);
        }
    };

    protected void ItemClick(int position) {

    }
    protected void refresh() {

    }
    protected  void loadMore() {

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
}
