package com.example.hrh.testweatherinfo.UtilTest;

import android.content.pm.PackageManager;
import android.net.ConnectivityManager;

import com.example.hrh.testweatherinfo.base.BaseApplication;

/**
 * Created by hrh on 2015/9/21.
 */
public class TDevice {

    public static int getVersionCode() {
        int versionCode = 0;
        try {
            versionCode = BaseApplication
                    .context()
                    .getPackageManager()
                    .getPackageInfo(BaseApplication.context().getPackageName(),
                            0).versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    public static boolean hasInternet() {
        boolean flag;
        if (((ConnectivityManager) BaseApplication.context().getSystemService(
                "connectivity")).getActiveNetworkInfo() != null)
            flag = true;
        else
            flag = false;
        return flag;
    }
}
