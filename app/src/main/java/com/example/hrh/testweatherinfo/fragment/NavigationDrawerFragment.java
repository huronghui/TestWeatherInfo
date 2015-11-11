package com.example.hrh.testweatherinfo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.UtilTest.UIHelper;
import com.example.hrh.testweatherinfo.activity.AsyncTaskImageLoaderActivity;
import com.example.hrh.testweatherinfo.base.BaseApplication;
import com.example.hrh.testweatherinfo.data.SimpleBackPage;

/**
 * Created by hrh on 2015/10/4.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * ���ѡ��item��λ��
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * ����û��Ƿ���ҪĬ�Ͽ���drawer��key
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * ����activityʵ�ֵĻص��ӿڵ�����
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * ��action bar��drawerlayout�󶨵����
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private List<DrawerListItem> mData = new ArrayList<DrawerListItem>();

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ͨ�����flag�ж��û��Ƿ��Ѿ�֪��drawer�ˣ���һ������Ӧ����ʾ��drawer�����룩��֮������Ӧ��Ĭ�Ͻ�������
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // ���ø�fragmentӵ���Լ���actionbar action item
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        View headerView = inflater.inflate(R.layout.list_header, null);
        mDrawerListView.addHeaderView(headerView);

        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BaseApplication.showToast(position + "");
                selectItem(position);
                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.TESTFRAGMENTACTIVITY);
            }
        });

        String[] itemTitle = getResources().getStringArray(R.array.item_title);
        int[] itemIconRes = {
                R.drawable.ic_drawer_home,
                R.drawable.ic_drawer_explore,
                R.drawable.ic_drawer_follow,
                R.drawable.ic_drawer_collect,
                R.drawable.ic_drawer_draft,
                R.drawable.ic_drawer_search,
                R.drawable.ic_drawer_question,
                R.drawable.ic_drawer_setting};

        for (int i = 0; i < itemTitle.length; i++) {
            DrawerListItem item = new DrawerListItem(getResources().getDrawable(itemIconRes[i]), itemTitle[i]);
            mData.add(item);

        }
        selectItem(mCurrentSelectedPosition);
        DrawerListAdapter adapter = new DrawerListAdapter(this.getActivity(), mData);
        mDrawerListView.setAdapter(adapter);
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        return mDrawerListView;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * ���õ���drawer
     *
     * @param fragmentId   fragmentent��id
     * @param drawerLayout fragment������
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        //����Action bar�ϵ�app icon
        actionBar.setDisplayShowHomeEnabled(false);

        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* ���� */
                mDrawerLayout,                    /* DrawerLayout ���� */
              //  R.drawable.ic_drawer,             /* �滻actionbar�ϵ�'Up'ͼ�� */
                null,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // ���� onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).commit();
                }

                getActivity().supportInvalidateOptionsMenu(); // ���� onPrepareOptionsMenu()
            }
        };

        // ����ǵ�һ�ν���Ӧ�ã���ʾ����
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            if(mCurrentSelectedPosition == 0) {
                mCallbacks.onNavigationDrawerItemSelected(getString(R.string.app_name));
                return;
            }
            mCallbacks.onNavigationDrawerItemSelected(mData.get(position - 1).getTitle());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // ��ϵͳ���øı�ʱ����DrawerToggle�ĸı����÷���������������л���ص��˷�����
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //�������ʱ��ʾӦ��ȫ�ֵ�actionbar����
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.action_example) {
            Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * �������ʱ��ʾӦ��ȫ�ֵ�actionbar����
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    /**
     * ����activityҪʵ�ֵĻص��ӿ�
     * ����activity���fragment֮��ͨѶ
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * ��drawer�е�ĳ��item��ѡ���ǵ��ø÷���
         */
        void onNavigationDrawerItemSelected(String title);
    }

}
