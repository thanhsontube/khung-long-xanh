package com.khunglong.xanh.main.klx;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.base.Controller;
import com.khunglong.xanh.comments.CmtAdapter;
import com.khunglong.xanh.dialog.ReplyDialog;
import com.khunglong.xanh.json.DragonData;
import com.khunglong.xanh.json.PageData;
import com.khunglong.xanh.myfacebook.FbCommentsLoader;
import com.khunglong.xanh.myfacebook.FbUserLoader;
import com.khunglong.xanh.myfacebook.object.FbCmtData;
import com.khunglong.xanh.myfacebook.object.FbCmtFrom;
import com.khunglong.xanh.myfacebook.object.FbComments;
import com.khunglong.xanh.utils.MsUtils;

public class DetailCommentFragment extends BaseFragment {
    FilterLog log = new FilterLog("DetailCommentFragment");

    // adapter
    private CmtAdapter cmtAdapter;
    // data
    private List<FbCmtData> cmtListData = new ArrayList<FbCmtData>();

    // layout
    private ListView cmtListview;
    TextView txtCommend;
    private View mEmpty;
    private PageData pageData;
    private int position = 1;
    private String page = null;
    private DragonData dragonData = null;

    // public static DetailCommentFragment newInstance(String page) {
    // DetailCommentFragment f = new DetailCommentFragment();
    // Bundle bundle = new Bundle();
    // bundle.putString("page", page);
    // f.setArguments(bundle);
    // return f;
    // }
    //
    // @Override
    // public void onCreate(Bundle savedInstanceState) {
    // super.onCreate(savedInstanceState);
    // setHasOptionsMenu(true);
    //
    // if (getArguments() != null) {
    // Bundle bundle = getArguments();
    // page = bundle.getString("page");
    // if (page.equals(resource.getListPageResource().get(0).getFbName())) {
    // dragonData = resource.getChandaiData();
    // }
    //
    // if (page.equals(resource.getListPageResource().get(1).getFbName())) {
    // dragonData = resource.getKlxData();
    // }
    // } else {
    // page = resource.getListPageResource().get(0).getFbName();
    // dragonData = resource.getKlxData();
    // }
    //
    // log.d("log>>> onCreate " + "page:" + page);
    //
    // }

