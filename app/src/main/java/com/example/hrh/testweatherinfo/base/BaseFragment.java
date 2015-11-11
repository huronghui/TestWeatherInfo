package com.example.hrh.testweatherinfo.base;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.interf.BaseFragmentInterface;

/**
 * Created by hrh on 2015/11/6.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener, BaseFragmentInterface{

    public enum STATE {
        NONE,
        REFRESH,
        LOADMORE,
        NOMORE,
        FRESSNONE;
    }
    public static STATE mState = STATE.NONE;
    protected LayoutInflater mInflater;

    public ApplicationData getApplicationData() {
        return (ApplicationData)(getActivity().getApplication());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = inflater;
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    protected View inflateView(int resId) {
        return this.mInflater.inflate(getLayoutId(), null);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected abstract int getLayoutId();

   public boolean onBackpressd() {
       return false;
   }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
