package util.datamanager.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import data.CityData;
import data.WeatherBean.Cityweatherbean;
import de.greendao.daoexample.City;
import de.greendao.daoexample.CityDao;
import de.greendao.daoexample.DaoMaster;
import de.greendao.daoexample.DaoSession;
import de.greendao.daoexample.Weather;
import de.greendao.daoexample.WeatherDao;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by hrh on 2015/8/29.
 */
public class CityWeatherInfoCache {
    private static final String TAG = CityWeatherInfoCache.class.getSimpleName();
    private HashMap<String, Cityweatherbean> mCityWeatherInfoCache = new HashMap<String, Cityweatherbean>();
    SQLiteDatabase db;
    DaoMaster daoMaster;
    DaoSession daoSession;
    private WeatherDao mWeatherDao;
    private Context mContext;

    public CityWeatherInfoCache(Context context) {
        this.mContext = context;
    }

    public Cityweatherbean readCache(String id) {
        Log.e(TAG,"-------------- read cache ------------------");
        Cityweatherbean bean = mCityWeatherInfoCache.get(id);
        Weather weather = ReadDao(id);
        Cityweatherbean cityweatherbean = new Cityweatherbean();
        Log.e(TAG,"++++++++++++++ read cache ++++++++++++++++++");
        return bean;
    }

    private Weather ReadDao(String id) {
        QueryBuilder qb = mWeatherDao.queryBuilder();
        qb.where(WeatherDao.Properties.Cityid.eq(id));
        List<Weather> resultList = qb.list();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    public void writeCache(Cityweatherbean cityweatherbean) {
        Log.e(TAG, "-------------- write cache ------------------");
        initGreenDao();
        Weather daoObj = new Weather(null,cityweatherbean.getCity(),cityweatherbean.getCityId(),cityweatherbean.getTemp1(),
                                           cityweatherbean.getTemp2(),cityweatherbean.getWeather(),cityweatherbean.getPtime());

        QueryBuilder qb = mWeatherDao.queryBuilder();
        qb.where(WeatherDao.Properties.Cityid.eq(cityweatherbean.getCityId()));
        List<Weather> resultList = qb.list();
        if (resultList.size() > 0) {
            daoObj.setId(resultList.get(0).getId());
            mWeatherDao.update(daoObj);
        } else {
            mWeatherDao.insert(daoObj);
        }
        mCityWeatherInfoCache.put(cityweatherbean.getCityId(), cityweatherbean);
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "Weather-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        mWeatherDao = daoSession.getWeatherDao();
    }

    public void clear() {
        mCityWeatherInfoCache.clear();
    }
}
