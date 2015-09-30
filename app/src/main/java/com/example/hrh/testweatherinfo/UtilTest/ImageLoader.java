package com.example.hrh.testweatherinfo.UtilTest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.adapter.NewsAdapter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hrh on 2015/9/30.
 */
public class ImageLoader {

    //create Cache
    private LruCache<String, Bitmap> mCache;
    private ImageView imageView;
    private String url;
    private ListView mListview;
    private Set<newsAsyncTask> mTask;

    public ImageLoader(ListView listView) {
        mListview = listView;
        mTask = new HashSet<>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cachesize = maxMemory / 4;
        mCache = new LruCache<String, Bitmap>(cachesize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存时调用
                return value.getByteCount();
            }
        };
    }

    //增加到缓存
    public void addBitmapToCache(String url, Bitmap bitmap) {
           if(getBitmapFromCache(url) == null) {
               mCache.put(url, bitmap);
           }
    }

    //从缓存中获取数据
    public Bitmap getBitmapFromCache(String url){
        return mCache.get(url);
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(imageView.getTag().equals(url)) {
                imageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    public void showImageByThread( ImageView imageView, final String ImageUrl) {

        this.imageView = imageView;
        this.url = ImageUrl;
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Bitmap bitmap = getBitmapForUrl(ImageUrl);
                    Message message = Message.obtain();
                    message.obj = bitmap;
                    handler.sendMessage(message);
                }
            }.start();
    }

    public Bitmap getBitmapForUrl(String UrlString) {
        Bitmap bitmap = null;
        InputStream is = null;
        try {
            URL url = new URL(UrlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void shwoImageByAsyncTask(ImageView imageView, String ImageUrl) {
            Bitmap bitmap = getBitmapFromCache(ImageUrl);
            if(bitmap == null) {
                imageView.setImageResource(R.mipmap.ic_launcher);
//                new newsAsyncTask(imageView, ImageUrl).execute(ImageUrl);
            } else {
                imageView.setImageBitmap(bitmap);
            }
    }

    public void cancelAllTask() {
        if(mTask != null) {
            for(newsAsyncTask task : mTask) {
                task.cancel(false);
            }
        }
    }

    private class newsAsyncTask extends AsyncTask<String, Void, Bitmap> {

        public ImageView mImageview;
        private String url;
        public newsAsyncTask( String url) {
//                this.mImageview = imageView;
            this.url = url;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            //从网络中获取图片
            Bitmap bitmap = getBitmapForUrl(url);
            if(bitmap != null) {
            //将图片存入缓存
                addBitmapToCache(url, bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView)mListview.findViewWithTag(url);
            if(imageView != null && bitmap != null) {
                if(imageView.getTag().equals(url)) {
                    imageView.setImageBitmap(bitmap);
                }
            }
            mTask.remove(this);
        }
    }

    //用来加载从start 到 end 的所有图片
    public void loadImages(int start, int end) {
        for (int i = start; i < end; i++) {
            String url = NewsAdapter.URLS[i];
            Bitmap bitmap = getBitmapFromCache(url);
            if(bitmap == null) {
                newsAsyncTask task = new newsAsyncTask(url);
                task.execute(url);
                mTask.add(task);
            } else {
                ImageView imageView = (ImageView)mListview.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
