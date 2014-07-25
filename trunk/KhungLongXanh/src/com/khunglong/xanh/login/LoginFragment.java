package com.khunglong.xanh.login;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.khunglong.xanh.MainActivity;
import com.khunglong.xanh.R;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.utils.GoogleAnaToolKLX;

public class LoginFragment extends BaseFragment implements OnClickListener{
	private static final String TAG = "LoginFragment";
	private UiLifecycleHelper uiHelper;
	FilterLog log = new FilterLog(TAG);
	
	private ILoginFragmentListener listener;

	public static interface ILoginFragmentListener {
		void onLogin(LoginFragment f, Session session);
		void onLogout(LoginFragment f, Session session);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof ILoginFragmentListener) {
			listener = (ILoginFragmentListener) activity;
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
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		log.d("log>>>" + "onCreateView");
	    View rootView = inflater.inflate(R.layout.login_fragment,container,false);
	    LoginButton authButton = (LoginButton) rootView.findViewById(R.id.authButton);
	    authButton.setReadPermissions(Arrays.asList("email", "user_likes", "user_status"));
	    authButton.setFragment(this);
	    
	    Button btnLogin = (Button) rootView.findViewById(R.id.login_btn_login);
	    btnLogin.setOnClickListener(this);
	    GoogleAnaToolKLX.trackerView(getActivity(), "LOGIN FRAGMENT");
		return rootView;
	    
	}
	
	private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        log.d(">>>Logged in...:" +session.getAccessToken());
	        listener.onLogin(LoginFragment.this, session);
	        startActivity(new Intent(getActivity(), MainActivity.class));
	        getActivity().finish();
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
	
	//TODO onclick
	@Override
    public void onClick(View v) {
	    switch (v.getId()) {
		case R.id.login_btn_login:
			if (Session.getActiveSession().isOpened()) {
				
				listener.onLogin(this, null);
				startActivity(new Intent(getActivity(), MainActivity.class));
				getActivity().finish();
			} else {
				Toast.makeText(getActivity(), "Login first", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	    
    }
}
