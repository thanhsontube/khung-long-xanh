package com.khunglong.xanh.main.klx;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.base.Controller;
import com.khunglong.xanh.comments.CmtAdapter;
import com.khunglong.xanh.json.DragonData;
import com.khunglong.xanh.json.PageData;
import com.khunglong.xanh.myfacebook.FbCommentsLoader;
import com.khunglong.xanh.myfacebook.FbUserLoader;
import com.khunglong.xanh.myfacebook.object.FbCmtData;
import com.khunglong.xanh.myfacebook.object.FbCmtFrom;
import com.khunglong.xanh.myfacebook.object.FbComments;

public class CommentFragment extends BaseFragment {
	FilterLog log = new FilterLog("CommentFragment");

	private ListView cmtListview;
	private CmtAdapter cmtAdapter;
	private List<FbCmtData> cmtListData = new ArrayList<FbCmtData>();
	private View mEmpty;

	public static CommentFragment newInstance() {
		CommentFragment f = new CommentFragment();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.comment_fragment, container, false);
		cmtListview = (ListView) rootView.findViewWithTag("listview");
		mEmpty = (ViewGroup) rootView.findViewById(android.R.id.empty);
		inflater.inflate(R.layout.waiting, (ViewGroup) mEmpty, true);
		enableEmptyview(true);
		cmtAdapter = new CmtAdapter(getActivity(), cmtListData, false);
		cmtListview.setAdapter(cmtAdapter);
		cmtListview.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		resource = ResourceManager.getInstance();
		return rootView;
	}

	private PageData pageData;
	private int position = 1;
	ResourceManager resource;

	public void setData(PageData data) {
		this.pageData = data;
		controllerComments.load();
	}

	private Controller controllerComments = new Controller() {

		@Override
		public void load() {
			Bundle params = new Bundle();
			params.putBoolean("summary", true);
			// params.putString("filter", "toplevel");
			params.putInt("limit", 20);
			final String graphPath = pageData.getId() + "/comments";

			resource.getFbLoaderManager().load(new FbCommentsLoader(getActivity(), graphPath, params) {

				@Override
				public void onFbLoaderSuccess(FbComments entry) {
					// updateComments(entry, position);
					cmtListData.clear();
					List<FbCmtData> collection = entry.getData();
					cmtListData.addAll(collection);
					cmtAdapter.notifyDataSetChanged();
					enableEmptyview(false);
				}

				@Override
				public void onFbLoaderStart() {
					enableEmptyview(true);
				}

				@Override
				public void onFbLoaderFail(Throwable e) {
					enableEmptyview(true);
					log.e("log>>>" + "FbCommentsLoader onFbLoaderFail:" + e.toString());
				}
			});
		}
	};

	private void updateComments(final FbComments entry, final int position) {
		log.d("log>>>" + "==========updateComments position:" + position);

		// DragonData mDragonData = resource.getKlxData();
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
		// for (int i = 0; i < entry.getData().size(); i++) {
		//
		//
		// final int pos = i;
		//
		// String idFrom = entry.getData().get(i).getFrom().getId();
		// String graphPath = idFrom + "/picture";
		// Bundle params = new Bundle();
		// params.putBoolean("redirect", false);
		// params.putInt("width", 100);
		// mFbLoaderManager.load(new FbUserLoader(context, graphPath, params) {
		//
		// @Override
		// public void onFbLoaderSuccess(FbCmtFrom f) {
		// cmtListData.get(pos).getFrom().setSource(f.getSource());
		// cmtAdapter.notifyDataSetChanged();
		// }
		// @Override
		// public void onFbLoaderStart() {
		// }
		//
		// @Override
		// public void onFbLoaderFail(Throwable e) {
		// log.e("log>>>" + "FbUserLoader  onFbLoaderFail:" + e.toString());
		// }
		// });
		// }
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
