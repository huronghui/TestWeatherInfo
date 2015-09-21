package com.example.hrh.testweatherinfo.UtilTest;

import android.util.Log;

/**
 * Created by hrh on 2015/9/21.
 */
public class TLog {
    public static final String LOG_TAG = "SIMICO";
    public static boolean DEBUG = true;

    public TLog() {

    }

    public static final void analytice(String log) {
        if(DEBUG) {
            Log.d(LOG_TAG,log);
        }
    }

    public static final void error(String log) {
        if (DEBUG)
            Log.e(LOG_TAG, "" + log);
    }

    public static final void log(String log) {
        if (DEBUG)
            Log.i(LOG_TAG, log);
    }

    public static final void log(String tag, String log) {
        if (DEBUG)
            Log.i(tag, log);
    }

    public static final void logv(String log) {
        if (DEBUG)
            Log.v(LOG_TAG, log);
    }

    public static final void warn(String log) {
        if (DEBUG)
            Log.w(LOG_TAG, log);
    }
}
