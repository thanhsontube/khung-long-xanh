package com.example.sonnt_commonandroid.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;

public class FileUtils {
	private static final String TAG = "FileUtils";
	static FilterLog log = new FilterLog(TAG);
	public static String getStringFromAsset(Context context, String name){
		BufferedReader reader = null;
		StringBuilder str = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(context.getAssets().open(name)));
			String line;
			while((line = reader.readLine()) != null){
				str.append(line);
				str.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return str.toString();
	}
	
	public static boolean delete(String path){
		try {
			File f = new File(path);
			if(f.exists()){
				return f.delete();
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static void write(String path, String content, boolean isAppend){
		try {
			File file = new File(path);
			FileWriter fileWriter = new FileWriter(file, isAppend);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.append(content);
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (Exception e) {
			log.e("NECVN>>>" + "error write file: " + e.toString());
		}
	}
	
	public static String read(String path){
		try {
			File file = new File(path);
			StringBuilder builder = new StringBuilder();
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line;
			while((line = bufferedReader.readLine())!= null){
				builder.append(line);
			}
			bufferedReader.close();
			return builder.toString();

		} catch (Exception e) {
			log.e("NECVN>>>" + "error read file: " + e.toString());
			return "";
		}
	}
}
