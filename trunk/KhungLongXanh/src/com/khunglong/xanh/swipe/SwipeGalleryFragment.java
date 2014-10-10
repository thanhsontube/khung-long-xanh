package com.khunglong.xanh.swipe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.R;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.base.MyFragmentPagerAdapter;
import com.khunglong.xanh.image.ImageFullZoomFragment;

public class SwipeGalleryFragment extends BaseFragment implements OnPageChangeListener {

	private static final String TAG = "SwipeGalleryFragment";
	private int position;
	List<String> pageTitle = new ArrayList<String>();
	private File[] fs;
	private ViewPager pager;
	FilterLog log = new FilterLog(TAG);
	private boolean isFavorite;

	private MainPagerAdapter adapter;

	public static SwipeGalleryFragment newInstance(int value) {
		SwipeGalleryFragment f = new SwipeGalleryFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("value", value);
		f.setArguments(bundle);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (getArguments() != null) {
			position = getArguments().getInt("value");
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getActivity().getActionBar().setHomeButtonEnabled(true);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		getActivity().getActionBar().setDisplayShowCustomEnabled(false);

		getActivity().getActionBar().setTitle("Swipe Gallery");
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.swipe_gallery_fragment, container, false);
		File file = new File(Environment.getExternalStorageDirectory(), "KLX");
		if (file.exists()) {
			fs = file.listFiles();
		}
		isFavorite = resource.getSqlite().isFavorite(fs[position].getName());
		getActivity().invalidateOptionsMenu();
		pageTitle.clear();
		for (File dto : fs) {
			pageTitle.add(dto.getName());
		}
		pager = (ViewPager) rootView.findViewById(R.id.pager_gallery);
		adapter = new MainPagerAdapter(getFragmentManager(), getActivity());
		pager.setOnPageChangeListener(this);
		pager.setAdapter(adapter);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		pager.setCurrentItem(position);
	}

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
			return false;
		}

		@Override
		public Fragment getItem(int position) {
			return ImageFullZoomFragment.newInstance(fs[position].getAbsolutePath());
		}

		@Override
		public int getCount() {
			return pageTitle.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {

			return pageTitle.get(position);
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public CharSequence getPageName(int position) {
			return pageTitle.get(position);
		}
	}

	private MenuItem itemFavorite;

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		itemFavorite = menu.findItem(R.id.action_favorite);
		if (itemFavorite != null) {
			return;
		}
		inflater.inflate(R.menu.menu_screen_swipe, menu);
		itemFavorite = menu.findItem(R.id.action_favorite);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		if (itemFavorite == null) {
			return;
		}
		if (isFavorite) {
			itemFavorite.setIcon(R.drawable.ic_action_star_on_light);
		} else {
			itemFavorite.setIcon(R.drawable.ic_action_star_off_light);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_favorite:
			if (TextUtils.isEmpty(fs[position].getName())) {
				return false;
			}
			if (isFavorite) {
				resource.getSqlite().removeFavorite(fs[position].getName());
			} else {
				resource.getSqlite().addFavorite(fs[position].getName());
			}
			isFavorite = !isFavorite;

			getActivity().invalidateOptionsMenu();
			break;
		case R.id.action_delete_on_swipe:
			getActivity().startActionMode(new Callback() {

				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
					return false;
				}

				@Override
				public void onDestroyActionMode(ActionMode mode) {

				}

				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					mode.getMenuInflater().inflate(R.menu.menu_confirm_delete, menu);
					return true;
				}

				@Override
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
					switch (item.getItemId()) {
					case R.id.action_delete_confirm:
						String name = fs[position].getName();
						File f = new File(Environment.getExternalStorageDirectory(), File.separator + "KLX"
								+ File.separator + fs[position].getName());
						if (f.exists()) {
							if (f.delete()) {
								Toast.makeText(getActivity(), "Delete success", Toast.LENGTH_SHORT).show();

								File file = new File(Environment.getExternalStorageDirectory(), "KLX");
								if (file.exists()) {
									fs = file.listFiles();
								}
								pageTitle.remove(name);
								adapter.notifyDataSetChanged();
							}
						}

						break;

					default:
						break;
					}
					mode.finish();
					return true;
				}
			});
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);

		if (itemFavorite != null) {
			itemFavorite.setVisible(false);
			getActivity().invalidateOptionsMenu();
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		position = arg0;
		isFavorite = resource.getSqlite().isFavorite(fs[arg0].getName());
		getActivity().invalidateOptionsMenu();
	}

}
