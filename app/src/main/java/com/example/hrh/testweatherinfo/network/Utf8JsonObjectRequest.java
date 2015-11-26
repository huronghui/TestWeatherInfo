package com.example.hrh.testweatherinfo.network;

import android.app.Activity;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

// import java.util.HashMap;
// import java.util.Map;
// import com.android.volley.VolleyError;
// import com.android.volley.toolbox.StringRequest;

// import com.baidu.hui.Util;

public class Utf8JsonObjectRequest extends JsonObjectRequest {
    private static final String TAG = Utf8JsonObjectRequest.class.getSimpleName();
    private Activity mActivity;

    public Utf8JsonObjectRequest(int method, String url, JSONObject jsonRequest, Listener<JSONObject> listener,
                                 ErrorListener errorListener,Activity activity) {
        super(method, url, jsonRequest, listener, errorListener);
        setShouldCache(false);
        mActivity = activity;
    }

    public Utf8JsonObjectRequest(String url, JSONObject jsonRequest, Listener<JSONObject> listener,
                                 ErrorListener errorListener,Activity activity) {
        super(url, jsonRequest, listener, errorListener);
        setShouldCache(false);
        mActivity = activity;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        String parsed = response.headers.get("Date");
//        ApplicationData com.example.hrh.testweatherinfo.data = (ApplicationData)mActivity.getApplication();
//        com.example.hrh.testweatherinfo.data.setCurrentDate(parsed);
        Log.e(TAG,"set current time = "+parsed);
        String str = null;
        try {
            str = new String(response.data, "utf-8");
            return Response.success(new JSONObject(str), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json; charset=UTF-8");

        return headers;
    }
}
