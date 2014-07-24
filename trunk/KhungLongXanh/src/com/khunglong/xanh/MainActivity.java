package com.khunglong.xanh;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.facebook.LoginActivity;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.khunglong.xanh.base.BaseFragmentActivity;
import com.khunglong.xanh.json.DragonData;
import com.khunglong.xanh.login.LoginFragment;
import com.khunglong.xanh.login.MyLoginActivity;
import com.khunglong.xanh.login.LoginFragment.ILoginFragmentListener;
import com.khunglong.xanh.main.MainFragment;
import com.khunglong.xanh.main.MainFragment2;
import com.khunglong.xanh.main.MainFragment3;
import com.khunglong.xanh.main.drawer.DrawerItemGenerator.DrawerItem;
import com.khunglong.xanh.main.drawer.FragmentChangeDrawerItem;
import com.khunglong.xanh.main.drawer.PageChangeDrawerItem;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.khunglong.xanh.myfacebook.FbMeLoader;
import com.khunglong.xanh.myfacebook.FbUserLoader;
import com.khunglong.xanh.myfacebook.object.FbCmtFrom;
import com.khunglong.xanh.myfacebook.object.FbMe;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends BaseFragmentActivity implements ILoginFragmentListener {
	private static final String TAG = "MainActivity";
	FilterLog log = new FilterLog(TAG);
	public DragonData mDragonData;
	public MyApplication app;
	public UiLifecycleHelper uiHelper;

	// drawer
	protected DrawerLayout mDrawerLayout;
	protected ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private final Handler mHandler = new Handler();
	private static final long DELAY_ON_DRAWER_CLICK = 250L;

	// avatar drawer
	private ImageView imgAvatar;
	private TextView txtName;
	
	//

	@Override
	protected Fragment createFragmentMain(Bundle savedInstanceState) {
		// return new LoginFragment();
		// return TestFragment.newInstance("khunglongxanhvietnam", 0);
		return MainFragment.newInstance("khunglongxanhvietnam");
	}

	@Override
	protected int getFragmentContentId() {
		return R.id.login_parent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		log.d("log>>>" + "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		 uiHelper = new UiLifecycleHelper(this, callback);
		    uiHelper.onCreate(savedInstanceState);
		app = (MyApplication) getApplication();
		mDragonData = app.getDragonData();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.user_info, 0) {
			@Override
			public boolean onOptionsItemSelected(MenuItem item) {
				return super.onOptionsItemSelected(item);
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		View footer = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
		        R.layout.menu_list_drawer_footer, null);
		mDrawerList.addFooterView(footer, null, false);

		LoginButton authButton = (LoginButton) footer.findViewById(R.id.authButtonLogout);
		View headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
		        R.layout.fragment_profile_drawer_item, null);
		
//		 authButton.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				startActivity(new Intent(MainActivity.this, LoginActivity.class));
//				finish();
//			}
//		});
//		    authButton.setReadPermissions(Arrays.asList("email", "user_likes", "user_status"));
//		    authButton.setFragment(this);
		mDrawerList.addHeaderView(headerView, null, false);
		
		ImageView imgProfile = (ImageView) headerView.findViewWithTag("img_button_profile");
		ImageView imgNew = (ImageView) headerView.findViewWithTag("img_button_new");
		imgAvatar = (ImageView) headerView.findViewWithTag("img_hexagon");
		txtName = (TextView) headerView.findViewWithTag("drawer_profile_name");
		// imgAvatar.setImageBitmap(BitmapUtils.maskBitmap(this, R.drawable.taylor_swift, R.drawable.hexagon_view2));

		mDrawerList.setAdapter(getDrawerAdapter());
		mDrawerList.setOnItemClickListener(itemClickListener);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	// TODO login listener
	@Override
	public void onLogin(LoginFragment f, Session session) {

		MainFragment f1 = new MainFragment();
		showFragment(f1, true);

		// me request
		FbLoaderManager fbLoaderManager = app.getmFbLoaderManager();
		String graphPath = "me/picture";
		Bundle params = new Bundle();
		params.putBoolean("redirect", false);
		params.putInt("width", 500);
		fbLoaderManager.load(new FbUserLoader(getApplicationContext(), graphPath, params) {

			@Override
			public void onFbLoaderSuccess(FbCmtFrom entry) {
				log.d("log>>>" + "FbUserLoader onFbLoaderSuccess");
				ImageLoader imageLoader = ImageLoader.getInstance();

				String uri = entry.getSource();
				imageLoader.displayImage(uri, imgAvatar, app.getOptionsCircle());
			}

			@Override
			public void onFbLoaderStart() {

			}

			@Override
			public void onFbLoaderFail(Throwable e) {
				log.e("log>>>" + "FbUserLoader onFbLoaderFail:" + e.toString());

			}
		});

		fbLoaderManager.load(new FbMeLoader(getApplicationContext(), "me", null) {

			@Override
			public void onFbLoaderSuccess(FbMe entry) {
				// TODO Auto-generated method stub
				txtName.setText(entry.getName());

			}

			@Override
			public void onFbLoaderStart() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFbLoaderFail(Throwable e) {
				// TODO Auto-generated method stub

			}
		});

		// getSupportFragmentManager().beginTransaction().replace(getFragmentContentId(), f1).commit();

	}

	@Override
	public void onLogout(LoginFragment f, Session session) {
		// TODO Auto-generated method stub

	}

	protected ListAdapter getDrawerAdapter() {
		final MyApplication app = (MyApplication) getApplication();
		return new DrawerAdapter(app.getDrawerItemGenerator().generateMain());
	}

	class DrawerAdapter extends ArrayAdapter<DrawerItem<?>> {
		public DrawerAdapter(List<DrawerItem<?>> objects) {
			super(MainActivity.this, 0, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getItem(position).getView(getLayoutInflater(), convertView, parent);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	/**
	 * item left-drawer click
	 */
	OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			final FragmentManager fm = getSupportFragmentManager();
			final FragmentTransaction ft = fm.beginTransaction();
			if (parent.getId() == R.id.left_drawer) {

				// close drawer
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						mDrawerLayout.closeDrawer(mDrawerList);
					}
				}, DELAY_ON_DRAWER_CLICK);

				final DrawerItem<?> item = (DrawerItem<?>) mDrawerList.getAdapter().getItem(position);
				if (item instanceof PageChangeDrawerItem) {

					String page = ((PageChangeDrawerItem) item).getParam();
					while (mFragmentTagStack.size() > 0) {
						fm.popBackStackImmediate();
						log.d("log>>>" + "fm.popBackStackImmediate()");
					}
					// log.d("log>>>" + "f instanceof MainFragment:" + mFragmentTagStack.size() +"tag:" + mFragmentTagStack.peek());

					// 1
					if (page.equals("khunglongxanhvietnam")) {
						setCurrentPosition(0);
						MainFragment ftest = (MainFragment) fm.findFragmentByTag(FRAGMENT_KEY);
						ft.show(ftest);
						if (fm.findFragmentByTag("f2") != null) {
							ft.hide(fm.findFragmentByTag("f2"));
						}

						if (fm.findFragmentByTag("f3") != null) {
							ft.hide(fm.findFragmentByTag("f3"));
						}
						ft.commit();

						// 2
					} else if (page.equals("haivl.com")) {
						setCurrentPosition(1);
						final Fragment ff = fm.findFragmentByTag(FRAGMENT_KEY);
						ft.hide(ff);

						// hide f3

						if (fm.findFragmentByTag("f3") != null) {
							ft.hide(fm.findFragmentByTag("f3"));
						}

						MainFragment2 f2;
						if (fm.findFragmentByTag("f2") == null) {
							log.d("log>>>" + "	ft.show(f2);");
							f2 = MainFragment2.newInstance(page);
							// f2 = new MainFragment3();
							ft.add(getFragmentContentId(), f2, "f2");
							ft.show(f2);
						} else {
							f2 = (MainFragment2) fm.findFragmentByTag("f2");
							ft.show(f2);
						}
						ft.commit();

					} else if (page.equals(MsConstant.PAGE_3)) {
						setCurrentPosition(2);

						// hide f1
						final Fragment ff = fm.findFragmentByTag(FRAGMENT_KEY);
						ft.hide(ff);

						// hide f2
						if (fm.findFragmentByTag("f2") != null) {
							ft.hide(fm.findFragmentByTag("f2"));

						}

						// show f3
						if (fm.findFragmentByTag("f3") == null) {
							// Fragment f3 = TestFragment.newInstance(page, 2);
							Fragment f3 = MainFragment3.newInstance(page);
							ft.add(getFragmentContentId(), f3, "f3");
							ft.show(f3);
							// ft.addToBackStack(null);
						} else {
							ft.show(fm.findFragmentByTag("f3"));
						}
						ft.commit();
					}

					log.d("log>>>" + "stack:" + mFragmentTagStack.size());

					// MainFragment fmain = (MainFragment) fm.findFragmentByTag(mFragmentTagStack.peek());
					// // fmain.changeViewPager(0);
					// fmain.load(page, 0);

					// Fragment f = ((FragmentChangeDrawerItem) item).getParam();
					// if (f instanceof MainFragment) {
					// log.d("log>>>" + "f instanceof MainFragment");
					// while (mFragmentTagStack.size() > 1) {
					// fm.popBackStackImmediate();
					// log.d("log>>>" + "fm.popBackStackImmediate()");
					// }
					// log.d("log>>>" + "f instanceof MainFragment:" + mFragmentTagStack.size());
					// // MainFragment fmain = (MainFragment) fm.findFragmentByTag(FRAGMENT_KEY);
					// MainFragment fmain = (MainFragment) fm.findFragmentByTag(mFragmentTagStack.get(1));
					// fmain.changeViewPager(0);
					// } else {
					// log.d("log>>>" + "showFragment");
					// while (mFragmentTagStack.size() > 0) {
					// fm.popBackStackImmediate();
					// log.d("log>>>" + "fm.popBackStackImmediate()");
					// }
					// showFragment(f, false);
					// }

				} else if (item instanceof FragmentChangeDrawerItem) {
					showFragment((Fragment) item.getParam(), true);
				}
			}

		}
	};

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        log.d(">>>Logged in...:" +session.getAccessToken());
	      
	    } else if (state.isClosed()) {
	    	  log.d(">>>Logged out...");
	    	  startActivity(new Intent(getApplicationContext(), MyLoginActivity.class));
	    	  MainActivity.this.finish();
	    	  
	    }
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}

}
