package com.khunglong.xanh.main;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.khunglong.xanh.MyApplication;
import com.khunglong.xanh.R;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.base.Controller;
import com.khunglong.xanh.comments.AnswerFragment;
import com.khunglong.xanh.comments.CmtAdapter;
import com.khunglong.xanh.json.Data;
import com.khunglong.xanh.json.DragonData;
import com.khunglong.xanh.login.LoginActivity;
import com.khunglong.xanh.main.MainFragment.IMainFragmentListener;
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

public class DetailsFragment extends BaseFragment implements IMainFragmentListener {
	FilterLog log = new FilterLog("DetailsFragment");
	ImageLoader imageLoader;
	private DisplayImageOptions optionsAvatar;
	private DisplayImageOptions optionsContent;
	private String link;
	private Holder holder;
	private int position;

	private ListView cmtListview;
	private CmtAdapter cmtAdapter;
	private List<FbCmtData> cmtListData;
	private Data data;
	private FbLoaderManager mFbLoaderManager;
	private MyApplication app;
	private Context context;
	private DragonData mDragonData;

	public DetailsFragment(int pos, String link) {
		this.link = link;
		this.position = pos;
	}

	// public void setDataList(List<CmtData> objects) {
	// cmtListData.clear();
	// cmtListData.addAll(objects);
	// cmtAdapter.notifyDataSetChanged();
	// }

	// public void notifyDataSetChanged(){
	// cmtAdapter.notifyDataSetChanged();
	// }

	// public void setId(String id, int position) {
	// log.d("log>>>" + "setId:" + id + ";size:" + mDragonData.getData().size() + ";position:" + position);
	// this.id = id;
	// this.position = position;
	// if (mDragonData.getData().size() <= position) {
	// restoreLayout();
	// mController.load();
	// } else {
	// updateLayout(mDragonData.getData().get(position));
	// }
	// }

	public void setData(Data data, int position) {
		log.d("log>>>" + "setData position:" + position + ";size: " + mDragonData.getData().size());
		this.data = data;
		this.position = position;

		if (mDragonData.getData().size() == position) {
			mDragonData.getData().add(data);
			updateLayoutPage(data);
		}

		// if (cmtAdapter == null) {
		// cmtAdapter = new CmtAdapter(context, mDragonData.getData().get(position).getComments().getData());
		// log.d("log>>>" + "CREATW NEW cmtAdapter:" + cmtAdapter);
		// cmtListview.setAdapter(cmtAdapter);
		// }

		// cmtListData.clear();
		// List<FbCmtData> collection = mDragonData.getData().get(position).getComments().getData();
		// cmtListData.addAll(collection);
		// cmtAdapter.notifyDataSetChanged();
		// if (mDragonData.getData().size() <= position) {
		// restoreLayout();
		// mController.load();
		// } else {
		// }
	}

	// private void restoreLayout() {
	// holder.createdName.setText("");
	// holder.snapshotContent.setVisibility(View.GONE);
	// holder.title.setText("");
	// // holder.answerUserName.setText("");
	// imageLoader.displayImage(null, holder.snapshotImg, optionsContent,null);
	// // holder.snapshotImg.setBackgroundResource(R.drawable.iviet_temp);
	// }

	private void updateLayoutPage(Data base) {
		log.d("log>>>" + "updateLayoutPage mDragonData.getData().size():" + mDragonData.getData().size() + ";position:"
		        + position);
		holder.createdName.setText(base.getCreated_time());
		holder.snapshotContent.setVisibility(View.GONE);
		holder.title.setText(base.getName());
		holder.answerUserName.setText(base.getType());
		imageLoader.displayImage(base.getSource(), holder.snapshotImg, optionsContent, null);

		// if(mDragonData.getData().size() <= position) {
		// log.d("log>>>" + "Load OLD");
		// holder.voteUps.setText(String.valueOf(base.getLikes().getSummary().getTotal_count()));
		// holder.numberAnswers.setText(String.valueOf(base.getComments().getSummary().getTotal_count()));
		//
		// cmtListData.clear();
		// FbComments entry = base.getComments();
		// List<FbCmtData> collection = entry.getData();
		// cmtListData.addAll(collection);
		// cmtAdapter.notifyDataSetChanged();
		// } else {
		// log.d("log>>>" + "Load news");
		// controllerLikes.load();
		// controllerComments.load();
		// }

		controllerLikes.load();
		controllerComments.load();
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
		mDragonData = app.getDragonData();
	}

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
		holder.snapshotImg.setBackgroundResource(R.drawable.iviet_temp);

