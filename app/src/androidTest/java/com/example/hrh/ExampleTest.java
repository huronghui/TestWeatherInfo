package com.example.hrh;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.test.ActivityUnitTestCase;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;

import com.android.volley.Request;
import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.Define;
import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.UtilTest.HttpUtils;
import com.example.hrh.testweatherinfo.UtilTest.KJLoger;
import com.example.hrh.testweatherinfo.activity.MainActivity;
import com.example.hrh.testweatherinfo.data.TestRobotBean.TestRobotResult;
import com.example.hrh.testweatherinfo.network.TestRobotHttpRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by hrh on 2015/11/27.
 */
public class ExampleTest extends AndroidTestCase {


    public void test() {
        String res = HttpUtils.doGet("给我讲个笑话");
        Log.e("TAG11", res);
        Gson gson = new Gson();
        TestRobotResult result = null;
        try
        {
            result = gson.fromJson(res, TestRobotResult.class);
            Log.e("TAG11", result.getText());
        } catch (Exception e)
        {
            Log.e("TAG11","Hello world");
        }
    }
}
