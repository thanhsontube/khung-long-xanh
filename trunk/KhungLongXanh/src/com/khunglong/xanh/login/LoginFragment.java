package com.khunglong.xanh.login;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.utils.GoogleAnaToolKLX;

public class LoginFragment extends BaseFragment implements OnClickListener {
    private static final String TAG = "LoginFragment";
    private UiLifecycleHelper uiHelper;
    FilterLog log = new FilterLog(TAG);

    private ResourceManager resource;

    public static interface ILoginListener {
        void onLogin(LoginFragment f, String pageName);

        void onLogout(LoginFragment f, Session session);
    }

    private ILoginListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILoginListener) {
            listener = (ILoginListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resource = ResourceManager.getInstance();
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        log.d("log>>>" + "onCreateView");
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        LoginButton authButton = (LoginButton) rootView.findViewById(R.id.authButton);
        authButton.setReadPermissions(Arrays.asList("email", "user_likes", "user_status"));
        authButton.setFragment(this);

        // setup page

        View page1 = rootView.findViewWithTag("page1");
        page1.setTag(resource.getListPageResource().get(0).getFbName());

        View page2 = rootView.findViewWithTag("page2");
        page2.setTag(resource.getListPageResource().get(1).getFbName());

        View page3 = rootView.findViewWithTag("page3");
        page3.setTag(resource.getListPageResource().get(2).getFbName());

        View page4 = rootView.findViewWithTag("page4");
        page4.setTag(resource.getListPageResource().get(3).getFbName());

        View page5 = rootView.findViewWithTag("page5");
        page5.setTag(resource.getListPageResource().get(4).getFbName());

        page1.setOnClickListener(this);
        page2.setOnClickListener(this);
        page3.setOnClickListener(this);
        page4.setOnClickListener(this);
        page5.setOnClickListener(this);

        GoogleAnaToolKLX.trackerView(getActivity(), "LOGIN FRAGMENT");
        return rootView;

    }

    private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            log.d(">>>Logged in...:" + session.getAccessToken());
        } else if (state.isClosed()) {
            log.d(">>>Logged out...");
            listener.onLogout(this, session);
        }
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

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

    @Override
    public void onClick(View v) {
        if (Session.getActiveSession().isOpened()) {
            listener.onLogin(LoginFragment.this, (String) v.getTag());
        } else {
            Toast.makeText(getActivity(), "Bạn phải login bằng Facebook trước !", Toast.LENGTH_SHORT).show();
        }

    }

}
