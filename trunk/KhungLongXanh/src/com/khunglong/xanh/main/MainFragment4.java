package com.khunglong.xanh.main;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.MsConstant;
import com.khunglong.xanh.MyApplication;
import com.khunglong.xanh.R;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.base.BaseFragmentActivity.OnBackPressListener;
import com.khunglong.xanh.base.Controller;
import com.khunglong.xanh.base.MyFragmentPagerAdapter;
import com.khunglong.xanh.json.DragonData;
import com.khunglong.xanh.libs.InfinitePagerAdapter;
import com.khunglong.xanh.libs.InfiniteViewPager;
import com.khunglong.xanh.myfacebook.FbLoaderAlbumsList;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.khunglong.xanh.myfacebook.FbPageLoader;
import com.khunglong.xanh.myfacebook.object.FbAlbums;
import com.khunglong.xanh.myfacebook.object.FbAlbumsDto;
import com.khunglong.xanh.utils.ActionBarUtils;

public class MainFragment4 extends BaseFragment implements OnPageChangeListener, OnBackPressListener {

	private static final String TAG = "MainFragment2";
	private static final int ILOAD = 10;
	private MainPagerAdapter2 mMainPagerAdapter;
	private InfinitePagerAdapter wrappedAdapter;
	private InfiniteViewPager mViewPager;
	private ActionBar mActionBar;
	private FilterLog log = new FilterLog(TAG);

	private String[] pageTitle;

	private IMainFragmentListener listener;

	private DragonData dragonData ;
	private ViewGroup mEmpty;
	private boolean isLoading = false;
	private FbLoaderManager mFbLoaderManager;
	private MyApplication app;
	private Context context;
	private String albumId;
	private boolean isFirstLoad = true;
	private String page;

	public static interface IMainFragmentListener {
		void onIMainFragmentStart(MainFragment4 f, int i, BaseObject link);

		void onMainFragmentPageSelected(MainFragment4 main, Fragment selected, BaseObject link);

		void onMainFragmentPageDeSelected(MainFragment4 main, Fragment selected, BaseObject link);
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

	public static MainFragment4 newInstance(String page) {
		MainFragment4 f = new MainFragment4();
		Bundle bundle = new Bundle();
		bundle.putString("page", page);
		f.setArguments(bundle);
		return f;
	}

	public MainFragment4() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity().getApplicationContext();
		app = (MyApplication) getActivity().getApplication();
		dragonData = new DragonData();
		app.getmListDragonDatas().add(dragonData); 
//		if (app.getmListDragonDatas().size() == 1) {
//			log.d("log>>>" + "add new dragonData");
//		} else {
//			dragonData = app.getmListDragonDatas().get(1);
//			sl
//		}
		
		setHasOptionsMenu(true);

