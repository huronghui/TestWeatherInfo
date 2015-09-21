package com.example.hrh.testweatherinfo.DataManagerCache;

import android.content.Context;

import java.io.File;

/**
 * Created by hrh on 2015/9/21.
 */
public class DataCleanManager {

    /**
     * 清除本应用内部缓存
     * (/com.example.hrh.testweatherinfo.data/com.example.hrh.testweatherinfo.data/com.xxx.xxx/cache)
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
     * @param filePath
     */
    public static void cleanCustomCache(File file) {
        deleteFilesByDirectory(file);
    }

    /**
     * 清楚本应用所有数据库
     * (/com.example.hrh.testweatherinfo.data/com.example.hrh.testweatherinfo.data/com.xxx.xxx/databases)
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/com/example/hrh/testweatherinfo/data/com.example.hrh.testweatherinfo.data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File child : directory.listFiles()) {
                if (child.isDirectory()) {
                    deleteFilesByDirectory(child);
                }
                child.delete();
            }
        }
    }
}
