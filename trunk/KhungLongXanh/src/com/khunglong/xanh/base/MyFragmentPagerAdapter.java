/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.khunglong.xanh.base;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.example.sonnt_commonandroid.utils.FilterLog;

/**
 * Implementation of {@link android.support.v4.view.PagerAdapter} that
 * represents each page as a {@link Fragment} that is persistently
 * kept in the fragment manager as long as the user can return to the page.
 */
public abstract class MyFragmentPagerAdapter extends PagerAdapter {
	private static final FilterLog log = new FilterLog(MyFragmentPagerAdapter.class.getSimpleName());

	private final FragmentManager mFragmentManager;
	private FragmentTransaction mCurTransaction = null;
	private Fragment mCurrentPrimaryItem = null;

	public MyFragmentPagerAdapter(FragmentManager fm) {
		mFragmentManager = fm;
	}

	/**
	 * Return the Fragment associated with a specified position.
	 */
	public abstract Fragment getItem(int position);
	
	public abstract boolean isFragmentReusable(Fragment f, int position);

	@Override
	public void startUpdate(View container) {
	}

	@Override
	public Object instantiateItem(View container, int position) {
		if (mCurTransaction == null) {
			mCurTransaction = mFragmentManager.beginTransaction();
		}

		// Do we already have this fragment?
		String name = makeFragmentName(container.getId(), position);
		Fragment fragment = mFragmentManager.findFragmentByTag(name);
		boolean reusable = (fragment != null && isFragmentReusable(fragment, position));
		
		if (fragment != null && reusable) {
			log.v("Reuse item show #" + position + ": f=" + fragment);
			mCurTransaction.show(fragment);
		} else {
			fragment = getItem(position);
			if (reusable) {
				log.v("Replacing item #" + position + ": f=" + fragment);
				mCurTransaction.replace(container.getId(), fragment, makeFragmentName(container.getId(), position));
			} else {
				log.v("Adding item #" + position + ": f=" + fragment);
				mCurTransaction.add(container.getId(), fragment, makeFragmentName(container.getId(), position));
			}
		}
		if (fragment != mCurrentPrimaryItem) {
			fragment.setMenuVisibility(false);
		}

		return fragment;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		if (mCurTransaction == null) {
			mCurTransaction = mFragmentManager.beginTransaction();
		}
		final Fragment f = (Fragment) object;
		if (isFragmentReusable(f, position)) {
			log.v("Hide item #" + position + ": f=" + object + " v=" + ((Fragment) object).getView());
			mCurTransaction.hide(f);
		} else {
			log.v("Detaching item #" + position + ": f=" + object + " v=" + ((Fragment) object).getView());
			mCurTransaction.detach((Fragment) object);
		}
	}

	@Override
	public void setPrimaryItem(View container, int position, Object object) {
		Fragment fragment = (Fragment) object;
		if (fragment != mCurrentPrimaryItem) {
			if (mCurrentPrimaryItem != null) {
				mCurrentPrimaryItem.setMenuVisibility(false);
			}
			if (fragment != null) {
				fragment.setMenuVisibility(true);
			}
			mCurrentPrimaryItem = fragment;
		}
	}

	@Override
	public void finishUpdate(View container) {
		if (mCurTransaction != null) {
			mCurTransaction.commitAllowingStateLoss();
			mCurTransaction = null;
			mFragmentManager.executePendingTransactions();
		}
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return ((Fragment) object).getView() == view;
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}
	
	public Fragment getFragment(View container, int position) {
		return mFragmentManager.findFragmentByTag(makeFragmentName(container.getId(), position));
	}

	private static String makeFragmentName(int viewId, int index) {
		return "makeFragmentName:switcher:" + viewId + ":" + index;
	}
}