		if (getArguments() != null) {
			Bundle bundle = getArguments();
			page = bundle.getString("page");
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = (ViewGroup) inflater.inflate(R.layout.main_fragment4, container, false);
		context = getActivity().getApplicationContext();
		mEmpty = (ViewGroup) rootView.findViewById(android.R.id.empty);
		mEmpty.setVisibility(View.VISIBLE);
		inflater.inflate(R.layout.waiting, mEmpty, true);
		log.d("log>>>" + "onCreateView3");
		// Set up the action bar.
		mActionBar = getActivity().getActionBar();

		mMainPagerAdapter = new MainPagerAdapter2(getFragmentManager(), getActivity());

		mViewPager = (InfiniteViewPager) rootView.findViewById(R.id.pager4);
		mViewPager.setStart(0);
		wrappedAdapter = new InfinitePagerAdapter(mMainPagerAdapter);
		mViewPager.setAdapter(wrappedAdapter);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setPageTransformer(true, new DepthPageTransformer());

		
		mFbLoaderManager = app.getmFbLoaderManager();
		if (listener != null) {
			listener.onIMainFragmentStart(MainFragment4.this, 10, null);
		}
		
		if (TextUtils.isEmpty(page)) {
			page = app.getId();
		}
		
		load(page, 0);
		// mController.load();
		return rootView;
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

	public void update() {
	}

	// Controller mController = new Controller() {
	//
	// @Override
	// public void load() {
	// isLoading = true;
	// String pathLoad = "KhungLongXanhVietNam/feed";
	// log.d("log>>>" + "pathLoad:" + pathLoad);
	// Request request = Request.newGraphPathRequest(Session.getActiveSession(), pathLoad, facebookCallback);
	// if (dragonData.getPaging() != null) {
	// Paging paging = dragonData.getPaging();
	// String next = paging.getNext();
	// log.d("log>>>" + ">>>>>>>>>>>>>>>>>>>>>>>>>next:" + next);
	// Bundle bundle = new Bundle();
	// List<NameValuePair>list1 = URLEncodedUtils.parse(URI.create(next), "UTF-8");
	// log.d("log>>>" + "list1:" + list1.size());
	// for (NameValuePair nameValuePair : list1) {
	// log.d("log>>>" + "name:" + nameValuePair.getName() + ";value:" +nameValuePair.getValue());
	// if (nameValuePair.getName().equalsIgnoreCase("limit")) {
	// bundle.putString("limit", nameValuePair.getValue());
	// }
	// if (nameValuePair.getName().equalsIgnoreCase("until")) {
	// bundle.putString("until", nameValuePair.getValue());
	// }
	// }
	// request.setParameters(bundle);
	// }
	// Request.executeBatchAsync(request);
	// }
	// };
	Controller controllerPhoto = new Controller() {

		@Override
		public void load() {
			isLoading = true;
			String after = dragonData.getPaging().getCursors().after;
			Bundle params = new Bundle();
			params.putString("after", after);
			final String graphPath = albumId + "/photos";
			log.v("log>>>" + "controllerPhoto LOAD after:" + after + ";graphPath>:" + graphPath);
			mFbLoaderManager.load(new FbPageLoader(context, graphPath, params) {

				@Override
				public void onFbLoaderSuccess(DragonData entry) {
					log.d("log>>>" + "FbPageLoader onFbLoaderSuccess:graphPath:" + graphPath + ";load Image:"
					        + entry.getData().size());
					dragonData.setPaging(entry.getPaging());
					dragonData.getData().addAll(entry.getData());
					mEmpty.setVisibility(View.GONE);
					
					DetailsFragment f1 = (DetailsFragment) mMainPagerAdapter.getFragment(mViewPager, 0);
					// set id of photos to details
					if (isFirstLoad) {
						// f1.setId(entry.getData().get(0).getId(), 0);
						f1.setData(entry.getData().get(0), 0);
						isFirstLoad = false;
					}
					isLoading = false;
				}

				@Override
				public void onFbLoaderStart() {
					mEmpty.setVisibility(View.VISIBLE);

				}

				@Override
				public void onFbLoaderFail(Throwable e) {
					mEmpty.setVisibility(View.GONE);
					log.e("log>>>" + "controllerPhoto onFbLoaderFail:" + e.toString() + ";graphPath:" + graphPath);

				}
			});
		}
	};

	Controller mController = new Controller() {

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
					for (final FbAlbumsDto element : entry.listFbAlbumsDto) {
						if (element.getName().equalsIgnoreCase("Timeline Photos")) {
							albumId = element.getId();
							controllerPhoto.load();
						}
					}
				}

				@Override
				public void onFbLoaderStart() {
					mEmpty.setVisibility(View.VISIBLE);
				}

				@Override
				public void onFbLoaderFail(Throwable e) {
					mEmpty.setVisibility(View.GONE);
					log.e("log>>>" + "mController onFbLoaderFail:" + e.toString());
				}
			});
		}
	};

	/*
	 * Request.Callback facebookCallback = new Request.Callback() { //TODO complete
	 * 
	 * @Override public void onCompleted(Response response) {
	 * 
	 * log.d("log>>>" + "onCompleted"); // log.d("log>>>" + "response:" + response); mEmpty.setVisibility(View.GONE); GraphMultiResult
	 * graphMultiResult = response.getGraphObjectAs(GraphMultiResult.class); File f = new File(Environment.getExternalStorageDirectory(),
	 * "log/KhungLongXanhlog.txt"); FileUtils.write(f.getPath(), "===================================", false); //get previous and next data
	 * Object g2 = graphMultiResult.getProperty("paging"); Paging paging ; Gson gson = new Gson(); paging = gson.fromJson(g2.toString(),
	 * Paging.class); dragonData.setPaging(paging);
	 * 
	 * // JSONObject json = graphMultiResult.getInnerJSONObject(); //get data GraphObjectList<GraphObject> list =
	 * graphMultiResult.getData(); log.d("log>>>" + "list GraphObject:" + list.size()); Data mData; int i = 0; for (GraphObject item : list)
	 * { JSONObject abc = item.getInnerJSONObject(); // FileUtils.write(f.getPath(), j++ + "abc:" + abc.toString(), true); mData = new
	 * Data(); String type = (String) item.getProperty("type"); if (!TextUtils.isEmpty(type) && type.equals("photo")) { String snapshot_img
	 * = (String) item.getProperty("picture"); // snapshot_img = DragonStringUtils.parseImageString2(snapshot_img);
	 * mData.setPicture(snapshot_img); String message = (String) item.getProperty("message"); String created_time = (String)
	 * item.getProperty("created_time"); mData.setType(type); mData.setCreated_time(created_time); mData.setMessage(message); FbComments
	 * comments; try { if (abc.has("comments")){ JSONObject jComments = abc.getJSONObject("comments"); // JSONArray jsonArray =
	 * jComments.getJSONArray("datca"); // log.d("log>>>" + "jsonArray:" + jsonArray.length()); //gson parse comments =
	 * gson.fromJson(jComments.toString(), FbComments.class); log.d("log>>>" + "comments size of i:" + i + " is:" + comments.data.size());
	 * for (CmtData cmtdata : comments.data) { log.d("log>>>" + i+ " name:" + cmtdata.from.name + ";message:" + cmtdata.message + ";id:"
	 * +cmtdata.from.id ); } } else { log.d("log>>>" + "i ==" + i + " has no comments"); comments = new FbComments(); }
	 * mData.setComments(comments);
	 * 
	 * } catch (Exception e) { log.d("log>>>" + "error cmt:" + e.toString()); } data.add(mData); }
	 * 
	 * i++;
	 * 
	 * } dragonData.setData(data); int j = 0; for (Data d1 : dragonData.getData()) { log.d("log>>>" + "----------Comment:" + j +
	 * "============"); j ++; int k = 0; for (CmtData c1 : d1.getComments().data) { log.d("log>>>" + "message:" + k + ":" + c1.message +
	 * ";user:" + c1.from.name +";id:" + c1.from.id + ";id2:" + c1.id); k++; } }
	 * 
	 * 
	 * 
	 * DetailsFragment f1 = (DetailsFragment) mMainPagerAdapter.getFragment(mViewPager, 0); // f1.setData(dragonData.getData().get(0));
	 * List<CmtData> list1 = dragonData.getData().get(0).getComments().data; //// log.d("log>>>" + "list1:" + list1.size()); // for (CmtData
	 * cmtData : list1) { // log.d("log>>>" + "cmtData:" + cmtData.message + ";user:" + cmtData.from.name); // }
	 * 
	 * 
	 * f1.setDataList(list1); f1.notifyDataSetChanged();
	 * 
	 * isLoading = false;
	 * 
	 * 
	 * } };
	 */

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
	public void onPageSelected(int arg0) {
		int virtualPosition = arg0 % (mMainPagerAdapter.getCount());
		if (arg0 > dragonData.getData().size() - 1) {
			return;
		}
		DetailsFragment f1 = (DetailsFragment) mMainPagerAdapter.getFragment(mViewPager, virtualPosition);
		// f.setData(dragonData.getData().get(arg0));
		// f.setId(dragonData.getData().get(arg0).getId(), arg0);
		f1.setData(dragonData.getData().get(arg0), arg0);
		// List<CmtData> list1 = dragonData.getData().get(arg0).getComments().data;
		// f.setDataList(list1);
		if ((dragonData.getData().size() - arg0) <= ILOAD && !isLoading) {
			// mController.load();
			controllerPhoto.load();
		}
	}

	// adapter

	class MainPagerAdapter2 extends MyFragmentPagerAdapter {

		Context mContext;
		private Fragment mPrimaryFragment;

		public MainPagerAdapter2(FragmentManager fm, Context context) {
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
			return new DetailsFragment(position, null);
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

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (hidden) {
			setMenuVisibility(false);
			if (mMainPagerAdapter != null && mMainPagerAdapter.getCurrentFragment() != null) {
				mMainPagerAdapter.getCurrentFragment().setMenuVisibility(false);
			}
		} else {
			setMenuVisibility(true);
			if (mMainPagerAdapter != null && mMainPagerAdapter.getCurrentFragment() != null) {
				mMainPagerAdapter.getCurrentFragment().setMenuVisibility(true);
			}
			if (mActionBar != null) {
				// ActionBarUtils.setTitle(mActionBar, getApplicationTitle(mViewPager.getCurrentItem()));
				// mActionBar.setTitle(getApplicationTitle(mViewPager.getCurrentItem()));
				update();
			}
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

	public void load(String page, int pos) {
		log.d("log>>>" + "load:" + page);
		this.page = page;
		mController.load();
	}

}
