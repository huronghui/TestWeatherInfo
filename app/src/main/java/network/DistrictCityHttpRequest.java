package network;

import android.app.Activity;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.hrh.testweatherinfo.Define;

import java.util.ArrayList;
import java.util.List;

import data.DistrictCity;

/**
 * Created by hrh on 2015/8/22.
 */
public class DistrictCityHttpRequest extends HttpRequest {

    public static final String TAG = DistrictCityHttpRequest.class.getSimpleName();
    public OnDistrictCityStringHttpListener mDistrictCityHttpListener;

    public interface OnDistrictCityStringHttpListener {
        void onSucced(DistrictCity[] products);

        void onError(int reason);
    }

    public void setDistrictCityStringHttpRequestListener(OnDistrictCityStringHttpListener listener) {
        mDistrictCityHttpListener = listener;
    }

    public DistrictCityHttpRequest(Activity activity) {
        super(activity);
        mStringHttpResultListener = new OnStringHttpRequestListener() {

            @Override
            public void onSucceed(String response) {
                if (null != response) {
                    Log.e(TAG, "response = " + response);
                    String privince[] = response.split(",");
                    List list = new ArrayList<DistrictCity>();
                    for (int i = 0; i < privince.length; i++) {
                        String privinceitem[] = privince[i].split("\\|");
                        DistrictCity city = new DistrictCity();
                        Long itemid = (long) Integer.valueOf(privinceitem[0]).intValue();
                        city.setId(itemid);
                        city.setCityName(privinceitem[1]);
                        list.add(city);
                    }
                    DistrictCity[] products = new DistrictCity[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        products[i] = (DistrictCity) list.get(i);
                    }
                    mDistrictCityHttpListener.onSucced(products);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG, error.getMessage());
                if (null != mHttpResultListener) {
                    mDistrictCityHttpListener.onError(Define.NetErrorReason.NOT_KNOWN.getReason());
                }
            }
        };
    }
}
