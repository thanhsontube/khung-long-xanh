package com.khunglong.xanh.save;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.R;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.save.adapter.GridPictureAdapter;

public class SaveImageFragment extends BaseFragment {

    private static final String TAG = "SaveTextFragment";

    FilterLog log = new FilterLog(TAG);

    private GridView gridView;
    private List<File> list = new ArrayList<File>();
    private GridPictureAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getActionBar().setHomeButtonEnabled(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().getActionBar().setDisplayShowCustomEnabled(false);

        getActivity().getActionBar().setTitle("Save Pictures");
        View rootView = inflater.inflate(R.layout.save_picture_fragment, container, false);
        gridView = (GridView) rootView.findViewWithTag("gridview");
        list.clear();
        File file = new File(Environment.getExternalStorageDirectory(), "KLX");
        if (file.exists()) {
            File[] fs = file.listFiles();
            for (File dto : fs) {
                list.add(dto);
            }
        }
        adapter = new GridPictureAdapter(getActivity(), list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener == null) {
                    return;
                }
                listener.onGridClick(list.get(position), position);
            }
        });

        return rootView;
    }

    private ISaveImageListener listener;

    public static interface ISaveImageListener {
        void onGridClick(File dto, int value);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISaveImageListener) {
            listener = (ISaveImageListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().invalidateOptionsMenu();
        listener = null;
    }

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(!isHidden()) {
			list.clear();
	        File file = new File(Environment.getExternalStorageDirectory(), "KLX");
	        if (file.exists()) {
	            File[] fs = file.listFiles();
	            for (File dto : fs) {
	                list.add(dto);
	            }
	        }
	        adapter.notifyDataSetChanged();
		}
	}
    
    


}
