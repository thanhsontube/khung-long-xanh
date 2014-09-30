package com.khunglong.xanh.main.klx;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.json.PageData;
import com.khunglong.xanh.utils.BitmapUtils;

public class DetailHeaderFragment extends BaseFragment {

    private static final String TAG = "DetailHeaderFragment";
    private TextView txtTitle;
    private ResourceManager resource;
    private FilterLog log = new FilterLog(TAG);

    private ImageView image;
    private ImageButton btnPopup;
    private PageData mPageData;

    private String page;

    public static DetailHeaderFragment newInstance() {
        DetailHeaderFragment f = new DetailHeaderFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resource = ResourceManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_image_fragment, container, false);
        image = (ImageView) rootView.findViewById(R.id.detail_image);
        txtTitle = (TextView) rootView.findViewWithTag("title");
        btnPopup = (ImageButton) rootView.findViewWithTag("popup");
        btnPopup.setOnClickListener(new MyClickListener(0));
        btnPopup.setEnabled(false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setData(String page, PageData pageData) {
        if (page.equalsIgnoreCase(resource.getListPageResource().get(0).getFbName())
                || page.equalsIgnoreCase(resource.getListPageResource().get(3).getFbName())
                || page.equalsIgnoreCase(resource.getListPageResource().get(4).getFbName())) {
            image.setScaleType(ScaleType.FIT_XY);
        }
        btnPopup.setEnabled(true);
        this.mPageData = pageData;
        try {
            log.d("log>>>" + "DetailHeaderFragment setData:" + pageData.getName());
            // title
            txtTitle.setText(pageData.getName());
            // image
            String link = pageData.getSource();
            log.d("log>>> " + "link:" + link);
            AQuery aQuery = new AQuery(getActivity());
            aQuery.id(image).image(link);
        } catch (Exception e) {
            log.e("log>>>" + "error updateImageAndTitle:" + e.toString());
        }
    }

    class MyClickListener implements OnClickListener, OnMenuItemClickListener {

        int id;

        public MyClickListener(int id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            PopupMenu popup = new PopupMenu(getActivity(), v);
            popup.setOnMenuItemClickListener(this);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_header_image, popup.getMenu());
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
            case R.id.action_save_text:
                if (resource.getSqlite().insertData(mPageData.getName())) {
                    Toast.makeText(getActivity(), R.string.save_success + mPageData.getName(), Toast.LENGTH_SHORT)
                            .show();
                }

                break;
            case R.id.action_save_image:
                AQuery aQuery = new AQuery(getActivity());
                try {

                    Bitmap bitmap = aQuery.getCachedImage(mPageData.getSourceQuality());
                    BitmapUtils.saveImage(getActivity(), bitmap);
                } catch (Exception e) {

                    try {
                        Bitmap bitmap = aQuery.getCachedImage(mPageData.getSource());
                        BitmapUtils.saveImage(getActivity(), bitmap);

                    } catch (Exception e2) {
                        Toast.makeText(getActivity(), "error 2 time:" + e.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
                break;

            default:
                break;
            }
            return false;
        }

    }
}