    public static DetailCommentFragment newInstance() {
        DetailCommentFragment f = new DetailCommentFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        log.d("log>>> " + "onCreateView page:" + page);
        View rootView = inflater.inflate(R.layout.comment_fragment_card, container, false);
        View v = getActivity().getActionBar().getCustomView();
        txtCommend = (TextView) v.findViewWithTag("commend");

        cmtListview = (ListView) rootView.findViewWithTag("listview");
        mEmpty = (ViewGroup) rootView.findViewById(android.R.id.empty);
        inflater.inflate(R.layout.waiting, (ViewGroup) mEmpty, true);
        enableEmptyview(true);
        cmtAdapter = new CmtAdapter(getActivity(), cmtListData, false);
        cmtListview.setAdapter(cmtAdapter);
        // alow touch list in fragment
        cmtListview.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;

            }
        });

        cmtListview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                String link = cmtListData.get(pos).getFrom().getSource();
                FragmentManager fm = getChildFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                // ReplyDialog dialog = new ReplyDialog();

                ReplyDialog dialog = ReplyDialog.newInstance(cmtListData.get(pos).getLike_count(), cmtListData.get(pos)
                        .getMessage(), cmtListData.get(pos).getFrom().getName(), link, cmtListData.get(pos).getId(),
                        position, pos);
                ft.add(dialog, dialog.getClass().getName());
                ft.commitAllowingStateLoss();

            }
        });
        resource = ResourceManager.getInstance();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setData1St(String page, PageData data, int position) {
        this.page = page;
        dragonData = MsUtils.getDragonDataFrompage(page);

        // if (page.equals(resource.getListPageResource().get(0).getFbName())) {
        // dragonData = resource.getChandaiData();
        // }
        //
        // if (page.equals(resource.getListPageResource().get(1).getFbName())) {
        // dragonData = resource.getKlxData();
        // }

        setData(data, position);
    }

    public void setData(PageData data, int position) {
        log.i("log>>> " + "DetailCommentFragment setData:" + position);
        txtCommend.setText("---");
        this.pageData = data;
        this.position = position;

        if (dragonData.getData().get(position).getComments() == null) {
            controllerComments.load();
        } else {
            txtCommend.setText("" + dragonData.getData().get(position).getComments().getSummary().getTotal_count());
            cmtAdapter.setData(dragonData.getData().get(position).getComments().getData(), true);
            enableEmptyview(false);
        }
    }

    private Controller controllerComments = new Controller() {

        @Override
        public void load() {
            Bundle params = new Bundle();
            params.putBoolean("summary", true);
            params.putString("filter", "toplevel");
            params.putInt("limit", 30);
            final String graphPath = pageData.getId() + "/comments";

            resource.getFbLoaderManager().load(new FbCommentsLoader(getActivity(), graphPath, params) {

                @Override
                public void onFbLoaderSuccess(FbComments entry) {
                    // updateComments(entry, position);
                    dragonData.getData().get(position).setComments(entry);

                    txtCommend.setText(""
                            + dragonData.getData().get(position).getComments().getSummary().getTotal_count());
                    cmtAdapter.setData(entry.getData(), true);

                    enableEmptyview(false);
                    // updateComments(entry, position);

                    loadAvatarCommender(entry);
                }

                @Override
                public void onFbLoaderStart() {
                    enableEmptyview(true);
                }

                @Override
                public void onFbLoaderFail(Throwable e) {
                    enableEmptyview(false);
                    Toast.makeText(getActivity(), "FbCommentsLoader onFbLoaderFail:" + e.toString(), Toast.LENGTH_SHORT)
                            .show();
                    log.e("log>>>" + "FbCommentsLoader onFbLoaderFail:" + e.toString());
                }
            });
        }
    };

    public void loadAvatarCommender(FbComments entry) {
        for (int i = 0; i < entry.getData().size(); i++) {

            final int pos = i;

            String idFrom = entry.getData().get(i).getFrom().getId();
            String graphPath = idFrom + "/picture";
            Bundle params = new Bundle();
            params.putBoolean("redirect", false);
            params.putInt("width", 100);
            resource.getFbLoaderManager().load(new FbUserLoader(getActivity(), graphPath, params) {

                @Override
                public void onFbLoaderSuccess(FbCmtFrom f) {
                    cmtListData.get(pos).getFrom().setSource(f.getSource());
                    cmtAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFbLoaderStart() {
                }

                @Override
                public void onFbLoaderFail(Throwable e) {
                    log.e("log>>>" + "FbUserLoader  onFbLoaderFail:" + e.toString());
                }
            });
        }
    }

    private void updateComments(final FbComments entry, final int position) {
        log.d("log>>>" + "==========updateComments position:" + position);

        // DragonData mDragonData = dragonData;
        // mDragonData.getData().get(position).getComments().setSummary(entry.getSummary());
        // mDragonData.getData().get(position).getComments().getData().addAll(entry.getData());
        // mDragonData.getData().get(position).getComments().setPaging(entry.getPaging());

        // for (int i = 0; i < 3; i ++) {
        // log.d("log>>>" + "message:" + entry.getData().get(i).getMessage());
        // log.d("log>>>" + "id:" + entry.getData().get(i).getFrom().getId() + ";name:" +
        // entry.getData().get(i).getFrom().getName() +
        // "message:" + entry.getData().get(i).getMessage()) ;
        // }

        // holder.numberAnswers.setText(String.valueOf(entry.getSummary().getTotal_count()));
        cmtListData.clear();
        List<FbCmtData> collection = entry.getData();
        cmtListData.addAll(collection);
        cmtAdapter.notifyDataSetChanged();

        // load avatar commenter
        for (int i = 0; i < entry.getData().size(); i++) {

            final int pos = i;

            String idFrom = entry.getData().get(i).getFrom().getId();
            String graphPath = idFrom + "/picture";
            Bundle params = new Bundle();
            params.putBoolean("redirect", false);
            params.putInt("width", 100);
            resource.getFbLoaderManager().load(new FbUserLoader(getActivity(), graphPath, params) {

                @Override
                public void onFbLoaderSuccess(FbCmtFrom f) {
                    cmtListData.get(pos).getFrom().setSource(f.getSource());
                    cmtAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFbLoaderStart() {
                }

                @Override
                public void onFbLoaderFail(Throwable e) {
                    log.e("log>>>" + "FbUserLoader  onFbLoaderFail:" + e.toString());
                }
            });
        }
    }

    public void enableEmptyview(boolean isOpen) {
        if (isOpen) {
            mEmpty.setVisibility(View.VISIBLE);
            cmtListview.setVisibility(View.GONE);
        } else {
            mEmpty.setVisibility(View.GONE);
            cmtListview.setVisibility(View.VISIBLE);
        }
    }

}
