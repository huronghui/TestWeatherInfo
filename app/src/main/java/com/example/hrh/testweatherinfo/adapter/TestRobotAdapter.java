package com.example.hrh.testweatherinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.data.TestRobotBean.ChatMessage;
import com.example.hrh.testweatherinfo.view.TestRobotViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by hrh on 2015/11/26.
 */
public class TestRobotAdapter extends BaseAdapter{

    private  Context context;
    private List<ChatMessage> mDatas;
    private LayoutInflater layoutInflater;
   public TestRobotAdapter(Context context, List<ChatMessage> mDatas) {
        layoutInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;

    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = mDatas.get(position);
        if(chatMessage.getType() == ChatMessage.Type.INCOMING) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TestRobotViewHolder viewHolder = null;
        ChatMessage chatMessage = mDatas.get(position);
        if(convertView == null) {
            if(chatMessage.getType() == ChatMessage.Type.INCOMING) {
                convertView = layoutInflater.inflate(R.layout.item_from_msg, parent, false);
                viewHolder = new TestRobotViewHolder();
                viewHolder.mDate = (TextView) convertView.findViewById(R.id.id_from_msg_date);
                viewHolder.mMsg = (TextView) convertView.findViewById(R.id.id_from_msg_info);
            } else {
                convertView = layoutInflater.inflate(R.layout.item_to_msg, parent, false);
                viewHolder = new TestRobotViewHolder();
                viewHolder.mDate = (TextView) convertView.findViewById(R.id.id_to_msg_date);
                viewHolder.mMsg = (TextView) convertView.findViewById(R.id.id_to_msg_info);
            }
            convertView.setTag(viewHolder);
        }  else {
            viewHolder = (TestRobotViewHolder) convertView.getTag();
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.mDate.setText(df.format(chatMessage.getDate()));
        viewHolder.mMsg.setText(chatMessage.getMsg());
        return convertView;
    }


}
