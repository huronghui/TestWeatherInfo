package com.example.hrh.testweatherinfo.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.UtilTest.ImageLoader;
import com.example.hrh.testweatherinfo.base.ListBaseAdapter;
import com.example.hrh.testweatherinfo.data.DistrictCity;
import com.example.hrh.testweatherinfo.data.imoocInfo.ImoocInfoListDataBean;
import com.example.hrh.testweatherinfo.view.CityItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrh on 2015/11/9.
 */
public class TestListAdapter extends ListBaseAdapter<ImoocInfoListDataBean>  implements AbsListView.OnScrollListener{

    private ImageLoader imageLoader;
    private int mSatrt, mEnd;
    private boolean mFirstIn;
    public static String[] URLS;

    public TestListAdapter(ApplicationData mAppData, ListView listView) {
        super(mAppData);
        imageLoader = new ImageLoader(listView);
        mFirstIn = true;
        listView.setOnScrollListener(this);
    }

    @Override
    public void setData(ArrayList<ImoocInfoListDataBean> data) {
        super.setData(data);
        setUrl();
    }

   public void  setUrl() {
       URLS = new String[mData.size()];
       for(int i = 0; i < mData.size(); i++) {
           URLS[i] = mData.get(i).getPicSmall();
       }
    }
    @Override
    public void addData(List<ImoocInfoListDataBean> data) {
        super.addData(data);
       setUrl();
    }

    @Override
    protected View getRealView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = getLayoutInflater(parent.getContext()).inflate(
                    R.layout.asynctaskimageroaderitem_layout, null);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.tv_icon);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_context);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);

        ImoocInfoListDataBean imoocInfoListDataBean = mData.get(position);
        String url = imoocInfoListDataBean.getPicSmall();
        viewHolder.ivIcon.setTag(url);
//        new ImageLoader().showImageByThread(viewHolder.ivIcon,
//                url);
//        imageLoader.shwoImageByAsyncTask(viewHolder.ivIcon, url);
        imageLoader.shwoImageByAsyncTask(viewHolder.ivIcon, url);
        viewHolder.tvTitle.setText(imoocInfoListDataBean.getName());
        viewHolder.tvContent.setText(imoocInfoListDataBean.getDescription());
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE) {
            //加载可见项
            imageLoader.loadImages(mSatrt, mEnd, URLS);
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
            imageLoader.loadImages(mSatrt, mEnd, URLS);
            mFirstIn = false;
        }
    }

    class ViewHolder{
        public TextView tvTitle;
        public TextView tvContent;
        public ImageView ivIcon;
    }
}
