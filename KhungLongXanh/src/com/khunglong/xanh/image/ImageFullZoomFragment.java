package com.khunglong.xanh.image;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.khunglong.xanh.MsConstant;
import com.khunglong.xanh.R;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.libs.TouchImageView;

public class ImageFullZoomFragment extends BaseFragment {

    private int position;
    private TouchImageView imageView;
    boolean isFavorite;
    private String value = null;

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
        setHasOptionsMenu(true);
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
        imageView = (TouchImageView) rootView.findViewWithTag("image");

        File file = new File(Environment.getExternalStorageDirectory(), "KLX");
        File dto = null;
        if (file.exists()) {
            File[] fs = file.listFiles();
            dto = fs[position];
            value = dto.getName();
        }
        aQuery.id(imageView).image(dto, 0);
        // check favorite
        isFavorite = resource.getSqlite().isFavorite(dto.getName());

        return rootView;
    }

    MenuItem itemFavorite;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        itemFavorite = menu.findItem(MsConstant.MENU_FAVORITE_ID);
        if (itemFavorite != null) {
            itemFavorite.setVisible(true);
            return;
        }
        itemFavorite = menu.add(0, MsConstant.MENU_FAVORITE_ID, 0, "Favorite");
        itemFavorite.setIcon(R.drawable.star_off);
        itemFavorite.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (isFavorite) {
            itemFavorite.setIcon(R.drawable.star_on);
        } else {
            itemFavorite.setIcon(R.drawable.star_off);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case MsConstant.MENU_FAVORITE_ID:
            Toast.makeText(getActivity(), "favorite", Toast.LENGTH_SHORT).show();
            if (TextUtils.isEmpty(value)) {
                return true;
            }
            if (isFavorite) {
                resource.getSqlite().removeFavorite(value);
            } else {
                resource.getSqlite().addFavorite(value);
            }
            isFavorite = !isFavorite;

            getActivity().invalidateOptionsMenu();
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

}
