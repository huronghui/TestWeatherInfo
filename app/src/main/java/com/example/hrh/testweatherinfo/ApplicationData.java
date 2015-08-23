package com.example.hrh.testweatherinfo;

import android.app.Activity;
import android.app.Application;

import com.android.volley.RequestQueue;

import network.json.PackageParser;
import util.datamanager.DataManager;

/**
 * Created by hrh on 2015/8/21.
 */
public class ApplicationData extends Application{
    private static final String TAG = ApplicationData.class.getSimpleName();
    private DataManager mDataManager;
    private RequestQueue mHttpRequestQueue;
    private PackageParser mPackageParser;
    private Activity mActivity;


    public DataManager getDataManager() {
        return mDataManager;
    }
    public void initial(Activity activity) {
        mActivity = (MainActivity) activity;
        mDataManager = new DataManager(mActivity);
    }

    public RequestQueue getHttpRequestQueue() {
        return mHttpRequestQueue;
    }

    public void setHttpRequestQueue(RequestQueue httpRequestQueue) {
        this.mHttpRequestQueue = httpRequestQueue;
    }

    public void setPackageParser(PackageParser packageParser) {
        this.mPackageParser = packageParser;
    }

    public PackageParser getPackageParser() {
        return mPackageParser;
    }
}
