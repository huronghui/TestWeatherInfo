package com.example.hrh.testweatherinfo.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.UtilTest.KJLoger;
import com.example.hrh.testweatherinfo.base.BaseActivity;
import com.example.hrh.testweatherinfo.fragment.TestListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hrh on 2015/11/11.
 */
public class TestFragmentActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_testfragment;
    }


    @Override
    public void initView() {
        ButterKnife.bind(this);
//        Button button = (Button) findViewById(R.id.bt);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                KJLoger.debug("TEST");
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.
//                        beginTransaction();
//                TestListFragment fragment = new TestListFragment();
//                //�ӵ�Activity��
//                fragmentTransaction.add(R.id.lv_fragment_container, fragment);
//                //�ӵ���̨��ջ�У�����һ�����Ļ���������ذ�ť���˵�Activity���棬û�еĻ���ֱ���˳�Activity
//                //����Ĳ����Ǵ�Fragment��Tag���൱��id
//                fragmentTransaction.addToBackStack("fragment1");
//                //��ס�ύ
//                fragmentTransaction.commit();
//            }
//        });
    }

    @Override
    public void initData() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
