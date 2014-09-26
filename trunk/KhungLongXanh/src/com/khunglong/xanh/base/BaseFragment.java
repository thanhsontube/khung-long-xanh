package com.khunglong.xanh.base;

import com.androidquery.AQuery;
import com.khunglong.xanh.ResourceManager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
    protected Context context;
    protected ResourceManager resource;
    protected AQuery aQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity().getApplicationContext();
        resource = ResourceManager.getInstance();
        aQuery = new AQuery(getActivity());
    }
}
