package com.example.sonnt_commonandroid.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class HttpRequestUtils {
	private static final String TAG = "HttpRequestUtils";
	static FilterLog log = new FilterLog(TAG);
	private static final int CONNECTION_TIME_OUT = 6000;
	private static final int SOCK_TIME_OUT = 6000;

	public static String makeRequestGET(String uri) {
		log.d("TEST >>> makeRequestGET uri >>>> " + uri);
		HttpParams httpParameters;
		httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used.
		HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIME_OUT);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		HttpConnectionParams.setSoTimeout(httpParameters, SOCK_TIME_OUT);
		String result = null;
		HttpClient httpclient = new DefaultHttpClient(httpParameters);
		try {
			// log.e("TEST>>> Uri.encode(uri) >>>> " + Uri.encode(uri));
			HttpGet request = new HttpGet(uri);
			ResponseHandler<String> handler = new BasicResponseHandler();
			return httpclient.execute(request, handler);
		} catch (Exception e) {
			log.e("TEST", "makeRequestGET err >>>> " + e.getMessage());
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return result;
	}

	public static String makeRequestPOST(String sentURL, String data) throws IOException {
		StringBuilder builder = new StringBuilder();

		// connection object
		HttpURLConnection conn = null;

		// stream read from response
		BufferedReader bufferedReader = null;

		try {

			String urlCreateFolder = sentURL;
			URL url = new URL(urlCreateFolder);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", data.length() + "");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setConnectTimeout(CONNECTION_TIME_OUT);
			// Time to wait to read data from server
			conn.setReadTimeout(CONNECTION_TIME_OUT * 4);
			// Send the body
			DataOutputStream dataOS = new DataOutputStream(conn.getOutputStream());

			// write head body
			dataOS.writeBytes(data);
			dataOS.flush();
			dataOS.close();

			// Ensure we got the HTTP 200 response code
			int responseCode = conn.getResponseCode();
			if (responseCode != HttpStatus.SC_OK) {
				return null;
			}

			// get output
			bufferedReader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String line;
			// reading each line
			while ((line = bufferedReader.readLine()) != null) {
				builder.append(line);
			}

			// close stream
			bufferedReader.close();

			// disconnect
			conn.disconnect();

			return builder.toString();

		} catch (IOException e) {
			throw e;
		} finally {
			// close stream reader
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			// disconnect
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	public static String makeRequestGET2(String sentURL) {
		StringBuilder builder = new StringBuilder();

		// connection object
		HttpURLConnection conn = null;

		// stream read from response
		BufferedReader bufferedReader = null;

		try {

			String urlCreateFolder = sentURL;
			URL url = new URL(urlCreateFolder);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("GET");
			// conn.setRequestProperty("Content-Length", data.length() + "");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setConnectTimeout(CONNECTION_TIME_OUT);
			// Time to wait to read data from server
			conn.setReadTimeout(CONNECTION_TIME_OUT * 4);
			// Send the body
			// DataOutputStream dataOS = new DataOutputStream(
			// conn.getOutputStream());
			//
			// // write head body
			// // dataOS.writeBytes(data);
			// dataOS.flush();
			// dataOS.close();

			// Ensure we got the HTTP 200 response code
			int responseCode = conn.getResponseCode();
			if (responseCode != HttpStatus.SC_OK) {
				return null;
			}

			// get output
			bufferedReader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String line;
			// reading each line
			while ((line = bufferedReader.readLine()) != null) {
				builder.append(line);
			}

			// close stream
			bufferedReader.close();

			// disconnect
			conn.disconnect();

			return builder.toString();

		} catch (Exception e) {
			Log.e(TAG, "NECVN>>>" + "error:" + e.toString());
			return null;
		}
	}

}
