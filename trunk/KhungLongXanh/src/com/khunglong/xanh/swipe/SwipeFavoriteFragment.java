package com.khunglong.xanh.swipe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.MsConstant;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.base.MyFragmentPagerAdapter;
import com.khunglong.xanh.data.MyDataHelper;
import com.khunglong.xanh.image.ImageFullZoomFragment;

public class SwipeFavoriteFragment extends BaseFragment implements OnPageChangeListener {

    private static final String TAG = "SwipeFavoriteFragment";
    private int position;
    List<String> pageTitle = new ArrayList<String>();
    private List<File> list = new ArrayList<File>();
    private ViewPager pager;
    FilterLog log = new FilterLog(TAG);
    private boolean isFavorite;

    private MainPagerAdapter adapter;

    public static SwipeFavoriteFragment newInstance(int value) {
        SwipeFavoriteFragment f = new SwipeFavoriteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("value", value);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        log.d("log>>> " + "onCreate");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            position = getArguments().getInt("value");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getActionBar().setHomeButtonEnabled(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().getActionBar().setDisplayShowCustomEnabled(false);

        getActivity().getActionBar().setTitle("Swipe Favorite");
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.swipe_favorite_fragment, container, false);
        File file = new File(Environment.getExternalStorageDirectory(), "KLX");
        

        pageTitle.clear();
        list.clear();

        Cursor cursor = ResourceManager.getInstance().getSqlite().getData(MyDataHelper.DATABASE_TABLE_FAVORITE);
        if (cursor.moveToFirst()) {
            do {
                String value = cursor.getString(1);
                if (file.exists()) {
                    File fPic = new File(file, value);
                    if (fPic.exists()) {
                        list.add(fPic);
                        pageTitle.add(fPic.getName());
                    }
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        pager = (ViewPager) rootView.findViewById(R.id.pager_favorite);
        adapter = new MainPagerAdapter(getFragmentManager(), getActivity());
        pager.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager.setCurrentItem(position);
        isFavorite = resource.getSqlite().isFavorite(list.get(position).getName());
    }

    class MainPagerAdapter extends MyFragmentPagerAdapter {

        Context mContext;
        private Fragment mPrimaryFragment;

        public MainPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.mContext = context;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            mPrimaryFragment = (Fragment) object;
        }

        public Fragment getCurrentFragment() {
            return mPrimaryFragment;
        }

        @Override
        public boolean isFragmentReusable(Fragment f, int position) {
            return false;
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFullZoomFragment.newInstance(list.get(position).getAbsolutePath());
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return pageTitle.get(position);
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public CharSequence getPageName(int position) {
            return pageTitle.get(position);
        }
    }

    private MenuItem itemFavorite;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        itemFavorite = menu.findItem(MsConstant.MENU_FAVORITE_ID);
        if (itemFavorite != null) {
            return;
        }
        itemFavorite = menu.add(0, MsConstant.MENU_FAVORITE_ID, 0, "Favorite");
        itemFavorite.setIcon(R.drawable.star_off);
        itemFavorite.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (itemFavorite == null) {
            return;
        }
        if (isFavorite) {
            itemFavorite.setIcon(R.drawable.star_on);
        } else {
            itemFavorite.setIcon(R.drawable.star_off);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MsConstant.MENU_FAVORITE_ID:
            if (TextUtils.isEmpty(list.get(position).getName())) {
                return false;
            }
            if (isFavorite) {
                resource.getSqlite().removeFavorite(list.get(position).getName());
            } else {
                resource.getSqlite().addFavorite(list.get(position).getName());
            }
            isFavorite = !isFavorite;

            getActivity().invalidateOptionsMenu();
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (itemFavorite != null) {
            itemFavorite.setVisible(false);
            getActivity().invalidateOptionsMenu();
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        position = arg0;
        isFavorite = resource.getSqlite().isFavorite(list.get(position).getName());
        getActivity().invalidateOptionsMenu();
    }

}
