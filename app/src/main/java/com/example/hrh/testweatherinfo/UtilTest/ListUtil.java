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
     * ��һ��List���͵�����ת��ΪArraylist��ʽ����
     */

    public ArrayList<T> listToAarraylist(List<T> bean) {
        ArrayList<T> arrayList = new ArrayList<T>();
        for (int i = 0; i < bean.size(); i++ ){
            arrayList.add( bean.get(i) ) ;
        }
        return arrayList;
    }
}
