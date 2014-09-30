package com.khunglong.xanh.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.facebook.Session;
import com.khunglong.xanh.MainActivity;
import com.khunglong.xanh.R;
import com.khunglong.xanh.base.BaseFragmentActivity;
import com.khunglong.xanh.login.LoginFragment.ILoginListener;

public class MyLoginActivity extends BaseFragmentActivity implements ILoginListener {

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
    public void onLogin(LoginFragment f, String pageName) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("page", pageName);
        startActivity(intent);
    }

    @Override
    public void onLogout(LoginFragment f, Session session) {

    }
}
