package com.khunglong.xanh.mycomments;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.khunglong.xanh.MyApplication;
import com.khunglong.xanh.R;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.comments.CmtAdapter;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.khunglong.xanh.myfacebook.object.FbCmtData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * answer of cmt
 * 
 * @author sonnt
 * 
 */
public class MyCommentsFragment extends BaseFragment {
	private static final String TAG = "AnswerFragment";
	FilterLog log = new FilterLog(TAG);
	private TextView txtTitle;
	private TextView txtName;
	private ImageView img;
	
	private ListView cmtListview;
	private CmtAdapter cmtAdapter;
	private List<FbCmtData> cmtListData;
	private Context context;
	private FbLoaderManager mFbLoaderManager;
	private MyApplication app;
	private String id;
	private int position;
	private int cmtPos;
	
	private String title;
	private String source;
	private ViewGroup mEmpty;
	
	private ImageLoader imageLoader;
	
	public static MyCommentsFragment newInstance(String title, String source, String id) {
		MyCommentsFragment f = new MyCommentsFragment();
		Bundle bundle = new Bundle();
		bundle.putString("title", title);
		bundle.putString("source", source);
		bundle.putString("id", id);
		f.setArguments(bundle);
		return f;
	}
	
	public MyCommentsFragment() {
	    super();
	    // TODO Auto-generated constructor stub
    }



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imageLoader = ImageLoader.getInstance();
		context = getActivity().getApplicationContext();
		app = (MyApplication) getActivity().getApplication();
		mFbLoaderManager = app.getmFbLoaderManager();
		if (getArguments() != null) {
			Bundle bundle = getArguments();
			title = bundle.getString("title");
			source = bundle.getString("source");
			id = bundle.getString("id");
		}
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.mycomments_fragment, container, false);
		txtTitle = (TextView) rootView.findViewById(R.id.mycmt_title);
		img = (ImageView) rootView.findViewById(R.id.mycmt_img);
		
		txtTitle.setText(title);
		
		MyApplication app = (MyApplication) getActivity().getApplication();
		imageLoader.displayImage(source, img, app.getOptionsContent(),null);
		return rootView;
	}

	
}
