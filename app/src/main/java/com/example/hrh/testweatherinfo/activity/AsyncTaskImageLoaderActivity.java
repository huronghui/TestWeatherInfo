package com.example.hrh.testweatherinfo.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.adapter.NewsAdapter;
import com.example.hrh.testweatherinfo.base.BaseActivity;
import com.example.hrh.testweatherinfo.data.WeatherBean.Newsbean;
import com.example.hrh.testweatherinfo.data.imoocInfo.ImoocInfoDataBean;
import com.example.hrh.testweatherinfo.data.imoocInfo.ImoocInfoListDataBean;
import com.example.hrh.testweatherinfo.network.json.PackageParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrh on 2015/9/30.
 */


public class AsyncTaskImageLoaderActivity extends BaseActivity {

    private ListView mlistview;
    private static String url = "http://www.imooc.com/api/teacher?type=4&num=30";
    PackageParser mJsonObjectParser;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_asynctaskimageloader;
    }

    @Override
    public void initView() {
        ApplicationData appData = (ApplicationData)this.getApplication();
        mJsonObjectParser = appData.getPackageParser();
        if(mJsonObjectParser == null) {
            mJsonObjectParser = new PackageParser();
            appData.setPackageParser(mJsonObjectParser);
        }
    }

    @Override
    public void initData() {
        mlistview = (ListView)findViewById(R.id.lv_main);
        new NewsAsyncTask().execute(url);
    }

    class NewsAsyncTask extends AsyncTask<String, Void, List<Newsbean>> {

        @Override
        protected List<Newsbean> doInBackground(String... params) {
                return getJsonForGson(params[0]);
        }

        @Override
        protected void onPostExecute(List<Newsbean> newsbeans) {
            super.onPostExecute(newsbeans);
            NewsAdapter adapter = new NewsAdapter(AsyncTaskImageLoaderActivity.this, newsbeans, mlistview);
            mlistview.setAdapter(adapter);
        }
    }

    private List<Newsbean> getJsonForGson(String param) {

        List<Newsbean> newsbeanList = new ArrayList<>();
        Newsbean newsbean;
        JSONObject paramJsonObject = null;
        try {
            String jsonString = readStream(new URL(url).openStream());
             try {
                 paramJsonObject = new JSONObject(jsonString);
                 Log.e("TEST GSON BEAN", jsonString);
                 Log.e("TEST GSON BEAN", paramJsonObject.toString());
                 ImoocInfoDataBean imoocInfoDataBean = mJsonObjectParser.parse(paramJsonObject, ImoocInfoDataBean.class);
                 if(imoocInfoDataBean != null) {
                    ImoocInfoListDataBean[] imoocInfoListDataBeans = imoocInfoDataBean.getImoocInfoListDataBean();
                    if (imoocInfoListDataBeans.length > 0) {
                        for (ImoocInfoListDataBean mImoocInfoDataBean : imoocInfoListDataBeans) {
                            newsbean = new Newsbean();
                            Log.e("TEST GSON BEAN", mImoocInfoDataBean.getDescription());
                            newsbean.setNewsIconUrl(mImoocInfoDataBean.getPicSmall());
                            newsbean.setNewsTitle(mImoocInfoDataBean.getName());
                            newsbean.setNewsContext(mImoocInfoDataBean.getDescription());
                            newsbeanList.add(newsbean);
                        }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsbeanList;
    }

    //未使用GSON解析之前的解析方式
//    private List<Newsbean> getJson(String param) {
//        List<Newsbean> newsbeanList = new ArrayList<>();
//        try {
//            String jsonString = readStream(new URL(url).openStream());
//            JSONObject jsonObject;
//            Newsbean newsbean;
//            try {
//                jsonObject = new JSONObject(jsonString);
//                JSONArray jsonArray = jsonObject.getJSONArray("data");
//                for(int i = 0; i < jsonArray.length(); i++) {
//                    jsonObject = jsonArray.getJSONObject(i);
//                    newsbean = new Newsbean();
//                    newsbean.setNewsIconUrl(jsonObject.getString("picSmall"));
//                    newsbean.setNewsTitle(jsonObject.getString("name"));
//                    newsbean.setNewsContext(jsonObject.getString("description"));
//                    newsbeanList.add(newsbean);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return newsbeanList;
//    }

    private String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";
        try {
            String line;
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            try {
                while((line = br.readLine()) != null) {
                    result += line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}

