package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.Define;
import com.example.hrh.testweatherinfo.R;

import adapter.PrivinceCityItemAdaper;
import data.PrivinceCity;
import network.PrivinceCityHttpRequest;
import util.datamanager.DataManager;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcAbsListView;
import zrc.widget.ZrcListView;

/**
 * Created by hrh on 2015/8/22.
 */
public class ProvinceCity extends Activity {

    public static final String TAG = PrivinceCity.class.getSimpleName();
    private ZrcListView mListView;
    private ApplicationData mAppData;
    private DataManager mDataManager;
    private PrivinceCityItemAdaper mAdapter;
    private PrivinceCityHttpRequest mPrivinceCityHttpRequest;
    private int mListViewItemPosition = 0;
    private Long remoteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAppData = (ApplicationData)getApplication();
        remoteId  = getIntent().getLongExtra("remoteId",-1);
        mDataManager = ((ApplicationData) this.getApplication()).getDataManager();

        initZrcvlistview();
        mPrivinceCityHttpRequest = new PrivinceCityHttpRequest(this);
        mPrivinceCityHttpRequest.setmProvincesStringHttpRequestListener(mPrivinceCityHttpRequestListener);
        mListView.refresh();
        mListView.setOnScrollListener(mOnScrollListener);
    }

    private void initZrcvlistview() {
        mListView = (ZrcListView)findViewById(R.id.zListView);
        mListView.setFirstTopOffset((int) this.getResources().getDimension(R.dimen.top_offset));

        SimpleHeader header = new SimpleHeader(this);
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        mListView.setHeadable(header);

        SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(0xff33bbee);
        mListView.setFootable(footer);

        mListView.setItemAnimForTopIn(R.anim.topitem_in);
        mListView.setItemAnimForBottomIn(R.anim.bottomitem_in);

        mListView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        mListView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });

        mDataManager.clearPrivinceItem();
        mListView.refresh();
        mAdapter = new PrivinceCityItemAdaper(this, mDataManager);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    private ZrcListView.OnItemClickListener mOnItemClickListener = new ZrcListView.OnItemClickListener() {

        @Override
        public void onItemClick(ZrcListView parent, View view, int position, long id) {

            Intent intent = new Intent(ProvinceCity.this, DistrictCityActivity.class);
            intent.putExtra("remoteId", mDataManager.getPrivinceCityItemIdList().get(position));
            startActivity(intent);
        }
    };

    private void refresh() {
        String id = remoteId+"";
        if(remoteId < 10) {
            id = "0"+id;
        }
       mPrivinceCityHttpRequest.StringRequest(Define.PRIVINCE_CITY_PATH + id + ".xml");
    }

    private void loadMore() {

    }

    private ZrcListView.OnScrollListener mOnScrollListener = new ZrcListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(ZrcAbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(ZrcAbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            Log.e(TAG, "firstVisibleItem = " + firstVisibleItem + " visibleItemCount = " + visibleItemCount);
            mListViewItemPosition = firstVisibleItem;
        }
    };

    public PrivinceCityHttpRequest.onPrivinceCityHttpRequestListener mPrivinceCityHttpRequestListener = new PrivinceCityHttpRequest.onPrivinceCityHttpRequestListener() {
        @Override
        public void onSucced(PrivinceCity[] response) {
            Log.e(TAG,"response = " + response);
            mDataManager.setPrivinceCityItems(response);
            mAdapter.notifyDataSetChanged();
            mListView.setRefreshSuccess("");
        }

        @Override
        public void onError(int reason) {

        }
    };
}

