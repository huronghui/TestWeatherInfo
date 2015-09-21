package com.example.hrh.testweatherinfo.network;

import android.app.Activity;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.hrh.testweatherinfo.Define;

import java.util.ArrayList;
import java.util.List;

import com.example.hrh.testweatherinfo.data.CityData;

/**
 * Created by hrh on 2015/8/21.
 */
public class ProvincesHttpRequest extends HttpRequest{
    private static final String TAG = ProvincesHttpRequest.class.getSimpleName();

//    private OnProvincesHttpRequestListener mProvincesHttpRequestListener;
    private OnProvincesHttpStringRequestListener mProvincesHttpStringRequestListener;

    public  interface OnProvincesHttpStringRequestListener {
        public void onSucced(CityData[] response);

        public void onError(int reason);
    }


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
                    List list=new ArrayList<CityData>();
                    for(int i = 0; i < privince.length; i++) {
                        String privinceitem[] = privince[i].split("\\|");
                        CityData cityData = new CityData();
                        Long itemid = (long)Integer.valueOf(privinceitem[0]).intValue();
                        cityData.setId(itemid);
                        cityData.setCityName(privinceitem[1]);
                        list.add(cityData);
                    }
                    CityData[] products = new CityData[list.size()];
                    for(int i=0;i<list.size();i++){
                        products[i]=(CityData)list.get(i);
                    }
                    mProvincesHttpStringRequestListener.onSucced(products);
                } else {
                    mProvincesHttpStringRequestListener.onError(Define.NetErrorReason.NOT_KNOWN.getReason());
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e(TAG,"error = " + error.getMessage());
                if (null != mProvincesHttpStringRequestListener) {
                    mProvincesHttpStringRequestListener.onError(Define.NetErrorReason.NOT_KNOWN.getReason());
                }
            }
        };
    }
}
