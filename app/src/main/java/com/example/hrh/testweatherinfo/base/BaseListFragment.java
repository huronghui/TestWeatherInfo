package com.example.hrh.testweatherinfo.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hrh.testweatherinfo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hrh on 2015/11/6.
 */
public abstract class BaseListFragment<T extends Parcelable> extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
                              AdapterView.OnItemClickListener, AbsListView.OnScrollListener{

    @Bind(R.id.listview)
    public ListView mListview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwiperefreshlayout;

    protected int mCurrentPage = 0;
    protected ListBaseAdapter<T> mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pull_refresh_listview;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        InitHttpRequest();
        initView(view);
    }

    @Override
    public void initView(View view) {
        mSwiperefreshlayout.setOnRefreshListener(this);
        mSwiperefreshlayout.setColorSchemeColors(
                R.color.swiperefresh_color1, R.color.swiperefresh_color2,
                R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        mListview.setOnItemClickListener(this);
        mListview.setOnScrollListener(this);

        if(mAdapter != null) {
            mListview.setAdapter(mAdapter);
        } else {
            mAdapter = getListAdapter();
            mListview.setAdapter(mAdapter);
        }

        requestData(false);
    }

    protected abstract void InitHttpRequest();
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected abstract ListBaseAdapter<T> getListAdapter();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh() {
         if(mState == STATE.REFRESH) {
               return;
         }
        mListview.setSelection(0);
        setSwipRefreshLoadingState();
        mCurrentPage = 0;
        mState = STATE.REFRESH;
        requestData(true);
    }

    protected void setSwipRefreshLoadingState() {
        if(mSwiperefreshlayout != null) {
            mSwiperefreshlayout.setRefreshing(true);
            mSwiperefreshlayout.setEnabled(false);
        }
    }


    /**
     * 获取列表数据
     *
     *
     * @param refresh
     */

   protected void requestData(boolean refresh) {
        sendRequestData();
    }

    protected void sendRequestData() {}


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }

        //数据已全部加载，或数据为空时， 或正在加载时，不处理滚动事件
        if(mState == STATE.REFRESH || mState == STATE.LOADMORE) {
            return;
        }

        //判断是否滚动到底部

        boolean scrollEnd = false;
        try {
            if(view.getPositionForView(mAdapter.getFooterView()) == view.getLastVisiblePosition()) {
                scrollEnd = true;
            }
        } catch (Exception e) {
            scrollEnd = false;
        }

        if(mState == STATE.NONE && scrollEnd) {
            if(mAdapter.getState() == ListBaseAdapter.STATE.LOAD_MORE
                    || mAdapter.getState() == ListBaseAdapter.STATE.NETWORK_ERROR) {
                mCurrentPage ++;
                mState = STATE.LOADMORE;
                requestData(false);
                mAdapter.setFooterViewLoading();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
    }

