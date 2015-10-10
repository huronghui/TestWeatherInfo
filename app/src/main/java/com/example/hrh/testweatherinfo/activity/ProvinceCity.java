package com.example.hrh.testweatherinfo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.Define;
import com.example.hrh.testweatherinfo.R;

import java.util.Timer;
import java.util.TimerTask;

import com.example.hrh.testweatherinfo.adapter.PrivinceCityItemAdaper;
import com.example.hrh.testweatherinfo.base.BaseActivity;
import com.example.hrh.testweatherinfo.base.BaseListViewActivity;
import com.example.hrh.testweatherinfo.data.PrivinceCityData;
import com.example.hrh.testweatherinfo.network.PrivinceCityHttpRequest;
import com.example.hrh.testweatherinfo.datamanager.DataManager;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcAbsListView;
import zrc.widget.ZrcListView;

/**
 * Created by hrh on 2015/8/22.
 */
public class ProvinceCity extends BaseListViewActivity {

    public static final String TAG = PrivinceCityData.class.getSimpleName();
    private PrivinceCityHttpRequest mPrivinceCityHttpRequest;
    private int TIME = 10000;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                Toast.makeText(ProvinceCity.this, "Hello World again", Toast.LENGTH_LONG).show();
            }
            super.handleMessage(msg);
        };
    };

    Timer timer = new Timer();

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    protected void ItemClick(int position) {
        Intent intent = new Intent(ProvinceCity.this, DistrictCityActivity.class);
        intent.putExtra("remoteId", mDataManager.getPrivinceCityItemIdList().get(position));
        startActivity(intent);
    }

    @Override
    protected void refresh() {
        String id = remoteId+"";
        if(remoteId < 10) {
            id = "0"+id;
        }
       mPrivinceCityHttpRequest.StringRequest(Define.PRIVINCE_CITY_PATH + id + ".xml");
    }

    @Override
    protected void loadMore() {

    }

    public PrivinceCityHttpRequest.onPrivinceCityHttpRequestListener mPrivinceCityHttpRequestListener = new PrivinceCityHttpRequest.onPrivinceCityHttpRequestListener() {
        @Override
        public void onSucced(PrivinceCityData[] response) {
            Log.e(TAG,"response = " + response);
            mDataManager.setPrivinceCityItems(response);
            mAdapter.notifyDataSetChanged();
            mListView.setRefreshSuccess("");
        }

        @Override
        public void onError(int reason) {
            mAdapter.notifyDataSetChanged();
            mListView.setRefreshSuccess("");
//            mListView.stopLoadMore();
        }
    };

    @Override
    protected void InitHttpRequest() {
        mPrivinceCityHttpRequest = new PrivinceCityHttpRequest(this);
        mPrivinceCityHttpRequest.setmProvincesStringHttpRequestListener(mPrivinceCityHttpRequestListener);
        timer.schedule(task, TIME, TIME);
    }



    @Override
    protected BaseAdapter getListAdapter() {
        return new PrivinceCityItemAdaper(this, mDataManager);
    }
}

