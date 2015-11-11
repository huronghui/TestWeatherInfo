package com.example.hrh.testweatherinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.Define;
import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.base.BaseActivity;
import com.example.hrh.testweatherinfo.data.WeatherBean.Cityweatherbean;
import com.example.hrh.testweatherinfo.datamanager.DataManager;
import com.example.hrh.testweatherinfo.network.CityWeatherHttpRequest;
import com.example.hrh.testweatherinfo.network.CityWeatherInfoHttpRequest;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hrh on 2015/8/23.
 */
public class CityWeatherActivity extends BaseActivity {

    private static final String TAG = CityWeatherActivity.class.getSimpleName();
    @Bind(R.id.city_textview_name)
    TextView cityTextviewName;
    @Bind(R.id.publish_text)
    TextView publishText;
    @Bind(R.id.current_date)
    TextView currentDate;
    @Bind(R.id.weather_desp)
    TextView weatherDesp;
    @Bind(R.id.temp1)
    TextView temp1;
    @Bind(R.id.temp2)
    TextView temp2;
    @Bind(R.id.weather_info_layout)
    LinearLayout weatherInfoLayout;
    private ApplicationData mAppData;
    private DataManager mDataManager;
    private CityWeatherHttpRequest mCityWeatherHttpRequest;
    private CityWeatherInfoHttpRequest mCityWeatherInfoHttpRequest;
    private Long remoteId;
    private String weatherId;


    private void initview() {
        ButterKnife.bind(this);
    }

    protected void refresh() {
        String id = remoteId + "";
        Log.e(TAG, "id = " + id);
        if ((id.length() % 2) != 0) {
            id = "0" + id;
        }
        mCityWeatherInfoHttpRequest.StringRequest(Define.PRIVINCE_CITY_PATH + id + ".xml");
    }

    public CityWeatherInfoHttpRequest.CityWeatherInfoHttpRequestListener mCityWeatherInfoHttpRequestListener =
            new CityWeatherInfoHttpRequest.CityWeatherInfoHttpRequestListener() {

                @Override
                public void onSucceed(String reponse) {
                    mCityWeatherHttpRequest.add(Define.CITY_WEATHER_PATH + reponse + ".html", null);
                }

                @Override
                public void onError(int reason) {

                }
            };

    public void LoadData(Cityweatherbean bean) {
        cityTextviewName.setText(bean.getCity());
        temp1.setText(bean.getTemp1());
        temp2.setText(bean.getTemp2());
        weatherDesp.setText(bean.getWeather());
        currentDate.setText(bean.getPtime());
    }

    public CityWeatherHttpRequest.onCityWeatherHttpRequestListtener mOnCityWeatherHttpRequestListener =
            new CityWeatherHttpRequest.onCityWeatherHttpRequestListtener() {
                @Override
                public void onSucceed(Cityweatherbean bean) {
                    Log.e(TAG, bean.toString());
                    LoadData(bean);
                }

                @Override
                public void onError(int reason) {
                    Log.e(TAG, "Error =" + reason);
                }
            };


    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.temp1:
                    Intent intent = new Intent("com.example.testweatherinfo.FORCE_TITLE");
                    sendBroadcast(intent);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weathercity;
    }

    @Override
    protected boolean haseBackButton() {
        return true;
    }

    @Override
    public void initView() {
        initview();
    }

    @Override
    public void initData() {

        mAppData = (ApplicationData) getApplication();
        mDataManager = mAppData.getDataManager();
        remoteId = getIntent().getLongExtra("remoteId", -1);

        mCityWeatherHttpRequest = new CityWeatherHttpRequest(this);
        mCityWeatherHttpRequest.setmCityWeatherHttpRequestListener(mOnCityWeatherHttpRequestListener);

        mCityWeatherInfoHttpRequest = new CityWeatherInfoHttpRequest(this);
        mCityWeatherInfoHttpRequest.setCityWeatherInfoHttpRequestListener(mCityWeatherInfoHttpRequestListener);
        refresh();
    }

}
