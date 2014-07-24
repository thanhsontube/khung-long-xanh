package com.khunglong.xanh.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.facebook.Session;
import com.khunglong.xanh.R;
import com.khunglong.xanh.base.BaseFragmentActivity;
import com.khunglong.xanh.login.LoginFragment.ILoginFragmentListener;

public class MyLoginActivity extends BaseFragmentActivity implements ILoginFragmentListener{

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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
	}

	@Override
    public void onLogin(LoginFragment f, Session session) {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void onLogout(LoginFragment f, Session session) {
	    // TODO Auto-generated method stub
	    
    }
}
