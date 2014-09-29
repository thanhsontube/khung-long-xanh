package com.khunglong.xanh.mycomments;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonnt_commonandroid.utils.FilterLog;
import com.facebook.Session;
import com.khunglong.xanh.MainActivity;
import com.khunglong.xanh.MyApplication;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.base.BaseFragment;
import com.khunglong.xanh.base.Controller;
import com.khunglong.xanh.comments.CmtAdapter;
import com.khunglong.xanh.myfacebook.FbLoaderManager;
import com.khunglong.xanh.myfacebook.FbPostCommentsLoader;
import com.khunglong.xanh.myfacebook.object.FbCmtData;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * answer of cmt
 * 
 * @author sonnt
 * 
 */
public class MyCommentsFragment extends BaseFragment {
	private static final String TAG = "AnswerFragment";
	FilterLog log = new FilterLog(TAG);

	// layout
	private TextView txtTitle;
	private TextView txtName;
	private ImageView img;
	private Button btnCmt;
	private EditText txtContent;

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
		context = getActivity().getApplicationContext();

		imageLoader = ImageLoader.getInstance();
		app = (MyApplication) getActivity().getApplication();
		mFbLoaderManager = ResourceManager.getInstance().getFbLoaderManager();
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
		txtContent = (EditText) rootView.findViewById(R.id.txt_content);
		btnCmt = (Button) rootView.findViewById(R.id.btn_comment);
		btnCmt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				controllerPost.load();
			}
		});
		txtTitle.setText(title);

		MyApplication app = (MyApplication) getActivity().getApplication();
		imageLoader.displayImage(source, img, ResourceManager.getInstance().getOptionsContent(), null);
		return rootView;
	}

	Controller controllerPost = new Controller() {

		@Override
		public void load() {
			Bundle params = new Bundle();
			String message = txtContent.getText().toString();
			if (TextUtils.isEmpty(message)) {
				return;
			}
			Session session = Session.getActiveSession();
			List<String> per = session.getPermissions();
			boolean isAllowPost = false;
			for (String string : per) {
				if (string.equals("publish_actions")) {
					isAllowPost = true;
				}
			}
			if (isAllowPost) {
				Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(getActivity(),
				        Arrays.asList("publish_actions"));
				session.requestNewPublishPermissions(newPermissionsRequest);
			}

			params.putString("message", message);
			final String graphPath = id + "/comments";
			mFbLoaderManager.load(new FbPostCommentsLoader(context, graphPath, params) {

				@Override
				public void onFbLoaderSuccess(String entry) {
					log.d("log>>>" + "FbPostCommentsLoader cmt success:" + entry);
					Toast.makeText(getActivity(), "entry", Toast.LENGTH_SHORT).show();
					MainActivity loginActivity = (MainActivity) getActivity();
					loginActivity.onBackPressed();
				}

				@Override
				public void onFbLoaderStart() {

				}

				@Override
				public void onFbLoaderFail(Throwable e) {
					log.e("log>>>" + "FbPostCommentsLoader onFbLoaderFail:" + e.toString());

				}
			});
		}
	};

}
