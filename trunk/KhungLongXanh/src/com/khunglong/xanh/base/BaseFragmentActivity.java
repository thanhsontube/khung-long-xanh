package com.khunglong.xanh.base;

import java.util.Stack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;

import com.example.sonnt_commonandroid.utils.FilterLog;

abstract public class BaseFragmentActivity extends FragmentActivity implements
		OnBackStackChangedListener {
	
	public interface OnBackPressListener {

		boolean onBackPress();
	}

	protected static final String FRAGMENT_KEY = "fragment-key";
	
	protected static final String SAVE_KEY_STACK = "tag_stack";
	private static final String TAG = "BaseFragmentActivity";
	FilterLog log = new FilterLog(TAG);

	abstract protected Fragment createFragmentMain(Bundle savedInstanceState);
	abstract protected int getFragmentContentId();
	protected final Stack<String> mFragmentTagStack = new Stack<String>();
	private int currentPosition = 0;

	public int getCurrentPosition() {
		return currentPosition;
	}
	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(getFragmentContentId(),
							createFragmentMain(savedInstanceState),
							FRAGMENT_KEY)
					.setTransition(FragmentTransaction.TRANSIT_NONE).commit();
		} else {
//			mFragmentTagStack.addAll((Collection<String>) savedInstanceState
//					.getSerializable(SAVE_KEY_STACK));
//			restoreFragmentsState();
		}

		getSupportFragmentManager().addOnBackStackChangedListener(this);
	}


	@Override
	public void onBackPressed() {
		log.d("log>>>" + "BASE onBackPressed mFragmentTagStack.size():" + mFragmentTagStack.size());
		final FragmentManager fm = getSupportFragmentManager();
		final Fragment f;
		if (mFragmentTagStack.size() > 0) {
			f = fm.findFragmentByTag(mFragmentTagStack.peek());
		} else {
			f = fm.findFragmentByTag(FRAGMENT_KEY);
		}

		if (f instanceof OnBackPressListener) {
			if (((OnBackPressListener) f).onBackPress()) {
				log.d("log>>>" + "f instanceof OnBackPressListener");
				return;
			}
		}
		super.onBackPressed();
	}

	@Override
	public void onBackStackChanged() {
		log.d("log>>>" + "BASE onBackStackChanged:" + mFragmentTagStack.size());
		FragmentManager fm = getSupportFragmentManager();
		if (fm.getBackStackEntryCount() == mFragmentTagStack.size()) {
			log.d("log>>>" + "======");
			return;
		}

		if (mFragmentTagStack.size() > 0) {
			final FragmentTransaction ft = fm.beginTransaction();
			String tag = mFragmentTagStack.pop();
			if (fm.findFragmentByTag(tag) != null) {
				ft.remove(fm.findFragmentByTag(tag));
			}
			
			
			 Fragment f2 = null, f3 = null;
			Fragment ff = fm.findFragmentByTag(FRAGMENT_KEY);
			if (fm.findFragmentByTag("f2") != null) {
				f2 = fm.findFragmentByTag("f2");
				ft.hide(f2);
				
			}
			if (fm.findFragmentByTag("f3") != null) {
				f3 = fm.findFragmentByTag("f3");
				ft.hide(f3);
				
			}
			switch (currentPosition) {
			case 0:
				if (f2 != null) {
					ft.hide(f2);
				}
				
				if (f3 != null) {
					ft.hide(f3);
				}
				break;
			case 1:
				if (f2 != null) {
					ft.show(f2);
					ft.hide(ff);
				}
				
				if (f3 != null) {
					ft.hide(f3);
				}
				break;
			case 2:
				if (f2 != null) {
					ft.hide(f2);
				}
				
				if (f3 != null) {
					ft.show(f3);
					ft.hide(ff);
				}
				break;

			default:
				break;
			}
			ft.commit();
		}

	}

	public void showFragment(Fragment f, boolean isTransit) {
		String ftag = f.getTag();
		final String tag = String.format("%s:%d", getClass().getName(), mFragmentTagStack.size());
		final FragmentManager fm = getSupportFragmentManager();
		final FragmentTransaction ft = fm.beginTransaction();
		
		if (mFragmentTagStack.size() > 0) {
			final Fragment ff = fm.findFragmentByTag(mFragmentTagStack.peek());
			ft.hide(ff);
		} else {
			final Fragment ff = fm.findFragmentByTag(FRAGMENT_KEY);
			ft.hide(ff);
			if (fm.findFragmentByTag("f2") != null) {
				final Fragment f2 = fm.findFragmentByTag("f2");
				ft.hide(f2);
				
			}
			if (fm.findFragmentByTag("f3") != null) {
				final Fragment f3 = fm.findFragmentByTag("f3");
				ft.hide(f3);
				
			}
			
		}
		if (fm.findFragmentByTag(ftag) == null) {
			ft.add(getFragmentContentId(), f, tag);
			ft.show(f);
		} else {
			ft.replace(getFragmentContentId(), f);
			ft.show(f);
		}
		if(isTransit){
			ft.addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		} else {
			ft.addToBackStack(null);
		}
		ft.commit();
		mFragmentTagStack.add(tag);	
	}
	/**
	 * duplicate
	 * @param f
	 * @param isTransit
	 */

	protected void restoreFragmentsState() {
		final FragmentManager fm = getSupportFragmentManager();
		final FragmentTransaction ft = fm.beginTransaction();
		if (mFragmentTagStack.size() == 0) {
			ft.show(fm.findFragmentByTag(FRAGMENT_KEY));
		} else {
			ft.hide(fm.findFragmentByTag(FRAGMENT_KEY));
			for (int i = 0; i < mFragmentTagStack.size(); i++) {
				String tag = mFragmentTagStack.get(i);
				Fragment f = fm.findFragmentByTag(tag);
				if (i + 1 < mFragmentTagStack.size()) {
					ft.hide(f);
				} else {
					ft.show(f);
				}
			}
		}
		ft.commit();
	}

}
