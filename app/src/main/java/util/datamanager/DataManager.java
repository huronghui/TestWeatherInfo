package util.datamanager;

import android.content.Context;

import java.util.ArrayList;

import data.CityData;
import data.DistrictCity;
import data.PrivinceCityData;
import util.datamanager.cache.CityItemCache;
import util.datamanager.cache.DistrictCityCache;
import util.datamanager.cache.PrivinceCityItemCache;

/**
 * Created by hrh on 2015/8/21.
 */
public class DataManager {
    private Context mContext;
    private CityItemCache mCityItemCache;
    private PrivinceCityItemCache mPrivinceCityItemCache;
    private DistrictCityCache mDistrictCityCache;

    public DataManager(Context context) {
        mContext = context;
        mCityItemCache = new CityItemCache(mContext);
        mPrivinceCityItemCache = new PrivinceCityItemCache(mContext);
        mDistrictCityCache = new DistrictCityCache(mContext);
    }

    /*
    * CityData Info
     */

    public CityData getCityItems(Long id) {
        return mCityItemCache.readCache(id);
    }

    public boolean setCityItems(CityData[]  products) {
        return mCityItemCache.writeCache(products);
    }

    public void clearCityItem() {
        mCityItemCache.clear();
    }

    public ArrayList<Long> getCityItemIdList() {
        return mCityItemCache.getmItemIdList();
    }

    /*
    * Privince city info
     */
    public PrivinceCityData getPrivinceCityItems(Long id) {
        return mPrivinceCityItemCache.readCache(id);
    }

    public  boolean setPrivinceCityItems(PrivinceCityData[] products) {
        return mPrivinceCityItemCache.writeCache(products);
    }

    public void clearPrivinceItem() {
        mPrivinceCityItemCache.clear();
    }

    public ArrayList<Long> getPrivinceCityItemIdList() {
        return mPrivinceCityItemCache.getmItemIdList();
    }

    /*
    *DistrictCity city info
     */
    public DistrictCity getDistrictCityItems(Long id) {
        return mDistrictCityCache.readCache(id);
    }

    public boolean setDistrictCityItems(DistrictCity[] products) {
        return mDistrictCityCache.writecache(products);
    }

    public void clearDistrictCity() {
        mDistrictCityCache.clear();
    }

    public ArrayList<Long> getDistrictCityItemList() {
        return mDistrictCityCache.getmItemIdList();
    }
}
