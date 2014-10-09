package com.khunglong.xanh.comments;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.khunglong.xanh.R;
import com.khunglong.xanh.ResourceManager;
import com.khunglong.xanh.myfacebook.object.FbCmtData;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * adapter of comments and cmt of cmt
 * 
 * @author unknown
 * 
 */
public class CmtAdapter extends ArrayAdapter<FbCmtData> {
	private List<FbCmtData> mList;
	private Context mContext;
	ImageLoader imageLoader;
	private ResourceManager resource;
	AQuery aQuery;

	public CmtAdapter(Context context, List<FbCmtData> list, boolean isAnswer) {
		super(context, 0, list);
		aQuery = new AQuery(context);
		mContext = context;
		mList = list;
		resource = ResourceManager.getInstance();

		imageLoader = ImageLoader.getInstance();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		final Holder holder;
		final FbCmtData dto = mList.get(position);
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.row_comments, parent, false);
			holder = new Holder();
			holder.img = (ImageView) v.findViewWithTag("icon");
			holder.txtTitle = (TextView) v.findViewWithTag("title");
			holder.txtUser = (TextView) v.findViewWithTag("user");
			holder.txtLikes = (TextView) v.findViewWithTag("likes");
			holder.txtCmts = (TextView) v.findViewWithTag("cmt");
			holder.viewCmt = v.findViewWithTag("ll_cmt");
			holder.btnPopup = (ImageButton) v.findViewWithTag("popup");
			v.setTag(holder);

		} else {
			holder = (Holder) v.getTag();
		}
		holder.img.setTag(position);

		holder.txtTitle.setText(dto.getMessage());
		holder.txtUser.setText(dto.getFrom().getName());
		holder.txtLikes.setText("" + dto.getLike_count());
		imageLoader.displayImage(dto.getFrom().getSource(), holder.img, resource.getOptionsCircle(), null);

		// if ((position & 1) == 0) {
		// v.setBackgroundResource(R.drawable.btn_common_selector1);
		// } else {
		// v.setBackgroundResource(R.drawable.btn_common_selector2);
		// }

		// if (isAnswer) {
		// holder.viewCmt.setVisibility(View.GONE);
		// } else {
		// holder.viewCmt.setVisibility(View.VISIBLE);
		// }

		holder.btnPopup.setOnClickListener(new MyClickListener(position));

		// update avatar
		// String idFrom = dto.getFrom().getId();
		// int pos = (Integer) holder.img.getTag();
		// Log.v("LOG", "log>>>" + "AVATAR tag:" + pos);
		// loadDataAvatar(position, idFrom, holder.img);
		return v;
	}

	public void setData(List<FbCmtData> objects, boolean isReset) {
		if (isReset) {
			mList.clear();
		}
		mList.addAll(objects);
		notifyDataSetChanged();
	}

	static class Holder {
		ImageView img;
		TextView txtTitle;
		TextView txtUser;
		TextView txtLikes;
		TextView txtCmts;
		ImageButton btnPopup;
		View viewCmt;
	}

	// public void loadDataAvatar(int pos, String idFrom, ImageView imageView) {
	// String source = mList.get(pos).getFrom().getSource();
	// if (!TextUtils.isEmpty(source)) {
	// imageLoader.displayImage(source, imageView, options, null);
	// return;
	// }
	// final WeakReference<ImageView> imageViewReference = new WeakReference<ImageView>(imageView);
	// String graphPath = idFrom + "/picture";
	// Bundle params = new Bundle();
	// params.putBoolean("redirect", false);
	// params.putInt("width", 100);
	// resource.getFbLoaderManager().load(new FbUserLoader(mContext, graphPath, params) {
	//
	// @Override
	// public void onFbLoaderSuccess(FbCmtFrom f) {
	//
	// if (imageViewReference != null) {
	// final ImageView imageView = imageViewReference.get();
	// if (imageView != null) {
	// int pos = (Integer) imageView.getTag();
	// Log.v("LOG", "log>>>" + "SUCCESS:" + pos);
	// mList.get(pos).getFrom().setSource(f.getSource());
	// imageLoader.displayImage(f.getSource(), imageView, options, null);
	// }
	// }
	// }
	//
	// @Override
	// public void onFbLoaderStart() {
	// }
	//
	// @Override
	// public void onFbLoaderFail(Throwable e) {
	// }
	// });
	// }

	class MyClickListener implements OnClickListener, OnMenuItemClickListener {

		int id;

		public MyClickListener(int id) {
			this.id = id;
		}

		@Override
		public void onClick(View v) {
			PopupMenu popup = new PopupMenu(mContext, v);
			popup.setOnMenuItemClickListener(this);
			MenuInflater inflater = popup.getMenuInflater();
			inflater.inflate(R.menu.menu_commend, popup.getMenu());
			popup.show();
		}

		@Override
		public boolean onMenuItemClick(MenuItem arg0) {
			FbCmtData dto = mList.get(id);
			if (resource.getSqlite().insertData(dto.getMessage())) {
				Toast.makeText(mContext, "Save success:" + dto.getMessage(), Toast.LENGTH_SHORT).show();
			}
			return false;
		}

	}

}
