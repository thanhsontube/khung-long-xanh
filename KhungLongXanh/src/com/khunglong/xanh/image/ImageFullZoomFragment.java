package com.khunglong.xanh.image;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.khunglong.xanh.R;
import com.khunglong.xanh.base.BaseFragment;

public class ImageFullZoomFragment extends BaseFragment {

    private int position;
    private ImageView imageView;

    public static ImageFullZoomFragment newInstance(int value) {
        ImageFullZoomFragment f = new ImageFullZoomFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("value", value);
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("value");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getActionBar().setHomeButtonEnabled(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().getActionBar().setDisplayShowCustomEnabled(false);

        getActivity().getActionBar().setTitle("Details");
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.image_full_zoom_fragment, container, false);
        imageView = (ImageView) rootView.findViewWithTag("image");

        File file = new File(Environment.getExternalStorageDirectory(), "KLX");
        File dto = null;
        if (file.exists()) {
            File[] fs = file.listFiles();
            dto = fs[position];
        }
        aQuery.id(imageView).image(dto, 0);

        return rootView;
    }
}
