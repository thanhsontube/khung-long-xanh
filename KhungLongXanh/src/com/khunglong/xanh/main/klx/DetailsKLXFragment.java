package com.khunglong.xanh.main.klx;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.MainActivity;
import com.khunglong.xanh.MyApplication;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.base.Controller;
import com.khunglong.xanh.comments.AnswerFragment;
import com.khunglong.xanh.comments.CmtAdapter;
import com.khunglong.xanh.json.DragonData;
import com.khunglong.xanh.json.PageData;
import com.khunglong.xanh.mycomments.MyCommentsFragment;
import com.khunglong.xanh.myfacebook.FbCommentsLoader;
import com.khunglong.xanh.myfacebook.FbLikesLoader;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.khunglong.xanh.myfacebook.FbUserLoader;
import com.khunglong.xanh.myfacebook.object.FbCmtData;
import com.khunglong.xanh.myfacebook.object.FbCmtFrom;
import com.khunglong.xanh.myfacebook.object.FbComments;
import com.khunglong.xanh.myfacebook.object.FbLikes;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class DetailsKLXFragment extends BaseFragment {
	FilterLog log = new FilterLog("DetailsKLXFragment");
	ImageLoader imageLoader;

	// layout

	// actionBar
	TextView txtLike;

	private DisplayImageOptions optionsAvatar;
	private DisplayImageOptions optionsContent;
	private String link;
	private String linkHigh;
	private String content;
	private int position;

	private ListView cmtListview;
	private CmtAdapter cmtAdapter;
	private List<FbCmtData> cmtListData;
	private PageData pageData;
	private FbLoaderManager mFbLoaderManager;
	private MyApplication app;
	private Context context;
	// private DragonData mDragonData;
	private int maxPosition = -1;

	private ResourceManager resource;
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

	public static DetailsKLXFragment newInstance() {
		DetailsKLXFragment f = new DetailsKLXFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity().getApplicationContext();
		resource = ResourceManager.getInstance();
		app = (MyApplication) getActivity().getApplication();
		imageLoader = ImageLoader.getInstance();
		optionsContent = app.getOptionsContent();
		mFbLoaderManager = resource.getFbLoaderManager();

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		FragmentManager fm = getChildFragmentManager();
		ImageTitleKlxFragment f = (ImageTitleKlxFragment) fm.findFragmentById(R.id.detail_top);
		FragmentTransaction ft = fm.beginTransaction();
		if (f == null) {
			ft.add(R.id.detail_top, ImageTitleKlxFragment.newInstance());
		}

		CommentFragment cmtFragment = (CommentFragment) fm.findFragmentById(R.id.detail_bottom);
		if (cmtFragment == null) {
			ft.add(R.id.detail_bottom, CommentFragment.newInstance());
		}
		ft.commit();
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

	public void setData(PageData data, int position) {
		txtLike.setText("---");
		log.d("log>>>" + "setData position:" + position + ";" + data.getSource());
		this.pageData = data;
		this.position = position;
		updateLayoutPage(data);

		if (data.getLikes().getSummary() == null) {
			controllerLikes.load();
		} else {
			txtLike.setText("old:" + data.getLikes().getSummary().getTotal_count());
		}
	}

	private void updateLayoutPage(PageData pageData) {
		log.d("log>>>" + "updateLayoutPage title:" + pageData.getName());

		FragmentManager fm = getChildFragmentManager();
		ImageTitleKlxFragment f = (ImageTitleKlxFragment) fm.findFragmentById(R.id.detail_top);
		FragmentTransaction ft = fm.beginTransaction();
		if (f == null) {
			f = ImageTitleKlxFragment.newInstance();
			ft.add(R.id.detail_top, f);
		}

		// comment
		CommentFragment cmtFragment = (CommentFragment) fm.findFragmentById(R.id.detail_bottom);
		if (cmtFragment == null) {
			cmtFragment = CommentFragment.newInstance();
			ft.add(R.id.detail_bottom, cmtFragment);
		}
		ft.commit();

		// update
		f.updateImageAndTitle(pageData);
		cmtFragment.setData(pageData, position);
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
					resource.getKlxData().getData().get(position).setLikes(entry);

					txtLike.setText("new:" + entry.getSummary().getTotal_count());
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
