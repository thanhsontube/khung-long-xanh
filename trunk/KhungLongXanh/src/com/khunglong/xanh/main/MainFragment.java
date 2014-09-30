package com.khunglong.xanh.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.MsConstant;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.base.BaseFragmentActivity.OnBackPressListener;
import com.khunglong.xanh.base.Controller;
import com.khunglong.xanh.base.MyFragmentPagerAdapter;
import com.khunglong.xanh.json.DragonData;
import com.khunglong.xanh.libs.InfinitePagerAdapter;
import com.khunglong.xanh.libs.InfiniteViewPager;
import com.khunglong.xanh.main.klx.DetailMainFragment;
import com.khunglong.xanh.myfacebook.FbLoaderAlbumsList;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.khunglong.xanh.myfacebook.FbPageLoader;
import com.khunglong.xanh.myfacebook.object.FbAlbums;
import com.khunglong.xanh.myfacebook.object.FbAlbumsDto;
import com.khunglong.xanh.utils.MsUtils;

public class MainFragment extends BaseFragment implements OnPageChangeListener, OnBackPressListener {

    private static final String TAG = "MainFragment";
    private static final int ILOAD = 10;
    private MainPagerAdapter mMainPagerAdapter;
    private InfinitePagerAdapter wrappedAdapter;
    private InfiniteViewPager mViewPager;
    private FilterLog log = new FilterLog(TAG);

    private String[] pageTitle;

    private IMainFragmentListener listener;

    private DragonData dragonData;
    private ViewGroup mEmpty;
    private boolean isLoading = false;
    private FbLoaderManager mFbLoaderManager;
    private Context context;
    private String albumId;
    private String page;

    private ResourceManager resource;

    public static interface IMainFragmentListener {
        void onIMainFragmentStart(MainFragment f, int i, BaseObject link);

        void onMainFragmentPageSelected(MainFragment main, Fragment selected, BaseObject link);

