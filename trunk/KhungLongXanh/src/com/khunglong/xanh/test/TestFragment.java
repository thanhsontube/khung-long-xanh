package com.khunglong.xanh.test;

import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.R;
import com.khunglong.xanh.base.BaseFragment;

public class TestFragment extends BaseFragment {
	private static final String TAG = "TestFragment";
	private TextView txt;
	private String page;
	private int pos;
	FilterLog log = new FilterLog(TAG);

	public static TestFragment newInstance(String page, int pos) {
		TestFragment f = new TestFragment();
		Bundle bundle = new Bundle();
		bundle.putString("page", page);
		bundle.putInt("pos", pos);
		f.setArguments(bundle);
		return f;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		if (getArguments() != null) {
			Bundle bundle = getArguments();
			page = bundle.getString("page");
			pos = bundle.getInt("pos");
		}
		log.d("log>>>" + "onCreate page:" + page + "pos:" + pos);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		log.d("log>>>" + "onCreateView page:" + page + "pos:" + pos);
		View viewRoot = inflater.inflate(R.layout.test_fragment, container, false);
		txt = (TextView) viewRoot.findViewWithTag("text");
		txt.setText(getString());
		
		return viewRoot;
	}
	
	public String getString(){
		StringBuilder builder = new StringBuilder();
		builder.append(page);
		builder.append(":");
		builder.append(pos);
		return builder.toString();
	}
	
	public void load(String page){
		this.page = page;
		txt.setText(getString());
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
	    log.d("log>>>" + "onHiddenChanged page:" + page + ";hidden:" + hidden);
	    super.onHiddenChanged(hidden);
	}
	

}
