package com.khunglong.xanh.save;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;

public class SaveFragment extends BaseFragment {

    private ListView listview;
    SimpleCursorAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.save_fragment, container, false);
        listview = (ListView) rootView.findViewWithTag("listview");

        mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, ResourceManager
                .getInstance().getSqlite().getData(), new String[] { "value" }, new int[] { android.R.id.text1 }, 0);
        listview.setAdapter(mAdapter);
        return rootView;
    }

}
