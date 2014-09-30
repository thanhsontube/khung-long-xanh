package com.khunglong.xanh.notification;

import com.androidquery.AQuery;
import com.facebook.LoginActivity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;

public class NotificationUtils {
    public static void notify(Context context, String message, int icon) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        AQuery aQuery = new AQuery(context);
        Bitmap bitmap = aQuery.getCachedImage(icon);
        builder.setLargeIcon(bitmap);

        builder.setContentText(message);
        builder.setTicker(message);
        builder.setSmallIcon(icon);

        Intent intent = new Intent(context, LoginActivity.class);
        // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
