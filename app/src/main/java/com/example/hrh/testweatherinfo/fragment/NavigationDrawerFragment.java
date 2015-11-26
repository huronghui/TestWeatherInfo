package com.example.hrh.testweatherinfo.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.TextView;

import com.example.hrh.testweatherinfo.R;
import com.example.hrh.testweatherinfo.UtilTest.UIHelper;
import com.example.hrh.testweatherinfo.base.BaseFragment;
import com.example.hrh.testweatherinfo.data.SimpleBackPage;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hrh on 2015/10/4.
 */
public class NavigationDrawerFragment extends BaseFragment implements View.OnClickListener {

    /**
     * ��סѡ�е�item��λ��
     */

    private static final String STATE_SELECTION_POSITION = "selected_navigation_drawer_position";

    /**
     * ����û��Ƿ���ҪĬ�Ͽ���drawer��key
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * ����activity�Ļص��ӿ�
     */
    private NavigationDrawerCallbacks mCallback;

    /**
     * �����ݹ�����actionbar��
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private View mDrawerListView;
    private View mFragmentContentView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSaveInstanceState;
    private boolean mUserLearnedDrawer;

    @Bind(R.id.menu_item_quests)
    View mMenu_Item_Quests;
    @Bind(R.id.menu_item_opensoft)
    View mMenu_Item_Opensoft;
    @Bind(R.id.menu_item_blog)
    View mMenu_Item_Blog;
    @Bind(R.id.menu_item_gitapp)
    View mMenu_Item_Gitapp;
    @Bind(R.id.menu_item_setting)
    View mMenu_Item_Setting;
    @Bind(R.id.menu_item_theme)
    View mMenu_Item_Theme;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ͨ�����flag�ж��û��Ƿ��Ѿ�֪��drawer�ˣ���һ������Ӧ����ʾ��drawer�����룩��֮������Ӧ��Ĭ�Ͻ�������
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTION_POSITION);
            mFromSaveInstanceState = true;
        }

        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDrawerListView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView.setOnClickListener(this);
        ButterKnife.bind(this, mDrawerListView);
        initView(mDrawerListView);
        initData();
        return mDrawerListView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.menu_item_quests:
                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.TESTFRAGMENTACTIVITY);
                getActivity().finish();
                break;
            case R.id.menu_item_opensoft:
                UIHelper.showSimpleBack(getActivity(), SimpleBackPage.TESTROBOT);
                getActivity().finish();
                break;
            case R.id.menu_item_blog:
                break;
            case R.id.menu_item_gitapp:
                break;
            case R.id.menu_item_setting:
                break;
            case R.id.menu_item_theme:
                break;
            default:
                break;
        }

        mDrawerListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDrawerLayout.closeDrawers();
            }
        }, 800);
    }

    @Override
    public void initView(View view) {
        TextView night = (TextView)view.findViewById(R.id.tv_night);
        night.setText("�ռ�");

        mMenu_Item_Opensoft.setOnClickListener(this);
        mMenu_Item_Blog.setOnClickListener(this);
        mMenu_Item_Quests.setOnClickListener(this);
        mMenu_Item_Setting.setOnClickListener(this);
        mMenu_Item_Theme.setOnClickListener(this);
        mMenu_Item_Gitapp.setOnClickListener(this);
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null
                && mDrawerLayout.isDrawerOpen(mFragmentContentView);
    }

    /**
     * ���õ�����drawer
     * @param fragmentId  fragment ��id
     * @param drawerLayout fragment ������
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContentView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        //����Action bar�ϵ�app icon
        actionBar.setDisplayShowHomeEnabled(false);

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,
                null, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(! isAdded()) {
                    return;
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(! isAdded()) {
                    return;
                }
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).commit();
                }
                getActivity().invalidateOptionsMenu();
            }
        };

        // ����ǵ�һ�ν���Ӧ�ã���ʾ����
        if (!mUserLearnedDrawer && !mFromSaveInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContentView);
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void openDrawerMenu() {
        mDrawerLayout.openDrawer(mFragmentContentView);
    }
    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContentView);
        }
        if (mCallback != null) {
            mCallback.onNavigationDrawerItemSelected(position);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTION_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    public interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(int position);
    }
}
