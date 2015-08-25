package util.datamanager.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import data.CityData;
import de.greendao.daoexample.City;
import de.greendao.daoexample.CityDao;
import de.greendao.daoexample.DaoMaster;
import de.greendao.daoexample.DaoSession;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by hrh on 2015/8/21.
 */
public class CityItemCache {
    private static final String TAG = CityItemCache.class.getSimpleName();
    private ArrayList<Long> mItemIdList = new ArrayList<Long>();
    private HashMap<Long, SoftReference<CityData>> itemMap = new HashMap<>();
    private Context mContext;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private CityDao cityDao;
    private SQLiteDatabase db;

    public CityItemCache(Context context) {
        mContext = context;
    }

    public CityData readCache(Long id) {
        Log.e(TAG,"+++++++++++++ readCache +++++++++++");
//        SoftReference<CityData> reference = itemMap.get(id);
//        CityData itembean = reference.get();
//        if(null == itembean) {
            City city = readDAO(id);
            CityData cityData = new CityData(city.getCityId(),city.getCityName(),null);
            itemMap.put(id, new SoftReference<CityData>(cityData));
            return cityData;
//        }
//        Log.e(TAG,"------------- readcache ----------");
//        return itembean;
    }

    private void initGreenDAO() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "City-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        cityDao = daoSession.getCityDao();
    }

    public boolean writeCache(CityData[] products) {
        Log.e(TAG,"+++++++++++++++++ writecache +++++++++++++++");
        initGreenDAO();
        for(int i = 0; i < products.length; i++) {
            CityData itembean = products[i];
            City daoObj = new City(null,itembean.getCityName(),null,itembean.getId());

            QueryBuilder qb = cityDao.queryBuilder();
            qb.where(CityDao.Properties.CityId.eq(itembean.getId()));
            List<City> resultList = qb.list();
            if (resultList.size() > 0) {
                daoObj.setId(resultList.get(0).getId());
                cityDao.update(daoObj);
            } else {
                cityDao.insert(daoObj);
            }


            itemMap.put(itembean.getId(),new SoftReference<CityData>(itembean));
            if(!mItemIdList.contains(itembean.getId())) {
                mItemIdList.add(itembean.getId());
            }
        }
        Log.e(TAG, "---------- writeCache ---------");
        return true;
    }

    private City readDAO(long itemId) {
        //        HuiItem data = huiItemDao.load(itemId);
        QueryBuilder qb = cityDao.queryBuilder();
        qb.where(CityDao.Properties.CityId.eq(itemId));
        List<City> resultList = qb.list();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public ArrayList<Long> getmItemIdList() {
        if(mItemIdList.size() == 0) {
            Log.e(TAG,"+++++++++++++++++ getmItemIdList +++++++++++++++");
            initGreenDAO();
            QueryBuilder qb = cityDao.queryBuilder();
//            qb.where(CityDao.Properties.CityId.eq(itembean.getId()));
            List<City> resultList = qb.list();
           for(int i = 0; i < resultList.size(); i++) {
               if(!mItemIdList.contains(resultList.get(i).getId())) {
                   mItemIdList.add(resultList.get(i).getId());
               }
           }
            Log.e(TAG, "---------- getmItemIdList ---------");
        }
        return mItemIdList;
    }

    public void clear() {
        mItemIdList.clear();
        itemMap.clear();
    }
}
