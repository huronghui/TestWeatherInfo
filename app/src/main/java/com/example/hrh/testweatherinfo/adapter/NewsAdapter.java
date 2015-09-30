package com.example.hrh.testweatherinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.UtilTest.ImageLoader;
import com.example.hrh.testweatherinfo.data.WeatherBean.Newsbean;

import java.util.List;

/**
 * Created by hrh on 2015/9/30.
 */
public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener{

    private List<Newsbean> mList;
    private LayoutInflater mInflater;
    private ImageLoader imageLoader;
    private int mSatrt, mEnd;
    public static String[] URLS;
    private boolean mFirstIn;

    public NewsAdapter(Context context, List<Newsbean> data, ListView listView) {
        mList = data;
        mInflater = LayoutInflater.from(context);
        imageLoader = new ImageLoader(listView);
        URLS = new String[data.size()];
        for(int i = 0; i < data.size(); i++) {
            URLS[i] = data.get(i).getNewsIconUrl();
        }
        mFirstIn = true;
        listView.setOnScrollListener(this);
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.asynctaskimageroaderitem_layout, null);
            viewHolder.ivIcon = (ImageView)convertView.findViewById(R.id.tv_icon);
            viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.tv_title);
            viewHolder.tvContent = (TextView)convertView.findViewById(R.id.tv_context);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);
        String url = mList.get(position).getNewsIconUrl();
        viewHolder.ivIcon.setTag(url);
//        new ImageLoader().showImageByThread(viewHolder.ivIcon,
//                url);
       imageLoader.shwoImageByAsyncTask(viewHolder.ivIcon, url);
        viewHolder.tvTitle.setText(mList.get(position).getNewsTitle());
        viewHolder.tvContent.setText(mList.get(position).getNewsCntext());
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE) {
            //加载可见项
            imageLoader.loadImages(mSatrt, mEnd);
        } else {
            //停止任务
            imageLoader.cancelAllTask();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mSatrt = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
        if(mFirstIn && visibleItemCount > 0) {
            imageLoader.loadImages(mSatrt, mEnd);
            mFirstIn = false;
        }
    }

    class ViewHolder{
        public TextView tvTitle;
        public TextView tvContent;
        public ImageView ivIcon;
    }
}
