package com.example.hrh.testweatherinfo.fragment;

import android.os.Bundle;
import android.util.Log;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.Define;
import com.example.hrh.testweatherinfo.UtilTest.KJLoger;
import com.example.hrh.testweatherinfo.UtilTest.ListUtil;
import com.example.hrh.testweatherinfo.adapter.TestListAdapter;
import com.example.hrh.testweatherinfo.base.BaseListFragment;
import com.example.hrh.testweatherinfo.base.ListBaseAdapter;
import com.example.hrh.testweatherinfo.data.DistrictCity;
import com.example.hrh.testweatherinfo.data.imoocInfo.ImoocInfoListDataBean;
import com.example.hrh.testweatherinfo.interf.OnTabReselectListener;
import com.example.hrh.testweatherinfo.network.DistrictCityHttpRequest;
import com.example.hrh.testweatherinfo.network.ImoocInfoHttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrh on 2015/11/9.
 */
public class TestListFragment extends BaseListFragment implements OnTabReselectListener{

    private ImoocInfoHttpRequest mImoocInfoHttpRequest;

    @Override
    protected void InitHttpRequest() {
        mImoocInfoHttpRequest = new ImoocInfoHttpRequest(getActivity());
        mImoocInfoHttpRequest.setmImoocInfoHttpRequestListtener(imoocInfoHttpRequestListtener);
    }

    @Override
    protected TestListAdapter getListAdapter() {
        return new TestListAdapter((ApplicationData)getActivity().getApplication(), mListview);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void sendRequestData() {
        mImoocInfoHttpRequest.add(Define.IMOOCURL,null);
    }

    public ImoocInfoHttpRequest.ImoocInfoHttpRequestListtener imoocInfoHttpRequestListtener = new ImoocInfoHttpRequest.ImoocInfoHttpRequestListtener() {
        @Override
        public void onSuccess(List<ImoocInfoListDataBean> bean) {
            ListUtil<ImoocInfoListDataBean> listUtil = new ListUtil<>();
            ArrayList<ImoocInfoListDataBean> arrayList = listUtil.listToAarraylist(bean);
            mAdapter.setData(arrayList);
            executeOnLoadFinish();
        }

        @Override
        public void onError(int reason) {
            KJLoger.debug("Error = " + reason);
        }
    };

    @Override
    public void onTabReselect() {
        onRefresh();
    }
}
