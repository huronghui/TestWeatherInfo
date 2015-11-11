package com.example.hrh.testweatherinfo.network;

import android.app.Activity;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.hrh.testweatherinfo.Define;
import com.example.hrh.testweatherinfo.UtilTest.KJLoger;
import com.example.hrh.testweatherinfo.data.WeatherBean.Newsbean;
import com.example.hrh.testweatherinfo.data.imoocInfo.ImoocInfoDataBean;
import com.example.hrh.testweatherinfo.data.imoocInfo.ImoocInfoListDataBean;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hrh on 2015/11/9.
 */
public class ImoocInfoHttpRequest extends HttpRequest {

    private String TAG = ImoocInfoHttpRequest.class.getSimpleName();
    private ImoocInfoHttpRequestListtener mImoocInfoHttpRequestListtener;

    public interface ImoocInfoHttpRequestListtener {
        public void onSuccess(List<ImoocInfoListDataBean> bean);
        public void onError(int reason);
    }

    public void setmImoocInfoHttpRequestListtener(ImoocInfoHttpRequestListtener mImoocInfoHttpRequestListtener) {
        this.mImoocInfoHttpRequestListtener = mImoocInfoHttpRequestListtener;
    }

    public ImoocInfoHttpRequest(Activity activity) {
        super(activity);
        mHttpResultListener = new OnHttpRequestListener() {
            @Override
            public void onSucceed(String url, JSONObject response) {
                if (response != null) {
                    KJLoger.debug("response =" + response.toString());
                    ImoocInfoDataBean imoocInfoDataBean = mJsonObjectParser.parse(response, ImoocInfoDataBean.class);
                    if(imoocInfoDataBean != null) {
                        ImoocInfoListDataBean[] imoocInfoListDataBeans = imoocInfoDataBean.getImoocInfoListDataBean();
                        if (imoocInfoListDataBeans.length > 0) {
                            List<ImoocInfoListDataBean> imoocInfoListDataBeanList = new ArrayList<>();
                            imoocInfoListDataBeanList = Arrays.asList(imoocInfoListDataBeans);
                            mImoocInfoHttpRequestListtener.onSuccess(imoocInfoListDataBeanList);
                        } else {
                            KJLoger.debug("onErroe product detail. ");
                            mImoocInfoHttpRequestListtener.onError(Define.NetErrorReason.NOT_KNOWN.getReason());
                        }
                    } else {
                        mImoocInfoHttpRequestListtener.onError(Define.NetErrorReason.WRONG_ARGU.getReason());
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {
                if (null != mImoocInfoHttpRequestListtener) {
                    mImoocInfoHttpRequestListtener.onError(Define.NetErrorReason.NOT_KNOWN.getReason());
                }
            }
        };
    }
}
