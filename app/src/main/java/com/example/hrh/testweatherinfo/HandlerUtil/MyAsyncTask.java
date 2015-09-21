package com.example.hrh.testweatherinfo.HandlerUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

/**
 * Created by hrh on 2015/9/20.
 */
public class MyAsyncTask extends AsyncTask<Void, Integer, Integer>{

    private Context context;
    private ProgressBar progressBar;

    public MyAsyncTask(Context context, ProgressBar progressBar) {
        this.context = context;
        this.progressBar = progressBar;
    }
    /*
    * 运行在UI线程中，在调用doInBackground()之前执行
     */
    @Override
    protected void onPreExecute() {

    }

    /**
     * 后台运行的方法，可以运行在非UI线程，可以执行耗时的方法
     * @param params
     * @return
     */
    @Override
    protected Integer doInBackground(Void... params) {
        publishProgress();                           // 进度条
        return null;
    }

    /**
     * 运行在UI线程中，在doInBackground()执行完毕后执行
     * @param integer
     */
    @Override
    protected void onPostExecute(Integer integer) {

    }

    /**
     *  在publishProgress()被调用以后执行，publishProgress()用于更新进度
     * @param vaules
     */
    @Override
    protected void onProgressUpdate(Integer... vaules) {
        progressBar.setProgress(vaules[0]);
    }
}
