package com.khunglong.xanh.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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

public class DetailsFragment extends BaseFragment   {
	FilterLog log = new FilterLog("DetailsFragment");
	ImageLoader imageLoader;
	private DisplayImageOptions optionsAvatar;
	private DisplayImageOptions optionsContent;
	private String link;
	private String linkHigh;
	private String content;
	private Holder holder;
	private int position;

	private ListView cmtListview;
	private CmtAdapter cmtAdapter;
	private List<FbCmtData> cmtListData;
	private PageData pageData;
	private FbLoaderManager mFbLoaderManager;
	private MyApplication app;
	private Context context;
//	private DragonData mDragonData;
	private int maxPosition = -1;
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

	public DetailsFragment(int pos, String link) {
		this.link = link;
		this.position = pos;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (MyApplication) getActivity().getApplication();
		imageLoader = ImageLoader.getInstance();
		optionsAvatar = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
		        .considerExifParams(true).displayer(new RoundedBitmapDisplayer(100)).build();
		optionsContent = app.getOptionsContent();
		mFbLoaderManager = app.getmFbLoaderManager();
		context = getActivity().getApplicationContext();
		
//		mDragonData = app.getmListDragonDatas().get(0);
	}
	//TODO onCreateView
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		log.d("log>>>" + "onCreateView");
		View v = inflater.inflate(R.layout.row_main, container, false);

		holder = new Holder();
		holder.userAvatar = (ImageView) v.findViewById(R.id.user_avatar);
		holder.userName = (TextView) v.findViewById(R.id.user_name);
		holder.createdName = (TextView) v.findViewById(R.id.created_on);
		holder.answerUserName = (TextView) v.findViewById(R.id.answer_user_name);
		holder.recentAnswerDate = (TextView) v.findViewById(R.id.recent_answer_date);
		holder.snapshotImg = (ImageView) v.findViewById(R.id.snapshot_img);
		holder.title = (TextView) v.findViewById(R.id.title);
		holder.snapshotContent = (TextView) v.findViewById(R.id.snapshot_content);
		holder.voteUps = (TextView) v.findViewById(R.id.vote_ups);
		holder.numberAnswers = (TextView) v.findViewById(R.id.number_answers);
		holder.numberViews = (TextView) v.findViewById(R.id.number_views);
		holder.themColor = (LinearLayout) v.findViewById(R.id.them_color2);
		holder.themColor.setEnabled(false);
		holder.snapshotImg.setBackgroundResource(R.drawable.iviet_temp);

