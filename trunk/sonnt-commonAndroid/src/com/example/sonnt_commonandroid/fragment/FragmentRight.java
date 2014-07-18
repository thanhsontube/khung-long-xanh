package com.example.sonnt_commonandroid.fragment;

import com.example.sonnt_commonandroid.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentRight extends Fragment {

	public static FragmentRight init(int arg) {
		FragmentRight f = new FragmentRight();
		Bundle bundle = new Bundle();
		bundle.putInt("key", arg);
		f.setArguments(bundle);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_right, container, false);
		return v;
	}

}
