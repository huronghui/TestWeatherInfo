package util.datamanager.cache;

import android.content.Context;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;

import adapter.CityItemAdaper;
import data.City;

/**
 * Created by hrh on 2015/8/21.
 */
public class CityItemCache {
    private static final String TAG = CityItemCache.class.getSimpleName();
    private ArrayList<Long> mItemIdList = new ArrayList<Long>();
    private HashMap<Long, SoftReference<City>> itemMap = new HashMap<>();
    private Context mContext;

    public CityItemCache(Context context) {
        mContext = context;
    }

    public City readCache(Long id) {
        Log.e(TAG,"+++++++++++++ readCache +++++++++++");
        SoftReference<City> reference = itemMap.get(id);
        City itembean = reference.get();
        Log.e(TAG,"------------- readcache ----------");
        return itembean;
    }

    public boolean writeCache(City[] products) {
        Log.e(TAG,"+++++++++++++++++ writecache +++++++++++++++");
        for(int i = 0; i < products.length; i++) {
            City itembean = products[i];
            itemMap.put(itembean.getId(),new SoftReference<City>(itembean));
            if(!mItemIdList.contains(itembean.getId())) {
                mItemIdList.add(itembean.getId());
            }
        }
        Log.e(TAG, "---------- writeCache ---------");
        return true;
    }

    public ArrayList<Long> getmItemIdList() {
        return mItemIdList;
    }

    public void clear() {
        mItemIdList.clear();
        itemMap.clear();
    }
}
