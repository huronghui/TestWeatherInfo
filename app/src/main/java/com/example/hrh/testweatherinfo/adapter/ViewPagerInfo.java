package com.example.hrh.testweatherinfo.adapter;

import android.os.Bundle;

/**
 * Created by hrh on 2015/11/21.
 */
public class ViewPagerInfo {
    public final String tag;
    public final Class<?> aClass;
    public final Bundle args;
    public final String title;

    public ViewPagerInfo(String _title, String _tag, Class<?> _class, Bundle _args) {
        title = _title;
        tag = _tag;
        aClass = _class;
        args =  _args;
    }
}
