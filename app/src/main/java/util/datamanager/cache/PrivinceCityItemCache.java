package util.datamanager.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import data.PrivinceCityData;
import de.greendao.daoexample.DaoMaster;
import de.greendao.daoexample.DaoSession;
import de.greendao.daoexample.PrivinceCity;
import de.greendao.daoexample.PrivinceCityDao;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by hrh on 2015/8/22.
 */
public class PrivinceCityItemCache {

    public static final String TAG = PrivinceCityData.class.getSimpleName();
    private ArrayList<Long> listItem = new ArrayList<Long>();
    private HashMap<Long, SoftReference<PrivinceCityData>> cacheItem = new HashMap<Long, SoftReference<PrivinceCityData>>();
    private Context mContext;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private PrivinceCityDao privinceCityDao;
    private SQLiteDatabase db;

    public PrivinceCityItemCache( Context context) {
        mContext = context;
    }

    public PrivinceCityData readCache(Long id) {
        Log.e(TAG, "+++++++++++++ readCache +++++++++++");
        SoftReference<PrivinceCityData> reference = cacheItem.get(id);
        PrivinceCityData itembean = reference.get();
        if(null == itembean) {
            PrivinceCity daoobj = readDAO(id);
            itembean = new PrivinceCityData(daoobj.getCityId(),daoobj.getCountyName(),daoobj.getCountyCode());
            cacheItem.put(id, new SoftReference<PrivinceCityData>(itembean));

        }
        Log.e(TAG,"------------- readcache ----------");
        return itembean;
    }

    public Boolean writeCache(PrivinceCityData[] products) {
        Log.e(TAG,"+++++++++++++++++ writecache +++++++++++++++");
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "weather-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        privinceCityDao = daoSession.getPrivinceCityDao();
        for(int i = 0; i < products.length; i++) {

            PrivinceCityData itembean = products[i];

            PrivinceCity daoObj = new PrivinceCity(null,itembean.getCountyName(),null,itembean.getId());

            QueryBuilder qb = privinceCityDao.queryBuilder();
            qb.where(PrivinceCityDao.Properties.CityId.eq(itembean.getId()));
            List<PrivinceCity> resultList = qb.list();
            if (resultList.size() > 0) {
                daoObj.setId(resultList.get(0).getId());
                privinceCityDao.update(daoObj);
            } else {
                privinceCityDao.insert(daoObj);
            }

            cacheItem.put(itembean.getId(),new SoftReference<PrivinceCityData>(itembean));
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

    private PrivinceCity readDAO(long itemId) {
        //        HuiItem data = huiItemDao.load(itemId);
        QueryBuilder qb = privinceCityDao.queryBuilder();
        qb.where(PrivinceCityDao.Properties.CityId.eq(itemId));
        List<PrivinceCity> resultList = qb.list();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public ArrayList<Long> getmItemIdList() {
        return listItem;
    }

    public void clear() {
        cacheItem.clear();
        listItem.clear();
    }
}
