package adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hrh.testweatherinfo.R;

import data.PrivinceCityData;
import util.datamanager.DataManager;
import view.PrivinceCityItemViewHolder;

/**
 * Created by hrh on 2015/8/22.
 */
public class PrivinceCityItemAdaper extends BaseAdapter{

    public static final String TAG = PrivinceCityItemAdaper.class.getSimpleName();
    private Activity mActivity;
    private DataManager mDatamansger;
    private LayoutInflater mInflater;

    public PrivinceCityItemAdaper(Activity activity, DataManager dataManager) {
        mInflater = LayoutInflater.from(activity);
        mActivity = activity;
        mDatamansger = dataManager;
    }

    @Override
    public int getCount() {
        int size = mDatamansger.getPrivinceCityItemIdList().size();
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
        PrivinceCityItemViewHolder holder;
        if(null == convertView) {
            convertView =mInflater.inflate(R.layout.item_city,null);
            holder = new PrivinceCityItemViewHolder();

            holder.itemName = (TextView)convertView.findViewById(R.id.city_textview_name);
            convertView.setTag(holder);
        }  else {
            holder = (PrivinceCityItemViewHolder)convertView.getTag();
        }

        Long itemid = mDatamansger.getPrivinceCityItemIdList().get(position);

        Log.e(TAG, "pos = " + position + " itemId = " + itemid);
        PrivinceCityData itembean = mDatamansger.getPrivinceCityItems(itemid);
        if(null != itembean) {
            holder.itemName.setText(itembean.getCountyName());
        }
        return convertView;
    }
}
