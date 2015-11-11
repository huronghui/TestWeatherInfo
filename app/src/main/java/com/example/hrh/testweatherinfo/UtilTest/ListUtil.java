package com.example.hrh.testweatherinfo.UtilTest;

import android.os.Parcelable;

import com.example.hrh.testweatherinfo.data.imoocInfo.ImoocInfoListDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hrh on 2015/11/11.
 */
public class ListUtil<T> {
    /**
     * 将一个List类型的数据转换为Arraylist格式数据
     */

    public ArrayList<T> listToAarraylist(List<T> bean) {
        ArrayList<T> arrayList = new ArrayList<T>();
        for (int i = 0; i < bean.size(); i++ ){
            arrayList.add( bean.get(i) ) ;
        }
        return arrayList;
    }
}
