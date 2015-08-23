package network;

import android.app.Activity;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.hrh.testweatherinfo.Define;

import org.json.JSONObject;

import data.WeatherBean.BaseWeatherBean;
import data.WeatherBean.Cityweatherbean;

/**
 * Created by hrh on 2015/8/23.
 */
public class CityWeatherHttpRequest extends HttpRequest {

    private static final String TAG = CityWeatherHttpRequest.class.getSimpleName();
    public onCityWeatherHttpRequestListtener mCityWeatherHttpRequestListener;

    public interface onCityWeatherHttpRequestListtener {
        void onSucceed(Cityweatherbean bean);

        void onError(int reason);
    }

    public void setmCityWeatherHttpRequestListener(onCityWeatherHttpRequestListtener CityWeatherHttpRequestListener) {
        this.mCityWeatherHttpRequestListener = CityWeatherHttpRequestListener;
    }

    public CityWeatherHttpRequest(Activity activity) {
        super(activity);
        mHttpResultListener = new OnHttpRequestListener() {
            @Override
            public void onSucceed(String url, JSONObject response) {
                if(null != response) {
                    Log.e(TAG, "response = " + response.toString());
                    BaseWeatherBean itembean = mJsonObjectParser.parse(response,BaseWeatherBean.class);
                    Cityweatherbean databean = itembean.getWeatherinfo();
                    if(null != databean) {
                        Log.e(TAG,"on succeed product datail.");
                        mCityWeatherHttpRequestListener.onSucceed(databean);
                    } else {
                        Log.e(TAG, "onError products detail.");
                        mCityWeatherHttpRequestListener.onError(Define.NetErrorReason.NOT_KNOWN.getReason());
                    }
                } else {
                    mCityWeatherHttpRequestListener.onError(Define.NetErrorReason.WRONG_ARGU.getReason());
                }
            }

            @Override
            public void onError(VolleyError error) {
                if (null != mHttpResultListener) {
                    mCityWeatherHttpRequestListener.onError(Define.NetErrorReason.NOT_KNOWN.getReason());
                }
            }
        };

    }
}
