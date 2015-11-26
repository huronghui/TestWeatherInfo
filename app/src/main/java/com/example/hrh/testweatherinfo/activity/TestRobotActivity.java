package com.example.hrh.testweatherinfo.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;

import com.example.hrh.testweatherinfo.Define;
import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.UtilTest.KJLoger;
import com.example.hrh.testweatherinfo.adapter.TestRobotAdapter;
import com.example.hrh.testweatherinfo.base.BaseActivity;
import com.example.hrh.testweatherinfo.data.TestRobotBean.ChatMessage;

import com.example.hrh.testweatherinfo.network.TestRobotHttpRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hrh on 2015/11/26.
 */
public class TestRobotActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.id_sengd_msg)
    Button idSengdMsg;
    @Bind(R.id.id_input_msg)
    EditText idInputMsg;
    @Bind(R.id.id_listview_msgs)
    ListView idListviewMsgs;


    private TestRobotAdapter testRobotAdapter;
    private List<ChatMessage> mDatas;

    private TestRobotHttpRequest testRobotHttpRequest;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_testrobot;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        idSengdMsg.setOnClickListener(this);
    }

    @Override
    public void initData() {
        testRobotHttpRequest = new TestRobotHttpRequest(this);
        testRobotHttpRequest.setmImoocInfoHttpRequestListtener(testRobotHttpRequestResponse);
        mDatas = new ArrayList<>();
        mDatas.add(new ChatMessage("hello welcome", ChatMessage.Type.INCOMING, new Date()));
        testRobotAdapter = new TestRobotAdapter(this,mDatas);
        idListviewMsgs.setAdapter(testRobotAdapter);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_sengd_msg:
                String toMsg = idInputMsg.getText().toString();
                if(TextUtils.isEmpty(toMsg)) {
                    Toast.makeText(TestRobotActivity.this, "发送消息不能为空！",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                ChatMessage toMessage = new ChatMessage();
                toMessage.setDate(new Date());
                toMessage.setMsg(toMsg);
                toMessage.setType(ChatMessage.Type.OUTCOMING);
                mDatas.add(toMessage);
                testRobotAdapter.notifyDataSetChanged();
                idListviewMsgs.setSelection(mDatas.size() - 1);
                idInputMsg.setText("");
                try {
                    testRobotHttpRequest.add(Request.Method.GET, Define.TESTROBOTURL + URLEncoder.encode(toMsg, "utf8"), null);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    public TestRobotHttpRequest.TestRobotHttpRequestResponse testRobotHttpRequestResponse =
            new TestRobotHttpRequest.TestRobotHttpRequestResponse() {
        @Override
        public void onSuccess(String msg) {
            KJLoger.debug(msg);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMsg(msg);
            chatMessage.setDate(new Date());
            chatMessage.setType(ChatMessage.Type.INCOMING);
            mDatas.add(chatMessage);
            testRobotAdapter.notifyDataSetChanged();
            idListviewMsgs.setSelection(mDatas.size() - 1);
        }

        @Override
        public void onError(int reason) {

        }
    };
}
