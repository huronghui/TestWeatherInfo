package network;

import android.app.Activity;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.hrh.testweatherinfo.Define;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import data.City;

/**
 * Created by hrh on 2015/8/21.
 */
public class ProvincesHttpRequest extends HttpRequest{
    private static final String TAG = ProvincesHttpRequest.class.getSimpleName();

//    private OnProvincesHttpRequestListener mProvincesHttpRequestListener;
    private OnProvincesHttpStringRequestListener mProvincesHttpStringRequestListener;
//    public interface OnProvincesHttpRequestListener {
//        void onSucceed(String url, JSONObject products);
//
//        void onError(int reason);
//    }

    public  interface OnProvincesHttpStringRequestListener {
        void onSucced(City[] response);

        void onError(int reason);
    }

//    public void setProvincesHttpRequestListener(OnProvincesHttpRequestListener listener) {
//        mProvincesHttpRequestListener = listener;
//    }

    public void setmProvincesStringHttpRequestListener(OnProvincesHttpStringRequestListener listener) {
        mProvincesHttpStringRequestListener = listener;
    }

    public ProvincesHttpRequest(Activity activity) {
        super(activity);

        mStringHttpResultListener = new OnStringHttpRequestListener() {

            @Override
            public void onSucceed(String response) {
                if(null != response) {
                    Log.e(TAG,"response = "+response);
                    String privince[] = response.split(",");
                    List list=new ArrayList<City>();
                    for(int i = 0; i < privince.length; i++) {
                        String privinceitem[] = privince[i].split("\\|");
                        City city = new City();
                        Long itemid = (long)Integer.valueOf(privinceitem[0]).intValue();
                        city.setId(itemid);
                        city.setCityName(privinceitem[1]);
                        list.add(city);
                    }
                    City[] products = new City[list.size()];
                    for(int i=0;i<list.size();i++){
                        products[i]=(City)list.get(i);
                    }
                    mProvincesHttpStringRequestListener.onSucced(products);
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG,error.getMessage());
                if (null != mHttpResultListener) {
                    mProvincesHttpStringRequestListener.onError(Define.NetErrorReason.NOT_KNOWN.getReason());
                }
            }
        };
    }
}
