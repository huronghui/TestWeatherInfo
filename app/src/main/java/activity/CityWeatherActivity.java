package activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.Define;
import com.example.hrh.testweatherinfo.R;

import data.WeatherBean.Cityweatherbean;
import network.CityWeatherHttpRequest;
import network.CityWeatherInfoHttpRequest;
import util.datamanager.DataManager;

/**
 * Created by hrh on 2015/8/23.
 */
public class CityWeatherActivity extends Activity{

    private static final String TAG = CityWeatherActivity.class.getSimpleName();
    private ApplicationData mAppData;
    private DataManager mDataManager;
    private CityWeatherHttpRequest mCityWeatherHttpRequest;
    private CityWeatherInfoHttpRequest mCityWeatherInfoHttpRequest;
    private Long remoteId;
    private String weatherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAppData = (ApplicationData)getApplication();
        mDataManager = mAppData.getDataManager();
        remoteId  = getIntent().getLongExtra("remoteId", -1);

        mCityWeatherHttpRequest = new CityWeatherHttpRequest(this);
        mCityWeatherHttpRequest.setmCityWeatherHttpRequestListener(mOnCityWeatherHttpRequestListener);

        mCityWeatherInfoHttpRequest = new CityWeatherInfoHttpRequest(this);
        mCityWeatherInfoHttpRequest.setCityWeatherInfoHttpRequestListener(mCityWeatherInfoHttpRequestListener);
        refresh();
    }

    protected void refresh() {
        String id = remoteId+"";
        Log.e(TAG, "id = " + id);
        if(id.length() / 2 != 0 ) {
            id = "0"+id;
        }
        mCityWeatherInfoHttpRequest.StringRequest(Define.PRIVINCE_CITY_PATH + id + ".xml");
    }

    public CityWeatherInfoHttpRequest.CityWeatherInfoHttpRequestListener mCityWeatherInfoHttpRequestListener =
            new CityWeatherInfoHttpRequest.CityWeatherInfoHttpRequestListener() {

                @Override
                public void onSucceed(String reponse) {
                    mCityWeatherHttpRequest.add(Define.CITY_WEATHER_PATH + reponse + ".html" ,null);
                }

                @Override
                public void onError(int reason) {

                }
            };

    public void LoadData(Cityweatherbean bean) {

    }
    public CityWeatherHttpRequest.onCityWeatherHttpRequestListtener mOnCityWeatherHttpRequestListener =
            new CityWeatherHttpRequest.onCityWeatherHttpRequestListtener() {
                @Override
                public void onSucceed(Cityweatherbean bean) {
                    Log.e(TAG,bean.toString());
                    LoadData(bean);
                }

                @Override
                public void onError(int reason) {
                    Log.e(TAG,"Error =" + reason);
                }
            };


}
