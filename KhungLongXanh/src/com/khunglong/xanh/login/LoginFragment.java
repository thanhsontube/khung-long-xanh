package com.khunglong.xanh.login;

import java.util.Arrays;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.example.sonnt_commonandroid.utils.PreferenceUtil;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.khunglong.xanh.MsConstant;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.utils.GoogleAnaToolKLX;

public class LoginFragment extends BaseFragment implements OnClickListener {
    private static final String TAG = "LoginFragment";
    private UiLifecycleHelper uiHelper;
    FilterLog log = new FilterLog(TAG);

    private TextView n1, n2, n3, n4, n5;

    private ResourceManager resource;
    BroadCastUpdateNews receiver;

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

        receiver = new BroadCastUpdateNews();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MsConstant.ACTION_UPDATE_NEWS);
        getActivity().registerReceiver(receiver, filter);
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

        // setup new
        n1 = (TextView) rootView.findViewWithTag("new_page1");
        n2 = (TextView) rootView.findViewWithTag("new_page2");
        n3 = (TextView) rootView.findViewWithTag("new_page3");
        n4 = (TextView) rootView.findViewWithTag("new_page4");
        n5 = (TextView) rootView.findViewWithTag("new_page5");

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

        updateNews();
    }

    public void updateNews() {
        log.d("log>>> " + "updateNews");
        int iNew;
        if ((iNew = PreferenceUtil.getPreference(getActivity(), MsConstant.KEY_NEW_1, MsConstant.DEFAULT)) > 0) {
            n1.setText(String.valueOf(iNew));
            n1.setVisibility(View.VISIBLE);
        } else {
            n1.setVisibility(View.GONE);
        }

        if ((iNew = PreferenceUtil.getPreference(getActivity(), MsConstant.KEY_NEW_2, MsConstant.DEFAULT)) > 0) {
            n2.setText(String.valueOf(iNew));
            n2.setVisibility(View.VISIBLE);
        } else {
            n2.setVisibility(View.GONE);
        }

        if ((iNew = PreferenceUtil.getPreference(getActivity(), MsConstant.KEY_NEW_3, MsConstant.DEFAULT)) > 0) {
            n3.setText(String.valueOf(iNew));
            n3.setVisibility(View.VISIBLE);
        } else {
            n3.setVisibility(View.GONE);
        }

        if ((iNew = PreferenceUtil.getPreference(getActivity(), MsConstant.KEY_NEW_4, MsConstant.DEFAULT)) > 0) {
            n4.setText(String.valueOf(iNew));
            n4.setVisibility(View.VISIBLE);
        } else {
            n4.setVisibility(View.GONE);
        }

        if ((iNew = PreferenceUtil.getPreference(getActivity(), MsConstant.KEY_NEW_5, MsConstant.DEFAULT)) > 0) {
            n5.setText(String.valueOf(iNew));
            n5.setVisibility(View.VISIBLE);
        } else {
            n5.setVisibility(View.GONE);
        }
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
        getActivity().unregisterReceiver(receiver);
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

    private class BroadCastUpdateNews extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MsConstant.ACTION_UPDATE_NEWS)) {
                updateNews();
            }

        }

    }

}
