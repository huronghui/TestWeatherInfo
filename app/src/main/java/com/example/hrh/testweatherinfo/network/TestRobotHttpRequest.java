package com.example.hrh.testweatherinfo.network;

import android.app.Activity;

import com.android.volley.VolleyError;
import com.example.hrh.testweatherinfo.Define;
import com.example.hrh.testweatherinfo.UtilTest.KJLoger;
import com.example.hrh.testweatherinfo.data.TestRobotBean.TestRobotResult;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hrh on 2015/11/26.
 */
public class TestRobotHttpRequest extends HttpRequest{

    private String TAG = ImoocInfoHttpRequest.class.getSimpleName();

    public void setmImoocInfoHttpRequestListtener(TestRobotHttpRequestResponse mImoocInfoHttpRequestListtener) {
        this.mImoocInfoHttpRequestListtener = mImoocInfoHttpRequestListtener;
    }

    private TestRobotHttpRequestResponse mImoocInfoHttpRequestListtener;

    public interface TestRobotHttpRequestResponse {
        public void onSuccess(String msg);
        public void onError(int reason);
    }
    public TestRobotHttpRequest(Activity activity) {
        super(activity);
        mHttpResultListener = new OnHttpRequestListener() {

            @Override
            public void onSucceed(String url, JSONObject response) {
                if (response != null) {
                    KJLoger.debug("response =" + response.toString());
                    TestRobotResult testRobotResult = mJsonObjectParser.parse(response, TestRobotResult.class);
                    if (testRobotResult.getText() != null) {
                        mImoocInfoHttpRequestListtener.onSuccess(testRobotResult.getText());
                    } else {
                        KJLoger.debug("onErroe product detail. ");
                        mImoocInfoHttpRequestListtener.onError(Define.NetErrorReason.NOT_KNOWN.getReason());
                    }
                } else {
                    mImoocInfoHttpRequestListtener.onError(Define.NetErrorReason.WRONG_ARGU.getReason());
                }
            }
                @Override
                public void onError (VolleyError error){
                    if (null != mImoocInfoHttpRequestListtener) {
                        mImoocInfoHttpRequestListtener.onError(Define.NetErrorReason.NOT_KNOWN.getReason());
                    }
                }
        };
    }
}