		// list
		// comments
		cmtListview = (ListView) v.findViewById(R.id.listview_comment);
		cmtListData = new ArrayList<FbCmtData>();
		cmtAdapter = new CmtAdapter(context, cmtListData, false);
		cmtListview.setAdapter(cmtAdapter);
		cmtListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				MainActivity loginActivity = (MainActivity) getActivity();
				String link = cmtListData.get(pos).getFrom().getSource();
				AnswerFragment f = AnswerFragment.newInstance(cmtListData.get(pos).getMessage(), cmtListData.get(pos)
				        .getFrom().getName(),link, cmtListData.get(pos).getId(), position, pos);
				loginActivity.showFragment(f, true);

			}
		});

		imageLoader.displayImage(link, holder.snapshotImg, optionsContent, null);
		holder.snapshotImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Toast.makeText(getActivity(), "click:" + image, Toast.LENGTH_SHORT).show();
				listener.onDetailsFragmentPicture(linkHigh, content);
			}
		});
		holder.userAvatar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// baseTopFragment.mListener.onBaseTopAvatarClicked(baseTopFragment, base);
			}
		});
		holder.themColor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				log.d("log>>>" + "holder.themColor  click");
				MainActivity loginActivity = (MainActivity) getActivity();
				String title = pageData.getName();
				String source = pageData.getSource();
				String id = pageData.getId();
				log.d("log>>>" + "title:" + title + ";id:" + id + ";source:" + source);
				MyCommentsFragment f = MyCommentsFragment.newInstance(title, source, id);
				loginActivity.showFragment(f, true);
			}
		});

		return v;
	}

	public void setData(PageData data, int position) {
		log.d("log>>>" + "setData position:" + position +";" + data.getSource());
		this.pageData = data;
		this.position = position;
		
		updateLayoutPage();
		
//		if (maxPosition <= position) {
//			maxPosition = position;
//			updateLayoutPage(data);
//		}
		
		
//		if (mDragonData.getData().size() == position) {
//			mDragonData.getData().add(data);
//		}

	}

	private void updateLayoutPage() {
		holder.createdName.setText(pageData.getCreated_time());
		holder.snapshotContent.setVisibility(View.GONE);
		holder.title.setText(pageData.getName());
		holder.answerUserName.setText(pageData.getType());
		link = pageData.getSource();
		linkHigh = pageData.getSourceQuality();
		imageLoader.displayImage(linkHigh, holder.snapshotImg, optionsContent, null);
		content = pageData.getName();
		controllerLikes.load();
		controllerComments.load();
		holder.themColor.setEnabled(true);
	}

	

	

	static class Holder {
		public ImageView userAvatar;
		public TextView userName;
		public TextView createdName;
		public TextView answerUserName;
		public TextView recentAnswerDate;
		public ImageView snapshotImg;
		public TextView title;
		public TextView snapshotContent;
		public TextView voteUps;
		public TextView numberAnswers;
		public TextView numberViews;
		public LinearLayout themColor;

	}

	private Controller controllerComments = new Controller() {

		@Override
		public void load() {
			Bundle params = new Bundle();
			params.putBoolean("summary", true);
//			params.putString("filter", "toplevel");
			params.putInt("limit", 20);
			final String graphPath = pageData.getId() + "/comments";
			mFbLoaderManager.load(new FbCommentsLoader(context, graphPath, params) {

				@Override
				public void onFbLoaderSuccess(FbComments entry) {
					log.d("log>>>" + "FbCommentsLoader onFbLoaderSuccess entry:" + entry.getData().size()
					        + ";position:" + position + ";graphPath:" + graphPath);
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

	private Controller controllerLikes = new Controller() {

		@Override
		public void load() {
			Bundle params = new Bundle();
			params.putString("summary", "1");
			String graphPath = pageData.getId() + "/likes";
			mFbLoaderManager.load(new FbLikesLoader(context, graphPath, params) {

				@Override
				public void onFbLoaderSuccess(FbLikes entry) {
					log.d("log>>>" + "FbLikesLoader onFbLoaderSuccess entry:" + entry.getData().size());
					updateLikes(entry, position);
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
	
	/**
	 * 
	 * @param entry : Fbcmt of this picture at this position
	 * @param position : is position of picture
	 */

	private void updateComments(final FbComments entry, final int position) {
		log.d("log>>>" + "==========updateComments position:" + position);
		
		DragonData mDragonData = app.getmListDragonDatas().get(0);
		mDragonData.getData().get(position).getComments().setSummary(entry.getSummary());
		mDragonData.getData().get(position).getComments().getData().addAll(entry.getData());
		mDragonData.getData().get(position).getComments().setPaging(entry.getPaging());

		// for (int i = 0; i < 3; i ++) {
		// log.d("log>>>" + "message:" + entry.getData().get(i).getMessage());
		// log.d("log>>>" + "id:" + entry.getData().get(i).getFrom().getId() + ";name:" + entry.getData().get(i).getFrom().getName() +
		// "message:" + entry.getData().get(i).getMessage()) ;
		// }
		

		holder.numberAnswers.setText(String.valueOf(entry.getSummary().getTotal_count()));
		cmtListData.clear();
		List<FbCmtData> collection = entry.getData();
		cmtListData.addAll(collection);
		cmtAdapter.notifyDataSetChanged();
		
		//load avatar commenter
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
					log.e("log>>>" + "FbUserLoader  onFbLoaderFail:" + e.toString());
				}
			});
		}
	}
	/**
	 * update page like
	 * @param entry
	 * @param position of this page in list
	 */
	private void updateLikes(FbLikes entry, int position) {
		DragonData mDragonData = app.getmListDragonDatas().get(0);
		mDragonData.getData().get(position).getLikes().setSummary(entry.getSummary());
		mDragonData.getData().get(position).getLikes().getData().addAll(entry.getData());
		mDragonData.getData().get(position).getLikes().setPaging(entry.getPaging());

		holder.voteUps.setText(String.valueOf(entry.getSummary().getTotal_count()));
	}
}
