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
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.json.PageData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class DetailHeaderFragment extends BaseFragment {

    private static final String TAG = "DetailHeaderFragment";
    private TextView txtTitle;
    private ResourceManager resource;
    private FilterLog log = new FilterLog(TAG);

    private ImageView image;
    private ImageButton btnPopup;
    private ImageLoader imageLoader;
    private PageData mPageData;

    public static DetailHeaderFragment newInstance() {
        DetailHeaderFragment f = new DetailHeaderFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resource = ResourceManager.getInstance();
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_image_fragment, container, false);
        image = (ImageView) rootView.findViewById(R.id.detail_image);
        txtTitle = (TextView) rootView.findViewWithTag("title");
        btnPopup = (ImageButton) rootView.findViewWithTag("popup");
        btnPopup.setOnClickListener(new MyClickListener(0));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void updateImageAndTitle(PageData pageData) {
        this.mPageData = pageData;
        try {
            log.d("log>>>" + "updateImageAndTitle:" + pageData.getName());
            // log.d("log>>> " + "link:" + pageData.getSource());
            // image
            String link = pageData.getSource();
            // String linkHigh = pageData.getSourceQuality();
            imageLoader.displayImage(link, image, resource.getOptionsContent(), new ImageLoadingListener() {

                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    log.d("log>>> " + "onLoadingStarted:" + imageUri);

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    log.d("log>>> " + "onLoadingFailed:" + failReason.getCause().getMessage());

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    log.d("log>>> " + "onLoadingComplete");

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    log.d("log>>> " + "onLoadingCancelled");

                }
            });

            // title
            txtTitle.setText(pageData.getName());

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
                    Toast.makeText(getActivity(), "Save success:" + mPageData.getName(), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.action_save_image:
                Toast.makeText(getActivity(), "coming soon...", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
            }
            return false;
        }

    }
}