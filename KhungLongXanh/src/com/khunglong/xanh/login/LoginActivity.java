package com.khunglong.xanh.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.khunglong.xanh.MyApplication;
import com.khunglong.xanh.R;
import com.khunglong.xanh.base.BaseFragmentActivity;
import com.khunglong.xanh.json.DragonData;
import com.khunglong.xanh.login.LoginFragment.ILoginFragmentListener;
import com.khunglong.xanh.main.MainFragment;

public class LoginActivity extends BaseFragmentActivity implements ILoginFragmentListener {
	private static final String TAG = "LoginActivity";
	FilterLog log = new FilterLog(TAG);
	public DragonData mDragonData;
	public MyApplication app;
	public UiLifecycleHelper uiHelper;

	@Override
	protected Fragment createFragmentMain(Bundle savedInstanceState) {
		return new LoginFragment();
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
		app = (MyApplication) getApplication();
		mDragonData = app.getDragonData();
	}

	// TODO login listener
	@Override
	public void onLogin(LoginFragment f, Session session) {
		MainFragment f1 = new MainFragment();
		showFragment(f1, true);
		
		
//		getSupportFragmentManager().beginTransaction().replace(getFragmentContentId(), f1).commit();

	}

	@Override
	public void onLogout(LoginFragment f, Session session) {
		// TODO Auto-generated method stub

	}

}
