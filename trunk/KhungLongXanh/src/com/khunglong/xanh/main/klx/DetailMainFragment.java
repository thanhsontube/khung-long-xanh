package com.khunglong.xanh.main.klx;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.example.sonnt_commonandroid.utils.PreferenceUtil;
import com.khunglong.xanh.MsConstant;
import com.khunglong.xanh.MyApplication;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.base.Controller;
import com.khunglong.xanh.json.PageData;
import com.khunglong.xanh.myfacebook.FbLikesLoader;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.khunglong.xanh.myfacebook.object.FbLikes;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class DetailMainFragment extends BaseFragment {
	FilterLog log = new FilterLog("DetailMainFragment");
	ImageLoader imageLoader;

	// layout

	// actionBar
	private TextView txtLike;
	private ImageView imgSaveActionbar;

	private int position;

	private PageData pageData;
	private FbLoaderManager mFbLoaderManager;
	private MyApplication app;
	private Context context;
	// private DragonData mDragonData;

	private ResourceManager resource;
	private IDetailsFragmentListener listener;

	public static interface IDetailsFragmentListener {
		void onDetailsFragmentPicture(String link, String content);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof IDetailsFragmentListener) {
			listener = (IDetailsFragmentListener) activity;
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		listener = null;
	}

	public static DetailMainFragment newInstance() {
		DetailMainFragment f = new DetailMainFragment();
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity().getApplicationContext();
		resource = ResourceManager.getInstance();
		app = (MyApplication) getActivity().getApplication();
		imageLoader = ImageLoader.getInstance();
		mFbLoaderManager = resource.getFbLoaderManager();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		FragmentManager fm = getChildFragmentManager();
		DetailHeaderFragment f = (DetailHeaderFragment) fm.findFragmentById(R.id.detail_top);
		FragmentTransaction ft = fm.beginTransaction();
		if (f == null) {
			ft.add(R.id.detail_top, DetailHeaderFragment.newInstance());
		}

		DetailCommentFragment cmtFragment = (DetailCommentFragment) fm.findFragmentById(R.id.detail_bottom);
		if (cmtFragment == null) {
			ft.add(R.id.detail_bottom, DetailCommentFragment.newInstance());
		}
		ft.commit();
	}

	// TODO onCreateView
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		log.d("log>>>" + "onCreateView");
		View rootView = inflater.inflate(R.layout.klx_detail_fragment, container, false);

		View v = getActivity().getActionBar().getCustomView();
		txtLike = (TextView) v.findViewWithTag("like");

		imgSaveActionbar = (ImageView) v.findViewWithTag("save");
		
		return rootView;
	}
	
	OnClickListener imgSaveOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			log.d("log>>>" + "pageData.getSourceQuality():" + pageData.getSource());
			 saveImage(pageData.getSourceQuality());
		}
	};

	public void setData(PageData data, int pos) {
		txtLike.setText("---");
		pageData = data;
		position = pos;
		log.d("log>>>" + "setData position:" + position + ";" + pageData.getSource());
		updateLayoutPage(pageData);
		imgSaveActionbar.setOnClickListener(imgSaveOnClickListener);

		if (data.getLikes().getSummary() == null) {
			controllerLikes.load();
		} else {
			txtLike.setText("old:" + data.getLikes().getSummary().getTotal_count());
		}
	}

	private void updateLayoutPage(PageData pageData) {
		log.d("log>>>" + "updateLayoutPage title:" + pageData.getName());

		FragmentManager fm = getChildFragmentManager();
		DetailHeaderFragment f = (DetailHeaderFragment) fm.findFragmentById(R.id.detail_top);
		FragmentTransaction ft = fm.beginTransaction();
		if (f == null) {
			f = DetailHeaderFragment.newInstance();
			ft.add(R.id.detail_top, f);
		}

		// comment
		DetailCommentFragment cmtFragment = (DetailCommentFragment) fm.findFragmentById(R.id.detail_bottom);
		if (cmtFragment == null) {
			cmtFragment = DetailCommentFragment.newInstance();
			ft.add(R.id.detail_bottom, cmtFragment);
		}
		ft.commit();

		// update
		f.updateImageAndTitle(pageData);
		cmtFragment.setData(pageData, position);
	}

	private Controller controllerLikes = new Controller() {

		@Override
		public void load() {
			Bundle params = new Bundle();
			params.putString("summary", "1");
			String graphPath = pageData.getId() + "/likes";
			mFbLoaderManager.load(new FbLikesLoader(context, graphPath, params) {

				@Override
				public void onFbLoaderSuccess(FbLikes entry) {
					log.d("log>>>" + "FbLikesLoader onFbLoaderSuccess entry:" + entry.getSummary().getTotal_count());

					// update data like
					resource.getKlxData().getData().get(position).setLikes(entry);

					txtLike.setText("new:" + entry.getSummary().getTotal_count());
				}

				@Override
				public void onFbLoaderStart() {

				}

				@Override
				public void onFbLoaderFail(Throwable e) {
					log.e("log>>>" + "FbLikesLoader onFbLoaderFail:" + e.toString());
				}

			});
		}
	};

	private void saveImage(String link) {
		imageLoader.loadImage(link, new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String imageUri, View view) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

				try {

					ByteArrayOutputStream bytes = new ByteArrayOutputStream();
					loadedImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
					// you can create a new file name "test.jpg" in sdcard folder.
					File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "KLX");
					if (!folder.exists()) {
						folder.mkdirs();
					}

					String title = "KLX_";
					int i = PreferenceUtil.getPreference(getActivity(), MsConstant.KEY_SAVE, 0);
					title += i;
					title += ".jpg";

					File f = new File(Environment.getExternalStorageDirectory() + File.separator + "KLX"
							+ File.separator + title);
					f.createNewFile();
					// write the bytes in file
					FileOutputStream fo = new FileOutputStream(f);
					fo.write(bytes.toByteArray());

					// remember close de FileOutput
					fo.close();
					PreferenceUtil.setPreference(getActivity(), MsConstant.KEY_SAVE, ++i);
					Toast.makeText(getActivity(), "Your image is saved to this folder:" + f.toString(),
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Log.e("", "log>>>" + "error save Image:" + e.toString());
					Toast.makeText(getActivity(), "Error save Imager", Toast.LENGTH_LONG).show();
				}

			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				// TODO Auto-generated method stub

			}
		});
	}

}
