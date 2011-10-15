package com.laabroo.android.utrus.network;

import java.io.IOException;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DownloadImage {

	private Bitmap bitmap = null;
	private InputStream is = null;
	private HttpDownload download;

	public DownloadImage() {

	}

	public Bitmap downloadImage(String urlImage) {
		try {
			if (download == null) {
				download = new HttpDownload();
			}
			is = download.OpenHttpConnection(urlImage);
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

}
