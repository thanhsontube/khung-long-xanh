package com.example.sonnt_commonandroid.fragment;

import com.example.sonnt_commonandroid.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentLeft extends Fragment implements OnClickListener{
	
	Button btn1, btn2;

	public static FragmentLeft init(int arg) {
		FragmentLeft f = new FragmentLeft();
		Bundle bundle = new Bundle();
		bundle.putInt("key", arg);
		f.setArguments(bundle);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_left, container, false);
//		btn1 = (Button) v.findViewById(R.id.button1);
//		btn2 = (Button) v.findViewById(R.id.button2);
//		btn1.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		btn2.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		return v;
	}

	@Override
	public void onClick(View v) {}

}
