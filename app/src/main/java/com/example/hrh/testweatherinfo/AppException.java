package com.example.hrh.testweatherinfo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;

import com.example.hrh.testweatherinfo.UtilTest.UIHelper;

import org.kymjs.kjframe.utils.FileUtils;
import org.kymjs.kjframe.utils.SystemTool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Created by hrh on 2015/11/11.
 */
public class AppException extends Exception implements Thread.UncaughtExceptionHandler{

    private ApplicationData mContext;
    private byte type;// �쳣������
    // �쳣��״̬�룬����һ�������������״̬��
    private int code;

    private AppException(Context context) {
        this.mContext = (ApplicationData)context;
    }

    private AppException(byte type, int code, Exception excp) {
        super(excp);
        this.type = type;
        this.code = code;
    }

    /**
     * ��ȡAPP�쳣�����������
     *
     * @param context
     * @return
     */
    public static AppException getAppExceptionHandler(Context context) {
        return new AppException(context.getApplicationContext());
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(!handleException(ex)) {
            System.exit(0);
        }
    }

    private boolean handleException(Throwable ex) {
        if(ex == null || mContext == null) {
            return false;
        }
        boolean success = true;
        try {
            success = saveToSdCard(ex);
        } catch (Exception e) {

        }finally {
            if(!success) {
                return false;
            }else {
                final Context context = AppManager.getAppManager().currentActivity();
                //��ʾ�쳣��Ϣ&���ͱ���
                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        UIHelper.sendAppCrashReport(context);
                        Looper.loop();
                    }
                }.start();
            }
        }
        return true;
    }

    private boolean saveToSdCard(Throwable ex) throws Exception {
        boolean append = false;
        File file = FileUtils.getSaveFile("TestWeather", "TestWeather.log");
        if (System.currentTimeMillis() - file.lastModified() > 5000) {
            append = true;
        }
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
                file, append)));
        // ���������쳣��ʱ��
        pw.println(SystemTool.getDataTime("yyyy-MM-dd-HH-mm-ss"));
        // �����ֻ���Ϣ
        dumpPhoneInfo(pw);
        pw.println();
        // �����쳣�ĵ���ջ��Ϣ
        ex.printStackTrace(pw);
        pw.println();
        pw.close();
        return append;
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        // Ӧ�õİ汾���ƺͰ汾��
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),
                PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);
        pw.println();

        // android�汾��
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        pw.println();

        // �ֻ�������
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        pw.println();

        // �ֻ��ͺ�
        pw.print("Model: ");
        pw.println(Build.MODEL);
        pw.println();

        // cpu�ܹ�
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
        pw.println();
    }
}
