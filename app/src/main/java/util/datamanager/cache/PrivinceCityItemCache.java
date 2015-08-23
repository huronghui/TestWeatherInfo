package util.datamanager.cache;

import android.content.Context;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import data.PrivinceCity;

/**
 * Created by hrh on 2015/8/22.
 */
public class PrivinceCityItemCache {

    public static final String TAG = PrivinceCity.class.getSimpleName();
    private ArrayList<Long> listItem = new ArrayList<Long>();
    private HashMap<Long, SoftReference<PrivinceCity>> cacheItem = new HashMap<Long, SoftReference<PrivinceCity>>();
    private Context mContext;

    public PrivinceCityItemCache( Context context) {
        mContext = context;
    }

    public PrivinceCity readCache(Long id) {
        Log.e(TAG, "+++++++++++++ readCache +++++++++++");
        SoftReference<PrivinceCity> reference = cacheItem.get(id);
        PrivinceCity itembean = reference.get();
        Log.e(TAG,"------------- readcache ----------");
        return itembean;
    }

    public Boolean writeCache(PrivinceCity[] products) {
        Log.e(TAG,"+++++++++++++++++ writecache +++++++++++++++");
        for(int i = 0; i < products.length; i++) {

            PrivinceCity itembean = products[i];
            cacheItem.put(itembean.getId(),new SoftReference<PrivinceCity>(itembean));
            if(!listItem.contains(itembean.getId())) {
                listItem.add(itembean.getId());
            }
        }
        Log.e(TAG, "---------- writeCache ---------");
        return true;
    }

    public int getSize() {
        int size = listItem.size();
        return  size;
    }

    public ArrayList<Long> getmItemIdList() {
        return listItem;
    }

    public void clear() {
        cacheItem.clear();
        listItem.clear();
    }
}
