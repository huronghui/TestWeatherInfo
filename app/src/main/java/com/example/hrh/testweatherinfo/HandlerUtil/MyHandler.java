package com.example.hrh.testweatherinfo.HandlerUtil;


import android.app.Activity;
import android.os.Handler;

/**
 * Created by hrh on 2015/9/20.
 */
public class MyHandler extends Handler {

    private Activity activity;
    private int TIME;
    public MyTimeTask myTimeTask;

    public MyHandler(Activity activity, int time) {
        this.activity = activity;
        this.TIME = time;
    }

    public MyHandler(Activity activity, int time, MyTimeTask myTimeTask) {
        this.activity = activity;
        this.TIME = time;
        this.myTimeTask = myTimeTask;
    }

    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                handler.postDelayed(this,TIME);
                myTimeTask.TimeTask();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("hello exception is that...");
            }
        }
    };

    /**
     *
     */
    public void startTimeTask() {
        handler.postDelayed(runnable, TIME);
    }

    public void stopTimeTask() {
        handler.removeCallbacks(runnable);
    }
}
