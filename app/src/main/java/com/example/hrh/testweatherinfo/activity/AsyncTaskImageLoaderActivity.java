package com.example.hrh.testweatherinfo.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.adapter.NewsAdapter;
import com.example.hrh.testweatherinfo.data.WeatherBean.Newsbean;

import org.json.JSONArray;
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


public class AsyncTaskImageLoaderActivity extends Activity {

    private ListView mlistview;
    private static String url = "http://www.imooc.com/api/teacher?type=4&num=30";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctaskimageloader);
        mlistview = (ListView)findViewById(R.id.lv_main);
        new NewsAsyncTask().execute(url);
    }

    class NewsAsyncTask extends AsyncTask<String, Void, List<Newsbean>> {

        @Override
        protected List<Newsbean> doInBackground(String... params) {
            return getJson(params[0]);
        }

        @Override
        protected void onPostExecute(List<Newsbean> newsbeans) {
            super.onPostExecute(newsbeans);
            NewsAdapter adapter = new NewsAdapter(AsyncTaskImageLoaderActivity.this, newsbeans, mlistview);
            mlistview.setAdapter(adapter);
        }
    }

    private List<Newsbean> getJson(String param) {
        List<Newsbean> newsbeanList = new ArrayList<>();
        try {
            String jsonString = readStream(new URL(url).openStream());
            JSONObject jsonObject;
            Newsbean newsbean;
            try {
                jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    newsbean = new Newsbean();
                    newsbean.setNewsIconUrl(jsonObject.getString("picSmall"));
                    newsbean.setNewsTitle(jsonObject.getString("name"));
                    newsbean.setNewsContext(jsonObject.getString("description"));
                    newsbeanList.add(newsbean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsbeanList;
    }

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

