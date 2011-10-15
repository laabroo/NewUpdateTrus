package com.laabroo.android.utrus.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpDownload {

	public InputStream OpenHttpConnection(String urlString) throws IOException {
		InputStream is = null;
		int respond = -1;

		URL url = new URL(urlString);
		URLConnection connection = url.openConnection();
		if (!(connection instanceof HttpURLConnection))
			throw new IOException("Not a Http Connection.");

		try {
			HttpURLConnection urlConnection = (HttpURLConnection) connection;
			urlConnection.setAllowUserInteraction(false);
			urlConnection.setInstanceFollowRedirects(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();

			respond = urlConnection.getResponseCode();
			if (respond == HttpURLConnection.HTTP_OK) {
				is = urlConnection.getInputStream();
			}
		} catch (Exception e) {
			throw new IOException("Error Connection.");
		}

		return is;
	}

}
