package com.example.hrh.testweatherinfo.base;

import android.content.Context;
import android.content.Entity;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.UtilTest.TDevice;
import com.example.hrh.testweatherinfo.datamanager.DataManager;

import org.kymjs.kjframe.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrh on 2015/11/6.
 */
public class ListBaseAdapter<T extends Parcelable> extends BaseAdapter{

    public enum STATE {
        EMPTY_ITEM,
        LOAD_MORE,
        NO_MORE,
        NO_DATA,
        LESS_ONE_PAGE,
        NETWORK_ERROR,
        OTHER;
    }

    protected STATE state = STATE.LESS_ONE_PAGE;
    protected int _loadmoreText;
    protected int _loadFinishText;
    protected int _noDateText;
    private LayoutInflater mInflater;
    protected DataManager mDataManger;
    protected ApplicationData mAppData;

    protected LayoutInflater getLayoutInflater(Context context) {
        if (mInflater == null) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        return mInflater;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public STATE getState() {
        return this.state;
    }

    protected ArrayList<T> mData = new ArrayList<T>();

    public ListBaseAdapter(ApplicationData mAppData) {
        this.mAppData = mAppData;
        _loadmoreText = R.string.loading;
        _loadFinishText = R.string.loading_no_more;
        _noDateText = R.string.error_view_no_data;
        mDataManger = mAppData.getDataManager();
    }
    @Override
    public int getCount() {
       switch (getState()) {
           case EMPTY_ITEM:
               return getDataSizePlus1();
           case NETWORK_ERROR:
           case LOAD_MORE:
               return getDataSizePlus1();
           case NO_DATA:
               return 1;
           case NO_MORE:
               return getDataSizePlus1();
           case LESS_ONE_PAGE:
               return getDataSize();
           default:
               break;
       }
        return getDataSize();
    }

    public int getDataSizePlus1(){
        if(hasFooterView()){
            return getDataSize() + 1;
        }
        return getDataSize();
    }

    public int getDataSize() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        if(mData.size() > position) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(ArrayList<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public ArrayList<T> getData() {
        return mData == null ? (mData = new ArrayList<T>()) : mData;
    }

    public void addData(List<T> data) {
        if(mData != null && data != null && !data.isEmpty()) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addItem(T obj) {
        if (mData != null) {
            mData.add(obj);
        }
        notifyDataSetChanged();
    }

    public void addItem(int pos, T obj) {
        if (mData != null) {
            mData.add(pos, obj);
        }
        notifyDataSetChanged();
    }

    public void removeItem(Object obj) {
        mData.remove(obj);
        notifyDataSetChanged();
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void setNoDataText(int noDataText) {
        _noDateText = noDataText;
    }

    protected boolean loadMoreHasBg() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == getCount() - 1&&hasFooterView()) {// 最后一条
            // if (position < _data.size()) {
            // position = getCount() - 2; // footview
            // }
            if (getState() == STATE.LOAD_MORE || getState() == STATE.NO_MORE
                    || state == STATE.EMPTY_ITEM
                    || getState() == STATE.NETWORK_ERROR) {
                this.mFooterView = (LinearLayout) LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.list_cell_footer,
                        null);
                if (!loadMoreHasBg()) {
                    mFooterView.setBackgroundDrawable(null);
                }
                ProgressBar progress = (ProgressBar) mFooterView
                        .findViewById(R.id.progressbar);
                TextView text = (TextView) mFooterView.findViewById(R.id.text);
                switch (getState()) {
                    case LOAD_MORE:
                        setFooterViewLoading();
                        break;
                    case NO_MORE:
                        mFooterView.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                        text.setVisibility(View.VISIBLE);
                        text.setText(_loadFinishText);
                        break;
                    case EMPTY_ITEM:
                        progress.setVisibility(View.GONE);
                        mFooterView.setVisibility(View.VISIBLE);
                        text.setText(_noDateText);
                        break;
                    case NETWORK_ERROR:
                        mFooterView.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                        text.setVisibility(View.VISIBLE);
                        if (TDevice.hasInternet()) {
                            text.setText("加载出错了");
                        } else {
                            text.setText("没有可用的网络");
                        }
                        break;
                    default:
                        progress.setVisibility(View.GONE);
                        mFooterView.setVisibility(View.GONE);
                        text.setVisibility(View.GONE);
                        break;
                }
                return mFooterView;
            }
        }
        if (position < 0) {
            position = 0; // 若列表没有数据，是没有footview/headview的
        }
        return getRealView(position, convertView, parent);
    }

    protected View getRealView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    private LinearLayout mFooterView;

    private boolean hasFooterView() {
        return true;
    }

    public View getFooterView() {
        return this.mFooterView;
    }

    public void setFooterViewLoading(String loadMsg) {
        ProgressBar progress = (ProgressBar) mFooterView
                .findViewById(R.id.progressbar);
        TextView text = (TextView) mFooterView.findViewById(R.id.text);
        mFooterView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
        if (StringUtils.isEmpty(loadMsg)) {
            text.setText(_loadmoreText);
        } else {
            text.setText(loadMsg);
        }
    }

    public void setFooterViewLoading() {
        setFooterViewLoading("");
    }

    public void setFooterViewText(String msg) {
        ProgressBar progress = (ProgressBar) mFooterView
                .findViewById(R.id.progressbar);
        TextView text = (TextView) mFooterView.findViewById(R.id.text);
        mFooterView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);
        text.setText(msg);
    }

    protected void setText(TextView textView, String text, boolean needGone) {
        if (text == null || TextUtils.isEmpty(text)) {
            if (needGone) {
                textView.setVisibility(View.GONE);
            }
        } else {
            textView.setText(text);
        }
    }

    protected void setText(TextView textView, String text) {
        setText(textView, text, false);
    }
}
