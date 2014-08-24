package com.khunglong.xanh.main.klx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.MyApplication;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.json.PageData;

public class ImageTitleKlxFragment extends BaseFragment {

	private static final String TAG = "ImageTitleKlxFragment";
	private ImageView img;
	private TextView txtTitle;
	private ResourceManager resource;
	private FilterLog log = new FilterLog(TAG);

	public static ImageTitleKlxFragment newInstance() {
		ImageTitleKlxFragment f = new ImageTitleKlxFragment();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.detail_image_fragment, container, false);
		img = (ImageView) rootView.findViewWithTag("image");
		txtTitle = (TextView) rootView.findViewWithTag("title");
		resource = ResourceManager.getInstance();
		return rootView;
	}

	public void updateImageAndTitle(PageData pageData) {
		try {
			log.d("log>>>" + "updateImageAndTitle:" + pageData.getName());
			// image
			String link = pageData.getSource();
			// String linkHigh = pageData.getSourceQuality();
			MyApplication app = (MyApplication) getActivity().getApplication();
			resource.getImageLoader().displayImage(link, img, app.getOptionsContent(), null);

			// title
			txtTitle.setText(pageData.getName());
		} catch (Exception e) {
			log.e("log>>>" + "error updateImageAndTitle:" + e.toString());
		}
	}
}
