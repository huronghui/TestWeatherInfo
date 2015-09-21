package com.example.hrh.testweatherinfo.UtilTest;

import android.content.pm.PackageManager;

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
}
