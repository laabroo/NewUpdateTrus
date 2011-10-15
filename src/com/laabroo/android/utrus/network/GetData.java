package com.laabroo.android.utrus.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpGet;

import android.net.Uri;
import android.util.Log;

public class GetData {
	private static final String TAG = "GetData";
	HttpURLConnection connection = null;


	public GetData() {
		// TODO Auto-generated constructor stub
	}

	public String loadData() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getDefault());
		int hour = calendar.getTime().getHours();
		int minutes = calendar.getTime().getMinutes();
		Log.i(TAG, String.valueOf(hour));
		Log.i(TAG, String.valueOf(minutes));
		String url = "http://www.updaterus.com/index/at_time/" + hour + "/"
				+ minutes;

		Uri uri = Uri.parse(url);
		HttpRequest request = new HttpGet(uri.toString());
		request.setHeader("X-Requested-With", "XMLHttpRequest");
		request.setHeader("application/json", "charset:utf-8");

		StringBuilder builder = new StringBuilder();

		try {
			URL newUrl = new URL(url);
			connection = (HttpURLConnection) newUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			int rc = connection.getResponseCode();
			String message = connection.getResponseMessage();
			Log.i(TAG + "-Respond :", new String(String.valueOf(rc)));
			Log.i(TAG + "-Status : ", message);

			if (rc == 200) {
				InputStream inputStream = new BufferedInputStream(
						connection.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(inputStream));
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					builder.append(line);
					Log.i(TAG + "- Balikan ", line);
				}

			}

		} catch (IOException e) {
			Log.i(TAG + " - IOExeption", e.getMessage());
		} catch (Exception e) {
			Log.i(TAG + "- Exeption", e.getMessage());
		} finally {
			connection.disconnect();
		}
		return builder.toString();

	}

}
