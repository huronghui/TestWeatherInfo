package adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hrh.testweatherinfo.R;

import data.DistrictCity;
import util.datamanager.DataManager;
import view.CityItemViewHolder;
import view.DistrictCityViewHolder;

/**
 * Created by hrh on 2015/8/22.
 */
public class DistrictCityAdapter extends BaseAdapter {

    private static final String TAG = DistrictCityAdapter.class.getSimpleName();
    private DataManager mDataManager;
    private LayoutInflater mInflater;
    private Activity mActivity;

    public DistrictCityAdapter(Activity activity, DataManager dataManager) {
        this.mInflater = activity.getLayoutInflater();
        mActivity = activity;
        mDataManager = dataManager;
    }
    @Override
    public int getCount() {
        int size = mDataManager.getDistrictCityItemList().size();
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

        Long itemId = mDataManager.getDistrictCityItemList().get(position);
        Log.e("Adapter", "pos = " + position + " itemId = " + itemId);

        DistrictCity item = mDataManager.getDistrictCityItems(itemId);
        if(null != item) {
            holder.itemName.setText(item.getCityName());
        }
        return convertView;

    }
}
