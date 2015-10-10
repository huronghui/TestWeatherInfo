package com.example.hrh.testweatherinfo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.Define;
import com.example.hrh.testweatherinfo.R;

import com.example.hrh.testweatherinfo.adapter.DistrictCityAdapter;

import com.example.hrh.testweatherinfo.base.BaseActivity;
import com.example.hrh.testweatherinfo.base.BaseListViewActivity;
import com.example.hrh.testweatherinfo.data.DistrictCity;
import com.example.hrh.testweatherinfo.network.DistrictCityHttpRequest;
import com.example.hrh.testweatherinfo.datamanager.DataManager;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;

/**
 * Created by hrh on 2015/8/22.
 */
public class DistrictCityActivity extends BaseListViewActivity{

    protected static final String TAG = DistrictCityActivity.class.getSimpleName();
    private DistrictCityHttpRequest mDistrictCityHttpRequest;

    @Override
    protected void ItemClick(int position) {
        Intent intent = new Intent(DistrictCityActivity.this, CityWeatherActivity.class);
        intent.putExtra("remoteId", mDataManager.getDistrictCityItemList().get(position));
        startActivity(intent);
    }

    protected void refresh() {
        String id = remoteId+"";
        Log.e(TAG, "id = " + id + "ddd");
        if((id.length() % 2) != 0 ) {
            id = "0"+id;
        }
        Log.e(TAG, "id = " + id);
        mDistrictCityHttpRequest.StringRequest(Define.PRIVINCE_CITY_PATH + id + ".xml");
    }

    protected void loadMore() {

    }

    public DistrictCityHttpRequest.OnDistrictCityStringHttpListener mDistrictCityStringHttpRequest = new DistrictCityHttpRequest.OnDistrictCityStringHttpListener() {
        @Override
        public void onSucced(DistrictCity[] response) {
            Log.e(TAG, "response = " + response);
            mDataManager.setDistrictCityItems(response);
            mAdapter.notifyDataSetChanged();
            mListView.setRefreshSuccess("");
        }

        @Override
        public void onError(int reason) {

        }
    };

    @Override
    protected void InitHttpRequest() {
        mDistrictCityHttpRequest = new DistrictCityHttpRequest(this);
        mDistrictCityHttpRequest.setDistrictCityStringHttpRequestListener(mDistrictCityStringHttpRequest);
    }

    @Override
    protected BaseAdapter getListAdapter() {
        return new DistrictCityAdapter(this, mDataManager);
    }
}
