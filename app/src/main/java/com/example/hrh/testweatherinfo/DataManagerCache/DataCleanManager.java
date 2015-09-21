package com.example.hrh.testweatherinfo.DataManagerCache;

import android.content.Context;

import java.io.File;

/**
 * Created by hrh on 2015/9/21.
 */
public class DataCleanManager {

    /**
     * �����Ӧ���ڲ�����
     * (/com.example.hrh.testweatherinfo.data/com.example.hrh.testweatherinfo.data/com.xxx.xxx/cache)
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * ����Զ���·���µ��ļ���ʹ����С�ģ��벻Ҫ��ɾ������ֻ֧��Ŀ¼�µ��ļ�ɾ��
     * @param filePath
     */
    public static void cleanCustomCache(File file) {
        deleteFilesByDirectory(file);
    }

    /**
     * �����Ӧ���������ݿ�
     * (/com.example.hrh.testweatherinfo.data/com.example.hrh.testweatherinfo.data/com.xxx.xxx/databases)
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/com/example/hrh/testweatherinfo/data/com.example.hrh.testweatherinfo.data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * ɾ������ ����ֻ��ɾ��ĳ���ļ����µ��ļ�����������directory�Ǹ��ļ�������������
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
