package com.khunglong.xanh.zoom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sonnt_commonandroid.utils.PreferenceUtil;
import com.khunglong.xanh.MsConstant;
import com.khunglong.xanh.R;
import com.khunglong.xanh.libs.TouchImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class SingleTouchImageViewActivity extends Activity {

	private TouchImageView image;
	private DecimalFormat df;
	private String link;
	private String content;
	private ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_touchimageview);
		if (getIntent().getStringExtra("image") != null) {
			link = getIntent().getStringExtra("image");
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
		 imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(link, image);
		
		ImageView iClose = (ImageView) findViewById(R.id.zoom_img_close);
		iClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		ImageView iSave = (ImageView) findViewById(R.id.zoom_img_save);
		iSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
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
							//you can create a new file name "test.jpg" in sdcard folder.
							File folder = new File(Environment.getExternalStorageDirectory()
									+ File.separator + "KLX" );
							if (!folder.exists()) {
								folder.mkdirs();
							}
							
							String title = "KLX_";
							int i = PreferenceUtil.getPreference(getApplicationContext(), MsConstant.KEY_SAVE, 0);
							title += i;
							title += ".jpg";
							
							File f = new File(Environment.getExternalStorageDirectory()
									+ File.separator + "KLX" +File.separator + title);
							f.createNewFile();
							//write the bytes in file
							FileOutputStream fo = new FileOutputStream(f);
							fo.write(bytes.toByteArray());
							
							// remember close de FileOutput
							fo.close();
							PreferenceUtil.setPreference(getApplicationContext(), MsConstant.KEY_SAVE, ++i);
							Toast.makeText(SingleTouchImageViewActivity.this,
				                    "Your image is saved to this folder:" + f.toString(), Toast.LENGTH_LONG)
				                    .show();
                        } catch (Exception e) {
                        	Log.e("","log>>>" + "error save Image:" + e.toString());
                        	Toast.makeText(SingleTouchImageViewActivity.this,
				                    "Error save Imager", Toast.LENGTH_LONG)
				                    .show();
                        }
						
					}
					
					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						// TODO Auto-generated method stub
						
					}
				});
				
				
          
			}
		});

	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		finish();
//		return true;
//	}
}
