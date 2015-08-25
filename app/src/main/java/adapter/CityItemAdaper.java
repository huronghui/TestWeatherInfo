package adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hrh.testweatherinfo.R;

import data.CityData;
import util.datamanager.DataManager;
import view.CityItemViewHolder;

/**
 * Created by hrh on 2015/8/21.
 */
public class CityItemAdaper extends BaseAdapter{

    private static final String TAG = CityItemAdaper.class.getSimpleName();
    private LayoutInflater mInflater;       // 得到一个layoutinfalter对象来导入布局
    private Activity mActivty;
    private DataManager mDataManager;
    //构造函数
    public CityItemAdaper(Activity activity, DataManager dataManager) {
        this.mInflater = activity.getLayoutInflater();
        mDataManager = dataManager;
        mActivty = activity;
    }

    @Override
    public int getCount() {
        int size = mDataManager.getCityItemIdList().size();
        Log.e(TAG, "size = " + size);
        return size;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CityItemViewHolder holder;

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_city,null);
            holder = new CityItemViewHolder();

            holder.itemName = (TextView)convertView.findViewById(R.id.city_textview_name);
            convertView.setTag(holder);
        }  else {
            holder = (CityItemViewHolder)convertView.getTag();
        }

        Long itemId = mDataManager.getCityItemIdList().get(position);
        Log.e("Adapter", "pos = " + position + " itemId = " + itemId);

        CityData item = mDataManager.getCityItems(itemId);
        if(null != item) {
            holder.itemName.setText(item.getCityName());
        }
        return convertView;
    }
}
