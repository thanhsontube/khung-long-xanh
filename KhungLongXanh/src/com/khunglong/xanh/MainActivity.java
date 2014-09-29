package com.khunglong.xanh;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.example.sonnt_commonandroid.utils.FilterLog;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.khunglong.xanh.base.BaseFragmentActivity;
import com.khunglong.xanh.login.MyLoginActivity;
import com.khunglong.xanh.main.MainFragment;
import com.khunglong.xanh.main.drawer.DrawerItemGenerator.DrawerItem;
import com.khunglong.xanh.main.drawer.FragmentChangeDrawerItem;
import com.khunglong.xanh.main.drawer.PageChangeDrawerItem;
import com.khunglong.xanh.main.klx.DetailMainFragment.IDetailsFragmentListener;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.khunglong.xanh.myfacebook.FbMeLoader;
import com.khunglong.xanh.myfacebook.FbUserLoader;
import com.khunglong.xanh.myfacebook.object.FbCmtFrom;
import com.khunglong.xanh.myfacebook.object.FbMe;
import com.khunglong.xanh.utils.BitmapUtils;
import com.khunglong.xanh.utils.GoogleAnaToolKLX;
import com.khunglong.xanh.zoom.SingleTouchImageViewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends BaseFragmentActivity implements IDetailsFragmentListener {
    private static final String TAG = "MainActivity";
    FilterLog log = new FilterLog(TAG);
    public UiLifecycleHelper uiHelper;

    // drawer
    protected DrawerLayout mDrawerLayout;
    protected ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private final Handler mHandler = new Handler();
    private static final long DELAY_ON_DRAWER_CLICK = 250L;

    // avatar drawer
    private ImageView imgAvatar;
    private TextView txtName;

    private String page;

    ResourceManager resource;

    @Override
    protected Fragment createFragmentMain(Bundle savedInstanceState) {
        return MainFragment.newInstance(page);
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.login_parent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        log.d("log>>>" + "onCreate");
        resource = ResourceManager.getInstance();
        page = ResourceManager.getInstance().getListPageResource().get(0).getFbName();
        if (getIntent() != null) {
            page = getIntent().getStringExtra("page");
        }
        log.d("log>>> " + "page:" + page);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // get data. default the first of list

        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_action_storage,
                R.string.user_info, 0) {
            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                return super.onOptionsItemSelected(item);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        View footer = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.menu_list_drawer_footer, null);
        mDrawerList.addFooterView(footer, null, false);

        View headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.fragment_profile_drawer_item, null);

        imgAvatar = (ImageView) headerView.findViewWithTag("image");
        txtName = (TextView) headerView.findViewWithTag("title");
        mDrawerList.addHeaderView(headerView, null, false);
        mDrawerList.setAdapter(getDrawerAdapter());
        mDrawerList.setOnItemClickListener(itemClickListener);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowCustomEnabled(true);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.actionbar_custom, null);
        getActionBar().setCustomView(customView);

        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                getMyProfile();

            }
        }, DELAY_ON_DRAWER_CLICK);

    }

    // TODO login listener
    public void getMyProfile() {
        // me request
        FbLoaderManager fbLoaderManager = ResourceManager.getInstance().getFbLoaderManager();
        String graphPath = "me/picture";
        Bundle params = new Bundle();
        params.putBoolean("redirect", false);
        params.putInt("width", 500);
        fbLoaderManager.load(new FbUserLoader(getApplicationContext(), graphPath, params) {

            @Override
            public void onFbLoaderSuccess(FbCmtFrom entry) {
                log.d("log>>>" + "FbUserLoader onFbLoaderSuccess");
                String uri = entry.getSource();

                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(uri, imgAvatar, ResourceManager.getInstance().getOptionsCircle());
                // AQuery aQuery = new AQuery(getApplicationContext());
                // ImageOptions options = new ImageOptions();
                // options.round = 40;
                // aQuery.id(imgAvatar).image(uri, options);

            }

            @Override
            public void onFbLoaderStart() {

            }

            @Override
            public void onFbLoaderFail(Throwable e) {
                log.e("log>>>" + "FbUserLoader onFbLoaderFail:" + e.toString());

            }
        });

        fbLoaderManager.load(new FbMeLoader(getApplicationContext(), "me", null) {

            @Override
            public void onFbLoaderSuccess(FbMe entry) {
                // TODO Auto-generated method stub
                txtName.setText(entry.getName());
                GoogleAnaToolKLX.trackerView(getApplicationContext(),
                        "LOGIN:" + entry.getName() + ";email:" + entry.getEmail());

            }

            @Override
            public void onFbLoaderStart() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFbLoaderFail(Throwable e) {
                // TODO Auto-generated method stub

            }
        });

    }

    protected ListAdapter getDrawerAdapter() {
        return new DrawerAdapter(resource.getDrawerItemGenerator().generateMain());
    }

    class DrawerAdapter extends ArrayAdapter<DrawerItem<?>> {
        public DrawerAdapter(List<DrawerItem<?>> objects) {
            super(MainActivity.this, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(getLayoutInflater(), convertView, parent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            final FragmentManager fm = getSupportFragmentManager();
            if (mFragmentTagStack.size() > 0) {
                fm.popBackStackImmediate();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    /**
     * item left-drawer click
     */
    OnItemClickListener itemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final FragmentManager fm = getSupportFragmentManager();
            final FragmentTransaction ft = fm.beginTransaction();
            if (parent.getId() == R.id.left_drawer) {

                // close drawer
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mDrawerLayout.closeDrawer(mDrawerList);
                    }
                }, DELAY_ON_DRAWER_CLICK);

                final DrawerItem<?> item = (DrawerItem<?>) mDrawerList.getAdapter().getItem(position);
                if (item instanceof PageChangeDrawerItem) {

                    String page = ((PageChangeDrawerItem) item).getParam();
                    while (mFragmentTagStack.size() > 0) {
                        fm.popBackStackImmediate();
                        log.d("log>>>" + "fm.popBackStackImmediate()");
                    }
                    // log.d("log>>>" + "f instanceof MainFragment:" + mFragmentTagStack.size() +"tag:" +
                    // mFragmentTagStack.peek());

                    // 1
                    GoogleAnaToolKLX.trackerView(getApplicationContext(), "page:" + page);
                    // if (page.equals(getResources().getStringArray(R.array.page1)[0])) {
                    // ActionBarUtils.update(getActionBar(), getResources().getStringArray(R.array.page1)[1],
                    // R.drawable.ic_launcher);
                    // setCurrentPosition(0);
                    // MainFragment ftest = (MainFragment) fm.findFragmentByTag(FRAGMENT_KEY);
                    // ft.show(ftest);
                    // if (fm.findFragmentByTag("f2") != null) {
                    // ft.hide(fm.findFragmentByTag("f2"));
                    // }
                    //
                    // if (fm.findFragmentByTag("f3") != null) {
                    // ft.hide(fm.findFragmentByTag("f3"));
                    // }
                    //
                    // if (fm.findFragmentByTag("f4") != null) {
                    // ft.hide(fm.findFragmentByTag("f4"));
                    // }
                    // if (fm.findFragmentByTag("f5") != null) {
                    // ft.hide(fm.findFragmentByTag("f5"));
                    // }
                    // ft.commit();
                    //
                    // // 2
                    // } else if (page.equals(getResources().getStringArray(R.array.page2)[0])) {
                    // ActionBarUtils.update(getActionBar(), getResources().getStringArray(R.array.page2)[1],
                    // R.drawable.haivl);
                    // setCurrentPosition(1);
                    // final Fragment ff = fm.findFragmentByTag(FRAGMENT_KEY);
                    // ft.hide(ff);
                    //
                    // // hide f3
                    //
                    // if (fm.findFragmentByTag("f3") != null) {
                    // ft.hide(fm.findFragmentByTag("f3"));
                    // }
                    // if (fm.findFragmentByTag("f4") != null) {
                    // ft.hide(fm.findFragmentByTag("f4"));
                    // }
                    // if (fm.findFragmentByTag("f5") != null) {
                    // ft.hide(fm.findFragmentByTag("f5"));
                    // }
                    // MainFragment2 f2;
                    // if (fm.findFragmentByTag("f2") == null) {
                    // log.d("log>>>" + "	ft.show(f2);");
                    // f2 = MainFragment2.newInstance(page);
                    // // f2 = new MainFragment3();
                    // ft.add(getFragmentContentId(), f2, "f2");
                    // ft.show(f2);
                    // } else {
                    // f2 = (MainFragment2) fm.findFragmentByTag("f2");
                    // ft.show(f2);
                    // }
                    // ft.commit();
                    //
                    // } else if (page.equals(getResources().getStringArray(R.array.page3)[0])) {
                    // ActionBarUtils.update(getActionBar(), getResources().getStringArray(R.array.page3)[1],
                    // R.drawable.haivl);
                    // setCurrentPosition(2);
                    //
                    // // hide f1
                    // final Fragment ff = fm.findFragmentByTag(FRAGMENT_KEY);
                    // ft.hide(ff);
                    //
                    // // hide f2
                    // if (fm.findFragmentByTag("f2") != null) {
                    // ft.hide(fm.findFragmentByTag("f2"));
                    //
                    // }
                    // if (fm.findFragmentByTag("f4") != null) {
                    // ft.hide(fm.findFragmentByTag("f4"));
                    // }
                    // if (fm.findFragmentByTag("f5") != null) {
                    // ft.hide(fm.findFragmentByTag("f5"));
                    // }
                    // // show f3
                    // if (fm.findFragmentByTag("f3") == null) {
                    // // Fragment f3 = TestFragment.newInstance(page, 2);
                    // Fragment f3 = MainFragment3.newInstance(page);
                    // ft.add(getFragmentContentId(), f3, "f3");
                    // ft.show(f3);
                    // // ft.addToBackStack(null);
                    // } else {
                    // ft.show(fm.findFragmentByTag("f3"));
                    // }
                    // ft.commit();
                    // }
                    //
                    // else if (page.equals(getResources().getStringArray(R.array.page4)[0])) {
                    // ActionBarUtils.update(getActionBar(), getResources().getStringArray(R.array.page4)[1],
                    // R.drawable.nghiemtucvl);
                    // setCurrentPosition(3);
                    //
                    // // hide f1
                    // final Fragment ff = fm.findFragmentByTag(FRAGMENT_KEY);
                    // ft.hide(ff);
                    //
                    // // hide f2
                    // if (fm.findFragmentByTag("f2") != null) {
                    // ft.hide(fm.findFragmentByTag("f2"));
                    //
                    // }
                    //
                    // if (fm.findFragmentByTag("f3") != null) {
                    // ft.hide(fm.findFragmentByTag("f3"));
                    //
                    // }
                    // if (fm.findFragmentByTag("f5") != null) {
                    // ft.hide(fm.findFragmentByTag("f5"));
                    //
                    // }
                    //
                    // if (fm.findFragmentByTag("f4") == null) {
                    // Fragment f = MainFragment4.newInstance(page);
                    // ft.add(getFragmentContentId(), f, "f4");
                    // ft.show(f);
                    // } else {
                    // ft.show(fm.findFragmentByTag("f4"));
                    // }
                    // ft.commit();
                    // }
                    //
                    // else if (page.equals(getResources().getStringArray(R.array.page5)[0])) {
                    // ActionBarUtils.update(getActionBar(), getResources().getStringArray(R.array.page5)[1],
                    // R.drawable.hanhphucgiobay);
                    // setCurrentPosition(4);
                    //
                    // // hide f1
                    // final Fragment ff = fm.findFragmentByTag(FRAGMENT_KEY);
                    // ft.hide(ff);
                    //
                    // // hide f2
                    // if (fm.findFragmentByTag("f2") != null) {
                    // ft.hide(fm.findFragmentByTag("f2"));
                    //
                    // }
                    //
                    // if (fm.findFragmentByTag("f3") != null) {
                    // ft.hide(fm.findFragmentByTag("f3"));
                    //
                    // }
                    //
                    // if (fm.findFragmentByTag("f4") != null) {
                    // ft.hide(fm.findFragmentByTag("f4"));
                    //
                    // }
                    //
                    // if (fm.findFragmentByTag("f5") == null) {
                    // Fragment f = MainFragment5.newInstance(page);
                    // ft.add(getFragmentContentId(), f, "f5");
                    // ft.show(f);
                    // } else {
                    // ft.show(fm.findFragmentByTag("f5"));
                    // }
                    // ft.commit();
                    // }

                    log.d("log>>>" + "stack:" + mFragmentTagStack.size());

                    // MainFragment fmain = (MainFragment) fm.findFragmentByTag(mFragmentTagStack.peek());
                    // // fmain.changeViewPager(0);
                    // fmain.load(page, 0);

                    // Fragment f = ((FragmentChangeDrawerItem) item).getParam();
                    // if (f instanceof MainFragment) {
                    // log.d("log>>>" + "f instanceof MainFragment");
                    // while (mFragmentTagStack.size() > 1) {
                    // fm.popBackStackImmediate();
                    // log.d("log>>>" + "fm.popBackStackImmediate()");
                    // }
                    // log.d("log>>>" + "f instanceof MainFragment:" + mFragmentTagStack.size());
                    // // MainFragment fmain = (MainFragment) fm.findFragmentByTag(FRAGMENT_KEY);
                    // MainFragment fmain = (MainFragment) fm.findFragmentByTag(mFragmentTagStack.get(1));
                    // fmain.changeViewPager(0);
                    // } else {
                    // log.d("log>>>" + "showFragment");
                    // while (mFragmentTagStack.size() > 0) {
                    // fm.popBackStackImmediate();
                    // log.d("log>>>" + "fm.popBackStackImmediate()");
                    // }
                    // showFragment(f, false);
                    // }

                } else if (item instanceof FragmentChangeDrawerItem) {

                    Fragment f = (Fragment) item.getParam();
                    showFragment(f, true);
                }
            }

        }
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            log.d(">>>Logged in...:" + session.getAccessToken());

        } else if (state.isClosed()) {
            log.d(">>>Logged out...");
            startActivity(new Intent(getApplicationContext(), MyLoginActivity.class));
            MainActivity.this.finish();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onDetailsFragmentPicture(String link, String content) {
        // ZoomInZoomOut f = ZoomInZoomOut.newInstance(image);
        // FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // ft.add(getFragmentContentId(), f, "fa");
        // ft.addToBackStack(null);
        // ft.show(f);
        // ft.commit();
        // Intent intent = new Intent(this, AnimationActivity.class);
        Intent intent = new Intent(this, SingleTouchImageViewActivity.class);
        intent.putExtra("image", link);
        intent.putExtra("content", content);
        startActivity(intent);
        // startActivity(new Intent(this, AnimationActivity.class));

        // showFragment(f, true);

    }

    /*
     * private static final long EXIT_INTERVAL = 2000L; private long exitTimer = Long.MIN_VALUE;
     * 
     * private boolean tryFinish = false;
     * 
     * @Override public boolean dispatchKeyEvent(KeyEvent event) { log.d("log>>>" + "dispatchKeyEvent");
     * 
     * if ((event.getAction() == KeyEvent.ACTION_DOWN) && (event.getKeyCode() == KeyEvent.KEYCODE_BACK) &&
     * (event.getRepeatCount() == 0)) {
     * 
     * tryFinish = false; if (mFragmentTagStack.size() == 0) { tryFinish = true; }
     * 
     * if (tryFinish) { if ((exitTimer + EXIT_INTERVAL) < System.currentTimeMillis()) { Toast.makeText(this,
     * "Back Again to finish", Toast.LENGTH_SHORT).show(); exitTimer = System.currentTimeMillis(); } else { finish(); }
     * } else { return super.dispatchKeyEvent(event); }
     * 
     * return true; }
     * 
     * return super.dispatchKeyEvent(event); }
     */

    // @Override
    // public boolean onCreateOptionsMenu(Menu menu) {
    // getMenuInflater().inflate(R.menu.main, menu);
    // return true;
    // }

    @Override
    public void onBackStackChanged() {
        super.onBackStackChanged();
        if (mFragmentTagStack.size() > 0) {
            mDrawerToggle.setDrawerIndicatorEnabled(false);
        } else {
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            getActionBar().setDisplayShowCustomEnabled(true);
        }
    }

}