        void onMainFragmentPageDeSelected(MainFragment main, Fragment selected, BaseObject link);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IMainFragmentListener) {
            listener = (IMainFragmentListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public static MainFragment newInstance(String page) {
        MainFragment f = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("page", page);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        resource = ResourceManager.getInstance();
        mFbLoaderManager = resource.getFbLoaderManager();
        // dragonData = new DragonData();
        // app.getmListDragonDatas().add(dragonData);

        setHasOptionsMenu(true);
        resource.resetData();

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            page = bundle.getString("page");
            dragonData = MsUtils.getDragonDataFrompage(page);
            // if (page.equals(resource.getListPageResource().get(0).getFbName())) {
            // dragonData = resource.getChandaiData();
            // }
            //
            // if (page.equals(resource.getListPageResource().get(1).getFbName())) {
            // dragonData = resource.getKlxData();
            // }
            //
            // if (page.equals(resource.getListPageResource().get(2).getFbName())) {
            // dragonData = resource.getGtgData();
            // }
            //
            // if (page.equals(resource.getListPageResource().get(3).getFbName())) {
            // dragonData = resource.getHaivlData();
            // }
            // if (page.equals(resource.getListPageResource().get(4).getFbName())) {
            // dragonData = resource.getNghiemtucvlData();
            // }

        } else {
            Toast.makeText(getActivity(), "Error get Page", Toast.LENGTH_SHORT).show();
        }

        // dragonData = null;
        // dragonData = new DragonData();

        log.d("log>>> onCreate " + "page:" + page);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = (ViewGroup) inflater.inflate(R.layout.main_fragment, container, false);
        context = getActivity().getApplicationContext();
        mEmpty = (ViewGroup) rootView.findViewById(android.R.id.empty);
        mEmpty.setVisibility(View.VISIBLE);
        inflater.inflate(R.layout.waiting, mEmpty, true);
        log.d("log>>>" + "onCreateView2");

        mMainPagerAdapter = new MainPagerAdapter(getFragmentManager(), getActivity());

        mViewPager = (InfiniteViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setStart(0);
        wrappedAdapter = new InfinitePagerAdapter(mMainPagerAdapter);
        mViewPager.setAdapter(wrappedAdapter);
        mViewPager.setOnPageChangeListener(this);
        // mViewPager.setPageTransformer(true, new DepthPageTransformer());

        if (listener != null) {
            listener.onIMainFragmentStart(MainFragment.this, 10, null);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controllerAlbums.load();
    }

    public CharSequence getApplicationTitle(int position) {
        return mMainPagerAdapter.getPageTitle(position);
    }

    public void changeViewPager(int i) {
        mViewPager.setCurrentItem(i);
    }

    public boolean resetPagePosition() {
        return false;
    }

    public void findTimelineAlbums(FbAlbums entry) {
        albumId = "nothing";
        for (final FbAlbumsDto element : entry.listFbAlbumsDto) {
            if (element.getName().equalsIgnoreCase("Timeline Photos")) {
                albumId = element.getId();
                controllerTimelines.load();
                // controllerPhoto.load();
            }
        }

        log.d("log>>> " + "findTimelineAlbums albumId:" + albumId);
    }

    private Controller controllerAlbums = new Controller() {

        @Override
        public void load() {
            isLoading = true;

            mFbLoaderManager.load(new FbLoaderAlbumsList(context, page) {

                @Override
                public void onFbLoaderSuccess(FbAlbums entry) {
                    mEmpty.setVisibility(View.GONE);
                    log.d("log>>>" + "onFbLoaderSuccess size album:" + entry.listFbAlbumsDto.size());
                    dragonData.setAlbums(entry);
                    // get Timeline Photos album;
                    findTimelineAlbums(entry);
                }

                @Override
                public void onFbLoaderStart() {
                    mEmpty.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFbLoaderFail(Throwable e) {
                    mEmpty.setVisibility(View.GONE);
                    log.e("log>>>" + "controllerAlbums onFbLoaderFail:" + e.toString());
                }
            });
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mViewPager.invalidate();

    }

    @Override
    public boolean onBackPress() {
        return resetPagePosition();
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        int virtualPosition = position % (mMainPagerAdapter.getCount());
        if (position > dragonData.getData().size() - 1) {
            return;
        }
        DetailMainFragment f1 = (DetailMainFragment) mMainPagerAdapter.getFragment(mViewPager, virtualPosition);
        f1.setData1St(page, dragonData.getData().get(position), position);

        if ((dragonData.getData().size() - position) <= ILOAD && !isLoading) {
            controllerTimelines.load();
        }
    }

    // adapter

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
            return true;
        }

        @Override
        public Fragment getItem(int position) {
            // return new DetailsFragment(position, null);
            return DetailMainFragment.newInstance();
        }

        @Override
        public int getCount() {
            return MsConstant.PAGE_DUPE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitle[position];
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public CharSequence getPageName(int position) {
            return pageTitle[position];
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View view, float position) {

            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }

        }

    }

    Controller controllerTimelines = new Controller() {

        @Override
        public void load() {
            isLoading = true;
            String after = dragonData.getPaging().getCursors().after;
            Bundle params = new Bundle();
            params.putString("after", after);
            // final String graphPath = dragonData.getAlbumTimeLines() + "/photos";
            final String graphPath = albumId + "/photos";
            mFbLoaderManager.load(new FbPageLoader(context, graphPath, params) {

                @Override
                public void onFbLoaderSuccess(DragonData entry) {
                    log.d("log>>>" + "1 controllerTimelines success " + "load Image:" + entry.getData().size());
                    dragonData.setPaging(entry.getPaging());
                    dragonData.getData().addAll(entry.getData());
                    mEmpty.setVisibility(View.GONE);
                    // DetailsFragment f1 = (DetailsFragment) mMainPagerAdapter.getFragment(mViewPager, 0);
                    // if (isFirstLoad) {
                    // f1.setData(entry.getData().get(0), 0);
                    // isFirstLoad = false;
                    // }

                    DetailMainFragment f1 = (DetailMainFragment) mMainPagerAdapter.getFragment(mViewPager, 0);
                    f1.setData1St(page, entry.getData().get(0), 0);

                    isLoading = false;
                }

                @Override
                public void onFbLoaderStart() {
                    mEmpty.setVisibility(View.VISIBLE);

                }

                @Override
                public void onFbLoaderFail(Throwable e) {
                    mEmpty.setVisibility(View.GONE);
                    log.e("log>>>" + "controllerTimelines onFbLoaderFail:" + e.toString());

                }
            });
        }
    };

}
