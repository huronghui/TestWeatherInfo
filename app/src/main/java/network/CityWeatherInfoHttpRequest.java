package network;

import android.app.Activity;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.hrh.testweatherinfo.Define;

import java.util.ArrayList;
import java.util.List;

import activity.CityWeatherActivity;
import data.PrivinceCity;

/**
 * Created by hrh on 2015/8/23.
 */
public class CityWeatherInfoHttpRequest extends HttpRequest{

    private static final String TAG = CityWeatherInfoHttpRequest.class.getSimpleName();
    private CityWeatherInfoHttpRequestListener mCityWeatherInfoHttpRequestListener;

    public interface CityWeatherInfoHttpRequestListener {
        void onSucceed(String reponse);

        void onError(int reason);
    }

    public void setCityWeatherInfoHttpRequestListener(CityWeatherInfoHttpRequestListener cityWeatherInfoHttpRequestListener) {
        mCityWeatherInfoHttpRequestListener = cityWeatherInfoHttpRequestListener;
    }

    public CityWeatherInfoHttpRequest(Activity activity) {
        super(activity);
        mStringHttpResultListener = new OnStringHttpRequestListener() {
            @Override
            public void onSucceed(String response) {
                if(null != response) {
                    Log.e(TAG, "response = " + response);
                    String privince[] = response.split("\\|");
                    mCityWeatherInfoHttpRequestListener.onSucceed(privince[1]);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG,error.getMessage());
                if (null != mHttpResultListener) {
                    mCityWeatherInfoHttpRequestListener.onError(Define.NetErrorReason.NOT_KNOWN.getReason());
                }
            }
        };
    }
}
