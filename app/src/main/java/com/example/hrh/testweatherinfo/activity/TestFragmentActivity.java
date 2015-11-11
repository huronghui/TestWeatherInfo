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
//                //加到Activity中
//                fragmentTransaction.add(R.id.lv_fragment_container, fragment);
//                //加到后台堆栈中，有下一句代码的话，点击返回按钮是退到Activity界面，没有的话，直接退出Activity
//                //后面的参数是此Fragment的Tag。相当于id
//                fragmentTransaction.addToBackStack("fragment1");
//                //记住提交
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
