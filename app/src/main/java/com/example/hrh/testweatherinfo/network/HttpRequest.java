package com.example.hrh.testweatherinfo.network;

import android.app.Activity;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hrh.testweatherinfo.ApplicationData;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.util.Map;
import java.util.Objects;
import com.example.hrh.testweatherinfo.network.json.PackageParser;

/**
 * Created by hrh on 2015/8/21.
 */
public class HttpRequest {
    public static final String TAG = HttpRequest.class.getSimpleName();
    private Activity  mActivity;
    public RequestQueue mHttpRequestQueue;
    public OnHttpRequestListener mHttpResultListener;
    public OnStringHttpRequestListener mStringHttpResultListener;
    PackageParser mJsonObjectParser;

    public interface OnHttpRequestListener {
        void onSucceed(String url, JSONObject response);

        void onError(VolleyError error);
    }

    public interface OnStringHttpRequestListener {
        void onSucceed(String response);

        void onError(VolleyError error);
    }

    public HttpRequest(Activity activity) {
        mActivity = activity;
        ApplicationData appData = (ApplicationData)activity.getApplication();
        mHttpRequestQueue = appData.getHttpRequestQueue();
        if(null == mHttpRequestQueue) {
            mHttpRequestQueue = Volley.newRequestQueue(mActivity);
            appData.setHttpRequestQueue(mHttpRequestQueue);
        }

        mJsonObjectParser = appData.getPackageParser();
        if(null == mJsonObjectParser) {
            mJsonObjectParser = new PackageParser();
            appData.setPackageParser(mJsonObjectParser);
        }
    }

    public int stop(RequestQueue.RequestFilter requestFilter) {
        mHttpRequestQueue.cancelAll(requestFilter);
        return 0;
    }

    public int add(String url, JSONObject jsonObject) {
        if(null != jsonObject) {
            Log.d(TAG, "Out url = " + url + " jsonObject = "+ jsonObject.toString());
        }
        mHttpRequestQueue.add(getJsonObjectRequest(url, jsonObject));
        return 0;
    }

    public int StringRequest(String url) {
        Log.e(TAG,"url =" +url);
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "response = " + response);
                          mStringHttpResultListener.onSucceed(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.e(TAG, "response = ");
                          mStringHttpResultListener.onError(arg0);

            }
        }) {
            protected final String TYPE_UTF8_CHARSET = "charset=UTF-8";
            @Override
            protected Response<String> parseNetworkResponse(
                    NetworkResponse response) {
                try {
                    String type = response.headers.get(HTTP.CONTENT_TYPE);
                    if (type == null) {
                        type = TYPE_UTF8_CHARSET;
                        response.headers.put(HTTP.CONTENT_TYPE, type);
                    } else if (!type.contains("UTF-8")) {
                        type += ";" + TYPE_UTF8_CHARSET;
                        response.headers.put(HTTP.CONTENT_TYPE, type);
                    }
                } catch (Exception e) {
                }
                return super.parseNetworkResponse(response);
            }
        };

        request.setTag(mActivity);
        mHttpRequestQueue.add(request);
        return 0;
    }
    public int add(String url, JSONObject jsonObject, Objects tag) {
        if (null != jsonObject) {
            Log.d(TAG, "Out url = " + url + " jsonObject = " + jsonObject.toString());
        }
        Utf8JsonObjectRequest request = getJsonObjectRequest(url, jsonObject);
        request.setTag(tag);
        mHttpRequestQueue.add(request);
        return 0;
    }

    public int add(int type, String url, JSONObject jsonObject) {
        if (null != jsonObject) {
            Log.d(TAG, "Out url = " + url + " jsonObject = " + jsonObject.toString());
        }
        Utf8JsonObjectRequest request = getJsonObjectRequest(type, url, jsonObject);
//        request.setTag(tag);
        mHttpRequestQueue.add(request);
        return 0;
    }

    public int query(String url, Map<String, String> params) {
        mHttpRequestQueue.add(getNormalJsonPostRequest(url, params));
        return 0;
    }

    public JsonObjectRequest getBaseJsonObjectRequest(final String url) {
        return new JsonObjectRequest(url, null, createJsonListener(url), createErrorListener(url));
    }

    public Request<JSONObject> getNormalJsonPostRequest(final String url, Map<String, String> params) {
        return new NormalJsonPostRequest(url, createJsonListener(url), createErrorListener(url), params);
    }

    public Utf8JsonObjectRequest getJsonObjectRequest(final String url, JSONObject jsonObject) {
            return new Utf8JsonObjectRequest(url, jsonObject, createJsonListener(url),
                    createErrorListener(url), mActivity);
    }

    public Utf8JsonObjectRequest getJsonObjectRequest(int type, final String url, JSONObject jsonObject) {
        return new Utf8JsonObjectRequest(type, url, jsonObject, createJsonListener(url),
                createErrorListener(url), mActivity);
    }

    private Response.Listener<JSONObject> createJsonListener(final String url) {
        return new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                if (null != mHttpResultListener) {
                    if (null == response) {
                        Log.d(TAG,"url = " + url + " ||| response = null ERROR");
                        return;
                    }
                    Log.d(TAG,"url = " + url + " ||| " + response.toString());
                    mHttpResultListener.onSucceed(url, response);
                }
            }
        };
    }

    private Response.ErrorListener createErrorListener(final String url) {
        return new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (null != mHttpResultListener) {
                    mHttpResultListener.onError(error);
                }
            }
        };
    }
}
