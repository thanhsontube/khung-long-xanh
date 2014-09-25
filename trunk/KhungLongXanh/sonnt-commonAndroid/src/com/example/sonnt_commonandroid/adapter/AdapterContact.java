package com.example.sonnt_commonandroid.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class AdapterContact extends ArrayAdapter<String>{
	List<String> list;
	Context context;
	public AdapterContact(Context context, List<String> list) {
		super(context, 0, list);
		this.list = list;
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			view = inflater.inflate(resource, null);
			Holder holder = new Holder();
			/*
			 * config holder
			 */
			view.setTag(holder);
		}
		
		Holder holder = (Holder) view.getTag();
		return view;
	}
	
	static class Holder {
		
	}
}