		// list
		// comments
		cmtListview = (ListView) v.findViewById(R.id.listview_comment);
		cmtListData = new ArrayList<FbCmtData>();
		cmtAdapter = new CmtAdapter(context, cmtListData, false);
		// cmtAdapter = new CmtAdapter(context, mDragonData.getData().get(position).getComments().getData());
		// log.d("log>>>" + "cmtAdapter:" + cmtAdapter);
		cmtListview.setAdapter(cmtAdapter);

		cmtListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				LoginActivity loginActivity = (LoginActivity) getActivity();
				AnswerFragment f = AnswerFragment.newInstance(cmtListData.get(pos).getMessage(), cmtListData.get(pos)
				        .getFrom().getName(), cmtListData.get(pos).getId(), position, pos);
				loginActivity.showFragment(f, true);

			}
		});

		// BaseObject base = new
		// BaseObject("user_avatar"," user_name"," created_on"," answer_user_name"," recent_answer_date"," snapshot_img"," title"," snapshot_content",1,2,3," user_id"," topic_icon"," topic_name"," theme_color"," is_anonymous"," question_id");

		// holder.userName.setText(base.user_name);
		//
		// holder.answerUserName.setText(base.answer_user_name);
		// if(base.getNumber_answers() < 1) {
		// holder.answerUserName.setVisibility(View.GONE);
		// holder.recentAnswerDate.setVisibility(View.GONE);
		// }
		// holder.title.setText(base.title);
		// holder.snapshotContent.setText(base.snapshot_content);
		// holder.voteUps.setText(String.valueOf(base.vote_ups));
		// holder.numberAnswers.setText(String.valueOf(base.number_answers));
		// holder.numberViews.setText(String.valueOf(base.number_views));
		// holder.themColor.setBackgroundColor(Color.parseColor("#"+base.theme_color));
		// avatar

		imageLoader.displayImage(link, holder.snapshotImg, optionsContent, null);
		imageLoader
		        .displayImage(
		                "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xap1/t1.0-1/p480x480/10450426_558741767581283_8906675999024203968_n.jpg",
		                holder.userAvatar, optionsAvatar, null);

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
				// FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(getActivity())
				// .setLink("https://developers.facebook.com/android")
				// .setRef("574127096042750")
				// .build();
				//
				LoginActivity loginActivity = (LoginActivity) getActivity();
				// loginActivity.uiHelper.trackPendingDialogCall(shareDialog.present());
				String title = data.getName();
				String source = data.getSource();
				String id = data.getId();
				log.d("log>>>" + "title:" + title + ";id:" + id + ";source:" + source);
				MyCommentsFragment f = MyCommentsFragment.newInstance(title, source, id);
				loginActivity.showFragment(f, true);
			}
		});

		return v;
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

	@Override
	public void onIMainFragmentStart(MainFragment f, int i, BaseObject link) {
		// log.d("log>>>" + "onIMainFragmentStart:" + link);
		// if (TextUtils.isEmpty(link.getSnapshot_img())) {
		// return;
		// }
		// imageLoader.displayImage(link.getSnapshot_img(), holder.snapshotImg, optionsContent, null);

	}

	@Override
	public void onMainFragmentPageSelected(MainFragment main, Fragment selected, BaseObject link) {

	}

	@Override
	public void onMainFragmentPageDeSelected(MainFragment main, Fragment selected, BaseObject link) {

	}

	private Controller controllerComments = new Controller() {

		@Override
		public void load() {
			Bundle params = new Bundle();
			params.putBoolean("summary", true);
			params.putString("filter", "toplevel");
			params.putInt("limit", 50);
			final String graphPath = data.getId() + "/comments";
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
			String graphPath = data.getId() + "/likes";
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

	private void updateComments(final FbComments entry, final int position) {
		log.d("log>>>" + "==========updateComments position:" + position);
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

	private void updateLikes(FbLikes entry, int position) {
		mDragonData.getData().get(position).getLikes().setSummary(entry.getSummary());
		mDragonData.getData().get(position).getLikes().getData().addAll(entry.getData());
		mDragonData.getData().get(position).getLikes().setPaging(entry.getPaging());

		holder.voteUps.setText(String.valueOf(entry.getSummary().getTotal_count()));
	}
}
