package com.khunglong.xanh.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;

public class DatetimeUtils {
	 // SimpleDateFormat can be used to control the date/time display format:
    //   E (day of week): 3E or fewer (in text xxx), >3E (in full text)
    //   M (month): M (in number), MM (in number with leading zero)
    //              3M: (in text xxx), >3M: (in full text full)
    //   h (hour): h, hh (with leading zero)
    //   m (minute)
    //   s (second)
    //   a (AM/PM)
    //   H (hour in 0 to 23)
    //   z (time zone)

	private static final String MY_DATE_FORMAT = "yyyy-MM-dd (E) HH:mm:ss";

	@SuppressLint("SimpleDateFormat")
    public static String getTimtFromLongTime(String format, long time){
		String s = "";
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(time);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			s = dateFormat.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static String getTimtFromLongTime(long time){
		return getTimtFromLongTime(MY_DATE_FORMAT, time);
	}
	
	private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    public static String getTimeAgo(long time, Context ctx) {
        // TODO: use DateUtils methods instead
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }
        Calendar c = Calendar.getInstance();
        long now = c.getTimeInMillis();
        if (time > now || time <= 0) {
            return null;
        }
        final long diff = now - time;
        if (diff < MINUTE) {
            return "just now";
        } else if (diff < 2 * MINUTE) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE) {
            return diff / MINUTE + " minutes ago";
        } else if (diff < 90 * MINUTE) {
            return "an hour ago";
        } else if (diff < 24 * HOUR) {
            return diff / HOUR + " hours ago";
        } else if (diff < 48 * HOUR) {
            return "yesterday";
        } else {
            return diff / DAY + " days ago";
        }
    }
    
    public static String getTimeAgoVnAsk(long time, Context ctx) {
        // TODO: use DateUtils methods instead
//        if (time < 1000000000000L) {
//            // if timestamp given in seconds, convert to millis
//            time *= 1000;
//        }
        Calendar c = Calendar.getInstance();
        
        long now = c.getTimeInMillis();
        if (time > now || time <= 0) {
            return null;
        }
        final long diff = now - time;
        if (diff < MINUTE) {
            return "Vừa mới hỏi";
        } else if (diff < 2 * MINUTE) {
            return "Hỏi 1 phút trước";
        } else if (diff < 50 * MINUTE) {
            return "Hỏi " + diff / MINUTE + " phút trước";
        } else if (diff < 90 * MINUTE) {
            return "Hỏi 1 giờ trước";
        } else if (diff < 24 * HOUR) {
            return "Hỏi " + diff / HOUR + " giờ trước";
        } else if (diff < 48 * HOUR) {
            return "Hỏi ngày hôm qua";
        } else {
            return "Hỏi " + diff / DAY + " ngày trước";
        }
    }
    
    public static String getTimeAgoVnAnswer(long time, Context ctx) {
        // TODO: use DateUtils methods instead
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }
        Calendar c = Calendar.getInstance();
        long now = c.getTimeInMillis();
        if (time > now || time <= 0) {
            return null;
        }
        final long diff = now - time;
        if (diff < MINUTE) {
            return "Vừa mới trả lời";
        } else if (diff < 2 * MINUTE) {
            return "Trả lời 1 phút trước";
        } else if (diff < 50 * MINUTE) {
        	return "Trả lời " + diff / MINUTE + " phút trước";
        } else if (diff < 90 * MINUTE) {
            return "Trả lời 1 giờ trước";
        } else if (diff < 24 * HOUR) {
        	 return "Trả lời " + diff / HOUR + " giờ trước";
        } else if (diff < 48 * HOUR) {
            return "Trả lời ngày hôm qua";
        } else {
            return "Trả lời " + diff / DAY + " ngày trước";
        }
    }
    
    
    public static long stringToDate(String str) {
    	//2014-05-15T03:26:30.833010
//    	str = str.substring(0, 19);
//    	String dtStart = "2010-10-15T09:27:37Z"; 
    	str = "2014-05-15T03:26:30.833010";
    	SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault());  
    	try {  
    	    Date date = format.parse(str);  
    	    return date.getTime();
    	} catch (Exception e) {  
    	    e.printStackTrace();  
    	    return 0;
    	}
    }

}
