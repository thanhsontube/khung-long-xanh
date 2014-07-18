package com.khunglong.xanh.comments;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.khunglong.xanh.R;
import com.khunglong.xanh.myfacebook.object.FbCmtData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class CmtAdapter extends ArrayAdapter<FbCmtData> {
	private List<FbCmtData> mList;
	private Context mContext;
	ImageLoader imageLoader;
	private DisplayImageOptions options;
	private boolean isAnswer;

	public CmtAdapter(Context context, List<FbCmtData> list, boolean isAnswer) {
		super(context, 0, list);
		mContext = context;
		mList = list;
		this.isAnswer = isAnswer;

		imageLoader = ImageLoader.getInstance();
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.chat_emotion_icon)
		.showImageForEmptyUri(R.drawable.chat_emotion_icon)
		.showImageOnFail(R.drawable.chat_emotion_icon)
		.imageScaleType(ImageScaleType.EXACTLY)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(100))
		.build();
//		if (isAnswer) {
//			options = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.chat_emotion_icon)
//			.showImageForEmptyUri(R.drawable.chat_emotion_icon)
//			.showImageOnFail(R.drawable.chat_emotion_icon)
//			.imageScaleType(ImageScaleType.EXACTLY)
//			.cacheInMemory(true)
//			.cacheOnDisk(true)
//			.considerExifParams(true)
//			.displayer(new RoundedBitmapDisplayer(100))
//			.build();
//		} else  {
//			
//			options = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.chat_emotion_icon)
//			.showImageForEmptyUri(R.drawable.chat_emotion_icon)
//			.showImageOnFail(R.drawable.chat_emotion_icon)
//			.imageScaleType(ImageScaleType.EXACTLY)
//			.cacheInMemory(true)
//			.cacheOnDisk(true)
//			.considerExifParams(true)
//			.build();
//		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		final Holder holder;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.row_comments, parent, false);
			holder = new Holder();
			holder.img = (ImageView) v.findViewWithTag("icon");
			holder.img.setTag(position);
			holder.txtTitle = (TextView) v.findViewWithTag("title");
			holder.txtTime = (TextView) v.findViewWithTag("time");
			holder.txtLikes = (TextView) v.findViewWithTag("likes");
			holder.txtCmts = (TextView) v.findViewWithTag("cmt");
			holder.viewCmt = v.findViewWithTag("ll_cmt");
			v.setTag(holder);
		} else {
			holder = (Holder) v.getTag();
		}

		final FbCmtData dto = mList.get(position);
		holder.txtTitle.setText(dto.getMessage());
		holder.txtTime.setText(dto.getFrom().getName());
		holder.txtLikes.setText("" + dto.getLike_count());
		imageLoader.displayImage(dto.getFrom().getSource(), holder.img, options, null);
		if ((position & 1) == 0) {
			v.setBackgroundResource(R.drawable.btn_common_selector1);
		} else {
			v.setBackgroundResource(R.drawable.btn_common_selector2);
		}
		
		if (isAnswer) {
			holder.viewCmt.setVisibility(View.GONE);
		} else {
			holder.viewCmt.setVisibility(View.VISIBLE);
		}
		return v;
	}

	public void setData(List<FbCmtData> objects) {
		mList.clear();
		mList.addAll(objects);
		notifyDataSetChanged();
	}

	static class Holder {
		ImageView img;
		TextView txtTitle;
		TextView txtTime;
		TextView txtLikes;
		TextView txtCmts;
		
		View viewCmt;
	}

//	public String getDayAgo(String str, boolean isAsk, Context context) {
//		try {
//			long dateLong = DatetimeUtils.stringToDate(str);
//			if (isAsk) {
//				return DatetimeUtils.getTimeAgoVnAsk(dateLong, context);
//
//			}
//			return DatetimeUtils.getTimeAgoVnAnswer(dateLong, context);
//
//		} catch (Exception e) {
//			return null;
//		}
//	}
}
