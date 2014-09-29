package com.khunglong.xanh.main.klx;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.base.Controller;
import com.khunglong.xanh.json.PageData;
import com.khunglong.xanh.myfacebook.FbLikesLoader;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.khunglong.xanh.myfacebook.object.FbLikes;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DetailMainFragment extends BaseFragment {
    FilterLog log = new FilterLog("DetailMainFragment");
    ImageLoader imageLoader;

    // layout

    // actionBar
    private TextView txtLike;
    private int position;

    private PageData pageData;
    private FbLoaderManager mFbLoaderManager;
    private Context context;
    // private DragonData mDragonData;

    private ResourceManager resource;

    private String page;

    private IDetailsFragmentListener listener;
    public static interface IDetailsFragmentListener {
        void onDetailsFragmentPicture(String link, String content);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IDetailsFragmentListener) {
            listener = (IDetailsFragmentListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public static DetailMainFragment newInstance() {
        DetailMainFragment f = new DetailMainFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        resource = ResourceManager.getInstance();
        imageLoader = ImageLoader.getInstance();
        mFbLoaderManager = resource.getFbLoaderManager();
    }

    // TODO onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        log.d("log>>>" + "onCreateView");
        View rootView = inflater.inflate(R.layout.klx_detail_fragment, container, false);

        View v = getActivity().getActionBar().getCustomView();
        txtLike = (TextView) v.findViewWithTag("like");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        DetailHeaderFragment f = (DetailHeaderFragment) fm.findFragmentById(R.id.detail_top);
        FragmentTransaction ft = fm.beginTransaction();
        if (f == null) {
            ft.add(R.id.detail_top, DetailHeaderFragment.newInstance());
        }

        DetailCommentFragment cmtFragment = (DetailCommentFragment) fm.findFragmentById(R.id.detail_bottom);
        if (cmtFragment == null) {
            ft.add(R.id.detail_bottom, DetailCommentFragment.newInstance());
        }
        ft.commit();
    }

    public void setData1St(String page, PageData data, int pos) {
        this.page = page;
        pageData = data;
        position = pos;

        txtLike.setText("---");

        updateDetail(pageData, true);

        updateLike();
    }

    public void setData(PageData data, int pos) {
        // log.i("log>>> " + "DetailMainFragment setdata:" + pos);
        pageData = data;
        position = pos;

        txtLike.setText("---");

        updateDetail(pageData, false);
        updateLike();

    }

    private void updateLike() {
        log.v("log>>> " + "updateLike");

        if (pageData.getLikes().getSummary() == null) {
            controllerLikes.load();
        } else {
            txtLike.setText("" + pageData.getLikes().getSummary().getTotal_count());
        }
    }

    private void updateDetail(PageData pageData, boolean isFirst) {
        log.v("log>>>" + "updateDetail title:" + pageData.getName());

        FragmentManager fm = getChildFragmentManager();
        DetailHeaderFragment headerFragment = (DetailHeaderFragment) fm.findFragmentById(R.id.detail_top);
        FragmentTransaction ft = fm.beginTransaction();

        // comment
        DetailCommentFragment cmtFragment = (DetailCommentFragment) fm.findFragmentById(R.id.detail_bottom);
        ft.commit();
        headerFragment.setData(pageData);
        if (isFirst) {
            cmtFragment.setData1St(page, pageData, position);
        } else {
            cmtFragment.setData(pageData, position);
        }
    }

    private Controller controllerLikes = new Controller() {

        @Override
        public void load() {
            Bundle params = new Bundle();
            params.putString("summary", "1");
            String graphPath = pageData.getId() + "/likes";
            mFbLoaderManager.load(new FbLikesLoader(context, graphPath, params) {

                @Override
                public void onFbLoaderSuccess(FbLikes entry) {
                    log.d("log>>>" + "FbLikesLoader onFbLoaderSuccess entry:" + entry.getSummary().getTotal_count());

                    // update data like
//                    entry.getData().get(position).setLikes(entry);

                    txtLike.setText("" + entry.getSummary().getTotal_count());
                }

                @Override
                public void onFbLoaderStart() {

                }

                @Override
                public void onFbLoaderFail(Throwable e) {
                    log.e("log>>>" + "FbLikesLoader onFbLoaderFail:" + e.toString());
                }

            });
        }
    };

}
