package com.khunglong.xanh.utils;

import java.net.URL;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

public class BitmapUtils {

	public static Bitmap maskBitmap(Context context, int imageRes, int maskRes) {
		Resources resources = context.getResources();
		Bitmap original = BitmapFactory.decodeResource(resources, imageRes);
		Bitmap mask = BitmapFactory.decodeResource(resources, maskRes);
		original = Bitmap.createScaledBitmap(original, mask.getWidth(), mask.getHeight(), true);
		Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
		Canvas c = new Canvas(result);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		c.drawBitmap(original, 0, 0, null);
		c.drawBitmap(mask, 0, 0, paint);
		paint.setXfermode(null);
		return result;

	}
	
	public static Bitmap maskBitmap(Context context, String sUrl, int maskRes) {
		try {
	        
			Resources resources = context.getResources();
			URL url = new URL(sUrl);
			Bitmap original = BitmapFactory.decodeStream(url.openConnection().getInputStream());
			Bitmap mask = BitmapFactory.decodeResource(resources, maskRes);
			original = Bitmap.createScaledBitmap(original, mask.getWidth(), mask.getHeight(), true);
			Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
			Canvas c = new Canvas(result);
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
			c.drawBitmap(original, 0, 0, null);
			c.drawBitmap(mask, 0, 0, paint);
			paint.setXfermode(null);
			return result;
        } catch (Exception e) {
	       return null;
        }

	}
	
	public static Bitmap maskBitmap(Context context, Bitmap original, int maskRes) {
		Resources resources = context.getResources();
		Bitmap mask = BitmapFactory.decodeResource(resources, maskRes);
		original = Bitmap.createScaledBitmap(original, mask.getWidth(), mask.getHeight(), true);
		Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
		Canvas c = new Canvas(result);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		c.drawBitmap(original, 0, 0, null);
		c.drawBitmap(mask, 0, 0, paint);
		paint.setXfermode(null);
		return result;

	}
}
