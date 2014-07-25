package com.khunglong.xanh.zoom;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.khunglong.xanh.R;
import com.khunglong.xanh.libs.TouchImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SingleTouchImageViewActivity extends Activity {

	private TouchImageView image;
	private DecimalFormat df;
	private String link;
	private String content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_touchimageview);
		if (getIntent().getStringExtra("link") != null) {
			link = getIntent().getStringExtra("link");
			content = getIntent().getStringExtra("content");
		}
		getActionBar().hide();

//		getActionBar().setTitle("Zoom");
//		getActionBar().setDisplayHomeAsUpEnabled(true);
		//
		// DecimalFormat rounds to 2 decimal places.
		//
		df = new DecimalFormat("#.##");
		image = (TouchImageView) findViewById(R.id.img);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(link, image);
		
		ImageView iClose = (ImageView) findViewById(R.id.zoom_img_close);
		iClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		finish();
//		return true;
//	}
}
