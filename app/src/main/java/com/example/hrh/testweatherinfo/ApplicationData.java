package com.example.hrh.testweatherinfo;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.android.volley.RequestQueue;
import com.example.hrh.testweatherinfo.DataManagerCache.DataCleanManager;
import com.example.hrh.testweatherinfo.UtilTest.KJLoger;
import com.example.hrh.testweatherinfo.UtilTest.MethodsCompat;
import com.example.hrh.testweatherinfo.UtilTest.StringUtil;
import com.example.hrh.testweatherinfo.UtilTest.TLog;
import com.example.hrh.testweatherinfo.activity.MainActivity;
import com.example.hrh.testweatherinfo.base.BaseApplication;
import com.example.hrh.testweatherinfo.datamanager.DataManager;
import com.example.hrh.testweatherinfo.network.json.PackageParser;

import java.util.Properties;
import java.util.UUID;

/**
 * Created by hrh on 2015/8/21.
 */
public class ApplicationData extends BaseApplication {

    private static final String TAG = ApplicationData.class.getSimpleName();
    private DataManager mDataManager;
    private RequestQueue mHttpRequestQueue;
    private PackageParser mPackageParser;
    private Activity mActivity;
    private static ApplicationData instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();

    }

    private void init() {
        KJLoger.openDebugLog(true);
        TLog.DEBUG = BuildConfig.DEBUG;
    }

    public static ApplicationData getInstance() {
        return instance;
    }

    public void setProperties(Properties ps) {
        Appconfig.getAppConfig(this).set(ps);
    }

    public Properties getProperties() {
        return Appconfig.getAppConfig(this).get();
    }

    public void setProperty(String key, String value) {
        Appconfig.getAppConfig(this).set(key, value);
    }

    /**
     * 获取cookie时传AppConfig.CONF_COOKIE
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        String res = Appconfig.getAppConfig(this).get(key);
        return res;
    }

    public void removeProperty(String... key) {
        Appconfig.getAppConfig(this).remove(key);
    }

    /**
     * 获取App唯一的标识
     * @return
     */
    public String getAppId() {
        String uniqueID= getProperty(Appconfig.CONF_APP_UNIQUEID);
        if(StringUtil.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            setProperty(Appconfig.CONF_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }

    /**
     * 获取App安装包的信息
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    /**
     *  清除保持的缓存
     */
    public void cleanCookie() {
        removeProperty(Appconfig.CONF_COOKIE);
    }

    /**
     * 清除APP缓存
     */
    public void clearAppCache() {
        DataCleanManager.cleanDatabases(this);
        //清除数据缓存
        DataCleanManager.cleanInternalCache(this);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            DataCleanManager.cleanCustomCache(MethodsCompat
                    .getExternalCacheDir(this));
        }
        // 清除编辑器保存的临时内容
        Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }

    }
    public void initial(Activity activity) {
        mActivity = (MainActivity) activity;
        mDataManager = new DataManager(mActivity);
    }

    public RequestQueue getHttpRequestQueue() {
        return mHttpRequestQueue;
    }

    public void setHttpRequestQueue(RequestQueue httpRequestQueue) {
        this.mHttpRequestQueue = httpRequestQueue;
    }

    public void setPackageParser(PackageParser packageParser) {
        this.mPackageParser = packageParser;
    }

    public PackageParser getPackageParser() {
        return mPackageParser;
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }
}
