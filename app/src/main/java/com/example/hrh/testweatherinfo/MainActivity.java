package com.example.hrh.testweatherinfo;


import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import activity.ProvinceCity;
import adapter.CityItemAdaper;
import data.City;
import network.ProvincesHttpRequest;
import util.datamanager.DataManager;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcAbsListView;
import zrc.widget.ZrcListView;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ZrcListView mListView;
    private CityItemAdaper mAdapter;
    private DataManager mDataManager;
    private ApplicationData mAppData;
    private ProvincesHttpRequest provincesHttpRequest;
    private int mListViewItemPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAppData = (ApplicationData)getApplication();
        mAppData.initial(this);
        mDataManager = ((ApplicationData) this.getApplication()).getDataManager();
        initZrclistview();
        provincesHttpRequest = new ProvincesHttpRequest(this);
        provincesHttpRequest.setmProvincesStringHttpRequestListener(mHttpResultListener);
        mListView.refresh();
        mListView.setOnScrollListener(mOnScrollListener);
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

        mDataManager.clearCityItem();
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
                .StringRequest(Define.HUI_LIST_PATH);
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
        public void onSucced(City[] response) {
            Log.e(TAG,"response = " + response);
            mDataManager.setCityItems(response);
            mAdapter.notifyDataSetChanged();
            mListView.setRefreshSuccess("");
        }

        @Override
        public void onError(int reason) {

        }
    };
}
