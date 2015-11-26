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
    private byte type;// 异常的类型
    // 异常的状态码，这里一般是网络请求的状态码
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
     * 获取APP异常崩溃处理对象
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
                //显示异常信息&发送报告
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
        // 导出发生异常的时间
        pw.println(SystemTool.getDataTime("yyyy-MM-dd-HH-mm-ss"));
        // 导出手机信息
        dumpPhoneInfo(pw);
        pw.println();
        // 导出异常的调用栈信息
        ex.printStackTrace(pw);
        pw.println();
        pw.close();
        return append;
    }

    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        // 应用的版本名称和版本号
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),
                PackageManager.GET_ACTIVITIES);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);
        pw.println();

        // android版本号
        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        pw.println();

        // 手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        pw.println();

        // 手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
        pw.println();

        // cpu架构
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
        pw.println();
    }
}
