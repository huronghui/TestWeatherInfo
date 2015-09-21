package com.example.hrh.testweatherinfo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.hrh.testweatherinfo.UtilTest.StringUtil;

import org.kymjs.kjframe.utils.FileUtils;
import org.kymjs.kjframe.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by hrh on 2015/9/21.
 */
public class LogUploadService extends Service{

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final File log = FileUtils.getSaveFile("OSChina", "OSCLog.log");
        String data = null;
        try {
            FileInputStream inputStream = new FileInputStream(log);
            data = StringUtil.toConvertString(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LogUploadService.this.stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
}
