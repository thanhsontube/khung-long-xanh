package com.khunglong.xanh.save;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
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
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.data.MyDataHelper;
import com.khunglong.xanh.save.adapter.GridPictureAdapter;

public class FavoriteImageFragment extends BaseFragment {

    private static final String TAG = "SaveTextFragment";

    FilterLog log = new FilterLog(TAG);

    private GridView gridView;
    private List<File> list = new ArrayList<File>();
    private GridPictureAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getActionBar().setHomeButtonEnabled(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().getActionBar().setDisplayShowCustomEnabled(false);

        getActivity().getActionBar().setTitle("Favorite Pictures");
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.save_picture_fragment, container, false);
        gridView = (GridView) rootView.findViewWithTag("gridview");
        list.clear();
        File file = new File(Environment.getExternalStorageDirectory(), "KLX");

        Cursor cursor = ResourceManager.getInstance().getSqlite().getData(MyDataHelper.DATABASE_TABLE_FAVORITE);
        if (cursor.moveToFirst()) {
            do {
                String value = cursor.getString(1);
                if (file.exists()) {
                    File fPic = new File(file, value);
                    if (fPic.exists()) {
                        list.add(fPic);
                    }
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter = new GridPictureAdapter(getActivity(), list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener == null) {
                    return;
                }
                log.d("log>>> " + "FAVORITE Click:" + list.get(position).getName() + ";pos:" + position);
                listener.onFavoriteImageGridClick(list.get(position), position);
            }
        });

        return rootView;
    }

    private IFavoriteImageListener listener;

    public static interface IFavoriteImageListener {
        void onFavoriteImageGridClick(File dto, int value);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof IFavoriteImageListener) {
            listener = (IFavoriteImageListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
