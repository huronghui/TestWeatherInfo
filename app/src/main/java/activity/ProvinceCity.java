package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.Define;
import com.example.hrh.testweatherinfo.R;

import java.util.Timer;
import java.util.TimerTask;

import adapter.PrivinceCityItemAdaper;
import data.PrivinceCityData;
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

    public static final String TAG = PrivinceCityData.class.getSimpleName();
    private ZrcListView mListView;
    private ApplicationData mAppData;
    private DataManager mDataManager;
    private PrivinceCityItemAdaper mAdapter;
    private PrivinceCityHttpRequest mPrivinceCityHttpRequest;
    private int mListViewItemPosition = 0;
    private Long remoteId;
    private int TIME = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAppData = (ApplicationData)getApplication();
        remoteId  = getIntent().getLongExtra("remoteId",-1);
        mDataManager = mAppData.getDataManager();

        initZrcvlistview();

        mPrivinceCityHttpRequest = new PrivinceCityHttpRequest(this);
        mPrivinceCityHttpRequest.setmProvincesStringHttpRequestListener(mPrivinceCityHttpRequestListener);
        mListView.refresh();
        mListView.setOnScrollListener(mOnScrollListener);
        timer.schedule(task, TIME, TIME);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                Toast.makeText(ProvinceCity.this, "Hello World again", Toast.LENGTH_LONG).show();
            }
            super.handleMessage(msg);
        };
    };

    Timer timer = new Timer();

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

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

//        mDataManager.clearPrivinceItem();
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
        public void onSucced(PrivinceCityData[] response) {
            Log.e(TAG,"response = " + response);
            mDataManager.setPrivinceCityItems(response);
            mAdapter.notifyDataSetChanged();
            mListView.setRefreshSuccess("");
        }

        @Override
        public void onError(int reason) {
            mAdapter.notifyDataSetChanged();
            mListView.setRefreshSuccess("");
//            mListView.stopLoadMore();
        }
    };
}

