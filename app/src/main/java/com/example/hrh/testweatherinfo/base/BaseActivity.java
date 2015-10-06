package com.example.hrh.testweatherinfo.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hrh.testweatherinfo.R;

import org.kymjs.kjframe.utils.StringUtils;

import com.example.hrh.testweatherinfo.interf.BaseViewInterface;

/**
 * Created by hrh on 2015/10/5.
 */
public abstract class BaseActivity extends ActionBarActivity implements BaseViewInterface{

    protected ActionBar mActionBar;
    private TextView mTvActionTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }

        mActionBar = getSupportActionBar();
        if(hasActionBar()) {
            initActionBar(mActionBar);
        }

        init(savedInstanceState);
        initView();
        initData();
    }

    protected void init(Bundle savedInstanceState) {}

    protected int getLayoutId() {
        return 0;
    }

    protected boolean hasActionBar() {
        return true;
    }

    private void initActionBar(ActionBar Actionbar) {
        if(Actionbar == null) {
            return;
        }
        if(haseBackButton()) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
        } else {
            Actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
            Actionbar.setDisplayUseLogoEnabled(false);
            int titleRes = getActionBarTitle();
            if(titleRes != 0 ) {
                Actionbar.setTitle(titleRes);
            }
        }
    }

    protected boolean haseBackButton() {
        return false;
    }

    private int getActionBarTitle() {
        return R.string.app_name;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setActionBarTitle(int resId) {
        if (resId != 0) {
            setActionBarTitle(getString(resId));
        }
    }

    public void setActionBarTitle(String title) {
        if (StringUtils.isEmpty(title)) {
            title = getString(R.string.app_name);
        }
        if (hasActionBar() && mActionBar != null) {
            if (mTvActionTitle != null) {
                mTvActionTitle.setText(title);
            }
            mActionBar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
