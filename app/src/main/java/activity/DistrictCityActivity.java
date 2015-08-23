package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hrh.testweatherinfo.ApplicationData;
import com.example.hrh.testweatherinfo.Define;
import com.example.hrh.testweatherinfo.R;

import adapter.DistrictCityAdapter;
import adapter.PrivinceCityItemAdaper;
import data.DistrictCity;
import network.DistrictCityHttpRequest;
import util.datamanager.DataManager;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;

/**
 * Created by hrh on 2015/8/22.
 */
public class DistrictCityActivity extends Activity{

    protected static final String TAG = DistrictCityActivity.class.getSimpleName();
    protected ApplicationData mAppData;
    protected DataManager mDataManager;
    private Long remoteId;
    private ZrcListView mListView;
    private DistrictCityAdapter mAdapter;
    private DistrictCityHttpRequest mDistrictCityHttpRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAppData = (ApplicationData)getApplication();
        remoteId  = getIntent().getLongExtra("remoteId",-1);
        mDataManager = ((ApplicationData) this.getApplication()).getDataManager();
        initZrclistview();

        mDistrictCityHttpRequest = new DistrictCityHttpRequest(this);
        mDistrictCityHttpRequest.setDistrictCityStringHttpRequestListener(mDistrictCityStringHttpRequest);
        mListView.refresh();

    }

    private void initZrclistview() {
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

        mDataManager.clearDistrictCity();
        mListView.refresh();
        mAdapter = new DistrictCityAdapter(this, mDataManager);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(mOnItemClickListener);
    }


    private ZrcListView.OnItemClickListener mOnItemClickListener = new ZrcListView.OnItemClickListener() {

        @Override
        public void onItemClick(ZrcListView parent, View view, int position, long id) {

            Intent intent = new Intent(DistrictCityActivity.this, CityWeatherActivity.class);
            intent.putExtra("remoteId", mDataManager.getDistrictCityItemList().get(position));
            startActivity(intent);
        }
    };
    protected void refresh() {
        String id = remoteId+"";
        Log.e(TAG, "id = " + id + "ddd");
        if((id.length() % 2) != 0 ) {
            id = "0"+id;
        }
        Log.e(TAG, "id = " + id);
        mDistrictCityHttpRequest.StringRequest(Define.PRIVINCE_CITY_PATH + id + ".xml");
    }

    protected void loadMore() {

    }

    public DistrictCityHttpRequest.OnDistrictCityStringHttpListener mDistrictCityStringHttpRequest = new DistrictCityHttpRequest.OnDistrictCityStringHttpListener() {
        @Override
        public void onSucced(DistrictCity[] response) {
            Log.e(TAG, "response = " + response);
            mDataManager.setDistrictCityItems(response);
            mAdapter.notifyDataSetChanged();
            mListView.setRefreshSuccess("");
        }

        @Override
        public void onError(int reason) {

        }
    };
}
