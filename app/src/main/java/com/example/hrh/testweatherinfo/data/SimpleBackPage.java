package com.example.hrh.testweatherinfo.data;

import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.activity.AsyncTaskImageLoaderActivity;
import com.example.hrh.testweatherinfo.activity.TestFragmentActivity;

/**
 * Created by hrh on 2015/10/6.
 */
public enum  SimpleBackPage {

    ASYNCTASKIMAGELOADER(1, R.string.asynctaskimageloader, AsyncTaskImageLoaderActivity.class),
    TESTFRAGMENTACTIVITY(2,  R.string.asynctaskimageloader, TestFragmentActivity.class);

    private int title;
    private Class<?> clz;
    private int value;

    private SimpleBackPage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SimpleBackPage getPageByValue(int val) {
        for (SimpleBackPage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }
}
