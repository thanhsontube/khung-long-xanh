package com.khunglong.xanh.comments;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.MyApplication;
import com.khunglong.xanh.R;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.base.Controller;
import com.khunglong.xanh.myfacebook.FbCommentsLoader;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.khunglong.xanh.myfacebook.FbUserLoader;
import com.khunglong.xanh.myfacebook.object.FbCmtData;
import com.khunglong.xanh.myfacebook.object.FbCmtFrom;
import com.khunglong.xanh.myfacebook.object.FbComments;

/**
 * answer of cmt
 * 
 * @author sonnt
 * 
 */
public class AnswerFragment extends BaseFragment {
	private static final String TAG = "AnswerFragment";
	FilterLog log = new FilterLog(TAG);
	private TextView txtTitle;
	private TextView txtName;
	private ListView cmtListview;
	private CmtAdapter cmtAdapter;
	private List<FbCmtData> cmtListData;
	private Context context;
	private FbLoaderManager mFbLoaderManager;
	private MyApplication app;
	private String id;
	private int position;
	private int cmtPos;
	
	private String title;
	private String name;
	private ViewGroup mEmpty;
	
	public static AnswerFragment newInstance(String title, String name, String id, int position, int cmtPos) {
		AnswerFragment f = new AnswerFragment();
		Bundle bundle = new Bundle();
		bundle.putString("title", title);
		bundle.putString("name", name);
		bundle.putString("id", id);
		bundle.putInt("position", position);
		bundle.putInt("cmtPos", cmtPos);
		f.setArguments(bundle);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity().getApplicationContext();
		app = (MyApplication) getActivity().getApplication();
		mFbLoaderManager = app.getmFbLoaderManager();
		
		Bundle bundle = getArguments();
		title = bundle.getString("title");
		name = bundle.getString("name");
		id = bundle.getString("id");
		position = bundle.getInt("position");
		cmtPos = bundle.getInt("cmtPos");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.answer_fragment, container, false);
		mEmpty = (ViewGroup) rootView.findViewById(android.R.id.empty);
		mEmpty.setVisibility(View.VISIBLE);
		inflater.inflate(R.layout.waiting, mEmpty, true);
		txtTitle = (TextView) rootView.findViewWithTag("title");
		txtName = (TextView) rootView.findViewWithTag("name");
		
		txtTitle.setText(title);
		txtName.setText(name);
		cmtListview = (ListView) rootView.findViewById(R.id.answer_lv);
		cmtListData = new ArrayList<FbCmtData>();
		cmtAdapter = new CmtAdapter(context, cmtListData, true);
		cmtListview.setAdapter(cmtAdapter);
		controllerComments.load();
		return rootView;
	}

	private Controller controllerComments = new Controller() {

		@Override
		public void load() {
			mEmpty.setVisibility(View.VISIBLE);
			Bundle params = new Bundle();
			params.putBoolean("summary", true);
			params.putString("filter", "toplevel");
			final String graphPath = id + "/comments";
			mFbLoaderManager.load(new FbCommentsLoader(context, graphPath, params) {

				@Override
				public void onFbLoaderSuccess(FbComments entry) {
					mEmpty.setVisibility(View.GONE);
					log.d("log>>>" + "AnswerFragment FbCommentsLoader onFbLoaderSuccess entry:" + entry.getData().size()
					        +  ";graphPath:" + graphPath);
					updateComments(entry, position);
				}

				@Override
				public void onFbLoaderStart() {

				}

				@Override
				public void onFbLoaderFail(Throwable e) {
					log.e("log>>>" + "FbCommentsLoader onFbLoaderFail:" + e.toString());
				}
			});
		}
	};
	
	private void updateComments(FbComments entry, int position) {
		log.d("log>>>" + "==========updateComments position:" + position);
		cmtListData.clear();
		List<FbCmtData> collection = entry.getData();
		cmtListData.addAll(collection);
		cmtAdapter.notifyDataSetChanged();
		for (int i = 0; i < entry.getData().size(); i++) {
			final int pos = i;

			String idFrom = entry.getData().get(i).getFrom().getId();
			String graphPath = idFrom + "/picture";
			Bundle params = new Bundle();
			params.putBoolean("redirect", false);
			params.putInt("width", 100);
			mFbLoaderManager.load(new FbUserLoader(context, graphPath, params) {

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
					log.e("log>>>" + "FbUserLoader onFbLoaderFail:" + e.toString());
				}
			});
		}
	}
}
