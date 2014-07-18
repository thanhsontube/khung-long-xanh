package com.khunglong.xanh.utils;

public class DragonStringUtils {
	public static String parseImageString(String input) {
		try {
			String []array = input.split("/");
			array[6] = "pmaxxmax";
			StringBuilder sb = new StringBuilder();
			for (String string2 : array) {
                sb.append(string2);
                if (!string2.contains(".jpg")) {
                	sb.append("/");
                }
            }
			return sb.toString();
        } catch (Exception e) {
	        return null;
        }
	}
	
	public static String parseImageString(String input, String replace) {
		try {
			String []array = input.split("/");
			array[6] = replace;
			StringBuilder sb = new StringBuilder();
			for (String string2 : array) {
                sb.append(string2);
                if (!string2.contains(".jpg")) {
                	sb.append("/");
                }
            }
			return sb.toString();
        } catch (Exception e) {
	        return null;
        }
	}
	
	public static String parseImageString2(String input) {
		try {
			String []array = input.split("/");
			int i = 0;
			for (String string : array) {
	            if (string.equalsIgnoreCase("t1.0-9")) {
	            	break;
	            }
	            i ++;
            }
			
			
			array[++i] = "pmaxxmax";
			StringBuilder sb = new StringBuilder();
			for (String string2 : array) {
                sb.append(string2);
                if (!string2.contains(".jpg")) {
                	sb.append("/");
                }
            }
			return sb.toString();
        } catch (Exception e) {
	        return null;
        }
	}
}
