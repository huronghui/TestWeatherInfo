package activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.Define;
import com.example.hrh.testweatherinfo.R;

import org.w3c.dom.Text;

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

    private TextView cityNameText;
    private TextView temp1;
    private TextView temp2;
    private TextView weatherDespText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weathercity);
        initview();
        mAppData = (ApplicationData)getApplication();
        mDataManager = mAppData.getDataManager();
        remoteId  = getIntent().getLongExtra("remoteId", -1);

        mCityWeatherHttpRequest = new CityWeatherHttpRequest(this);
        mCityWeatherHttpRequest.setmCityWeatherHttpRequestListener(mOnCityWeatherHttpRequestListener);

        mCityWeatherInfoHttpRequest = new CityWeatherInfoHttpRequest(this);
        mCityWeatherInfoHttpRequest.setCityWeatherInfoHttpRequestListener(mCityWeatherInfoHttpRequestListener);
        refresh();
    }

    private void initview() {
        cityNameText = (TextView)findViewById(R.id.city_textview_name);
        temp1 = (TextView)findViewById(R.id.temp1);
        temp2 = (TextView)findViewById(R.id.temp2);
        weatherDespText = (TextView)findViewById(R.id.weather_desp);
    }

    protected void refresh() {
        String id = remoteId+"";
        Log.e(TAG, "id = " + id);
        if((id.length() % 2) != 0 ) {
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
        cityNameText.setText(bean.getCity());
        temp1.setText(bean.getTemp1());
        temp2.setText(bean.getTemp2());
        weatherDespText.setText(bean.getWeather());
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
