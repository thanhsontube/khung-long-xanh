package com.khunglong.xanh.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sonnt_commonandroid.utils.PreferenceUtil;
import com.khunglong.xanh.MsConstant;
import com.khunglong.xanh.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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
            // Bitmap original = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            ImageLoader imageLoader = ImageLoader.getInstance();
            // String uri = entry.getSource();
            Bitmap original = imageLoader.loadImageSync(sUrl);

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

    public static void saveImage(final Context context, ImageLoader imageLoader, String link) {

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
                    int i = PreferenceUtil.getPreference(context, MsConstant.KEY_SAVE, 0);
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
                    PreferenceUtil.setPreference(context, MsConstant.KEY_SAVE, ++i);
                    Toast.makeText(context, R.string.your_image_is_saved_to_this_folder + f.toString(), Toast.LENGTH_LONG)
                            .show();
                } catch (Exception e) {
                    Log.e("", "log>>>" + "error save Image:" + e.toString());
                    Toast.makeText(context, "Error save Imager", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                // TODO Auto-generated method stub

            }
        });
    }

    public static void saveImage(final Context context, Bitmap loadedImage) {
        try {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            loadedImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            // you can create a new file name "test.jpg" in sdcard folder.
            File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "KLX");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String title = "KLX_";
            int i = PreferenceUtil.getPreference(context, MsConstant.KEY_SAVE, 0);
            title += i;
            title += ".jpg";

            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "KLX" + File.separator
                    + title);
            f.createNewFile();
            // write the bytes in file
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());

            // remember close de FileOutput
            fo.close();
            PreferenceUtil.setPreference(context, MsConstant.KEY_SAVE, ++i);
            Toast.makeText(context, R.string.your_image_is_saved_to_this_folder + f.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("", "log>>>" + "error save Image:" + e.toString());
            Toast.makeText(context, R.string.error_save_imager, Toast.LENGTH_LONG).show();
        }

    }
}
