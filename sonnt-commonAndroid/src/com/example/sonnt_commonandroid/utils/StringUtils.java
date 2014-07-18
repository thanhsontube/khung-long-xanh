package com.example.sonnt_commonandroid.utils;

public class StringUtils {
	
	public static boolean isEmpty(String s){
		if (s == null || s.trim().equals("") ) {
			return true;
		}
		return false;
	}
	
	public static boolean equals(String s1, String s2) {
		if(isEmpty(s1)) {
			return false;
		}
		
		if(isEmpty(s2)){
			return false;
		}
		
		return s1.equals(s2);
	}
	
	public static String convert(int i) {
		return String.valueOf(i);
	}
	
	public static String convert (float i){
		return String.valueOf(i);
	}
	
	public static String convert (double i){
		return String.valueOf(i);
	}
	
	public static String getFirst(String s) {
		if (isEmpty(s)) {
			return "";
		}
		return s.substring(0, 1);
	}
	
}
