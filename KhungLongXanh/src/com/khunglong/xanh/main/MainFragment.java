package com.khunglong.xanh.main;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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

public class MainFragment extends BaseFragment implements OnPageChangeListener, OnBackPressListener {

	private static final String TAG = "MainFragment";
	private static final int ILOAD = 10;
	private MainPagerAdapter mMainPagerAdapter;
	private InfinitePagerAdapter wrappedAdapter;
	private InfiniteViewPager mViewPager;
	private ActionBar mActionBar;
	private FilterLog log = new FilterLog(TAG);

	private String[] pageTitle;

	private IMainFragmentListener listener;

	private DragonData dragonData = new DragonData();
	private ViewGroup mEmpty;
	private boolean isLoading = false;
	private FbLoaderManager mFbLoaderManager;
	private MyApplication app;
	private Context context;
	private String albumId;
	private boolean isFirstLoad = true;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = (ViewGroup) inflater.inflate(R.layout.main_fragment, container, false);
		context = getActivity().getApplicationContext();
		mEmpty = (ViewGroup) rootView.findViewById(android.R.id.empty);
		mEmpty.setVisibility(View.VISIBLE);
		inflater.inflate(R.layout.waiting, mEmpty, true);
		log.d("log>>>" + "onCreateView");
		// Set up the action bar.
		mActionBar = getActivity().getActionBar();

		mMainPagerAdapter = new MainPagerAdapter(getFragmentManager(), getActivity());

		mViewPager = (InfiniteViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setStart(0);
		wrappedAdapter = new InfinitePagerAdapter(mMainPagerAdapter);
		mViewPager.setAdapter(wrappedAdapter);
		mViewPager.setOnPageChangeListener(this);

		app = (MyApplication) getActivity().getApplication();
		mFbLoaderManager = app.getmFbLoaderManager();
		if (listener != null) {
			listener.onIMainFragmentStart(MainFragment.this, 10, null);
		}
		mController.load();
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
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
			log.v("log>>>" + "controllerPhoto LOAD after:" + after);
			final String graphPath = albumId + "/photos";
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
					log.e("log>>>" + "controllerPhoto onFbLoaderFail:" + e.toString());

				}
			});
		}
	};

	Controller mController = new Controller() {

		@Override
		public void load() {
			isLoading = true;

			mFbLoaderManager.load(new FbLoaderAlbumsList(context, app.getId()) {

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

							// String after = dragonData.getPaging().getCursors().after;
							// Bundle params = new Bundle();
							// params.putString("after", after);
							// mFbLoaderManager.load(new FbPageLoader(context, element.getId() + "/photos", params) {
							//
							// @Override
							// public void onFbLoaderSuccess(DragonData entry) {
							// dragonData = entry;
							// mEmpty.setVisibility(View.GONE);
							// log.d("log>>>" + "load Image:" + entry.getData().size());
							// DetailsFragment f1 = (DetailsFragment) mMainPagerAdapter.getFragment(mViewPager, 0);
							// //set id of photos to details
							// f1.setId(entry.getData().get(0).getId(), 0);
							// isLoading = false;
							// }
							//
							// @Override
							// public void onFbLoaderStart() {
							// mEmpty.setVisibility(View.VISIBLE);
							//
							// }
							//
							// @Override
							// public void onFbLoaderFail(Throwable e) {
							// mEmpty.setVisibility(View.GONE);
							//
							// }
							// });
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
	
	

}
