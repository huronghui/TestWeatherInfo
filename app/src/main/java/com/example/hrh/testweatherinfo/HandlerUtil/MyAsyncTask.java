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
    * ������UI�߳��У��ڵ���doInBackground()֮ǰִ��
     */
    @Override
    protected void onPreExecute() {

    }

    /**
     * ��̨���еķ��������������ڷ�UI�̣߳�����ִ�к�ʱ�ķ���
     * @param params
     * @return
     */
    @Override
    protected Integer doInBackground(Void... params) {
        publishProgress();                           // ������
        return null;
    }

    /**
     * ������UI�߳��У���doInBackground()ִ����Ϻ�ִ��
     * @param integer
     */
    @Override
    protected void onPostExecute(Integer integer) {

    }

    /**
     *  ��publishProgress()�������Ժ�ִ�У�publishProgress()���ڸ��½���
     * @param vaules
     */
    @Override
    protected void onProgressUpdate(Integer... vaules) {
        progressBar.setProgress(vaules[0]);
    }
}
