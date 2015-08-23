package network;

import android.app.Activity;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.hrh.testweatherinfo.Define;

import java.util.ArrayList;
import java.util.List;

import data.PrivinceCity;

/**
 * Created by hrh on 2015/8/22.
 */
public class PrivinceCityHttpRequest extends HttpRequest {

    public static final String TAG = PrivinceCityHttpRequest.class.getSimpleName();
    public onPrivinceCityHttpRequestListener mPrivinceCityHttpRequestListener;


    public interface onPrivinceCityHttpRequestListener {
        void onSucced(PrivinceCity[] response);

        void onError(int reason);
    }

    public void setmProvincesStringHttpRequestListener(onPrivinceCityHttpRequestListener listener) {
        mPrivinceCityHttpRequestListener = listener;
    }


    public PrivinceCityHttpRequest(Activity activity) {
        super(activity);
        mStringHttpResultListener = new OnStringHttpRequestListener() {

            @Override
            public void onSucceed(String response) {
                if(null != response) {
                    Log.e(TAG, "response = " + response);
                    String privince[] = response.split(",");
                    List list=new ArrayList<PrivinceCity>();
                    for(int i = 0; i < privince.length; i++) {
                        String privinceitem[] = privince[i].split("\\|");
                        PrivinceCity city = new PrivinceCity();
                        Long itemid = (long)Integer.valueOf(privinceitem[0]).intValue();
                        city.setId(itemid);
                        city.setCountyName(privinceitem[1]);
                        list.add(city);
                    }
                    PrivinceCity[] products = new PrivinceCity[list.size()];
                    for(int i=0;i<list.size();i++){
                        products[i]=(PrivinceCity)list.get(i);
                    }
                    mPrivinceCityHttpRequestListener.onSucced(products);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG,error.getMessage());
                if (null != mHttpResultListener) {
                    mPrivinceCityHttpRequestListener.onError(Define.NetErrorReason.NOT_KNOWN.getReason());
                }
            }
        };
    }
}
