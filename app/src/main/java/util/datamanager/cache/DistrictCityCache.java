package util.datamanager.cache;

import android.content.Context;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import data.DistrictCity;


/**
 * Created by hrh on 2015/8/22.
 */
public class DistrictCityCache {
    public static final String TAG = DistrictCity.class.getSimpleName();
    private ArrayList<Long> itemlist = new ArrayList<Long>();
    private HashMap<Long, WeakReference<DistrictCity>> itemMap = new HashMap<>();
    private Context mContext;

    public DistrictCityCache(Context context) {
        mContext = context;
    }

    public DistrictCity readCache(Long id) {
        Log.e(TAG, "+++++++++++++ readCache +++++++++++");
        WeakReference<DistrictCity> reference = itemMap.get(id);
        DistrictCity itembean = reference.get();
        Log.e(TAG,"------------- readcache ----------");
        return itembean;
    }

    public boolean writecache(DistrictCity[] products) {
        Log.e(TAG, "+++++++++++++ writeCache +++++++++++");
        for(int i = 0; i < products.length; i++) {
            DistrictCity itembean = products[i];
            itemMap.put(itembean.getId(),new WeakReference<DistrictCity>(itembean));

            if(!itemlist.contains(itembean.getId()))
                itemlist.add(itembean.getId());
        }
        Log.e(TAG,"------------- writeCache ----------");
        return true;
    }

    public ArrayList<Long> getmItemIdList() {
        return itemlist;
    }

    public void clear() {
        itemlist.clear();
        itemMap.clear();
    }
}
