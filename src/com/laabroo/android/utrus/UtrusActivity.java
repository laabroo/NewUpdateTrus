package com.laabroo.android.utrus;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.laabroo.android.utrus.network.DownloadImage;
import com.laabroo.android.utrus.network.GetData;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class UtrusActivity extends Activity {
	private static final String TAG = "MAIN";
	DownloadImage image;
	ImageView img;
	GetData data;
	ProgressDialog dialog;
	TextView name;
	Button btnTwitter;
	Button btnFb;
	Button btnFollow;
	Button btnCute;
	Activity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		name = (TextView) findViewById(R.id.text);
		btnFb = (Button) findViewById(R.id.btn_fb);
		btnFollow = (Button) findViewById(R.id.btn_follow);
		btnTwitter = (Button) findViewById(R.id.btn_twitter);
		btnCute = (Button) findViewById(R.id.btn_cute);
		img = (ImageView) findViewById(R.id.img);


	}

	public void onStart() {
		super.onStart();
		setContent();
	}

	public void onRestart() {
		super.onRestart();
		setContent();
	}

	public void onResume() {
		super.onResume();
		setContent();
	}

	public void onStop() {
		super.onStop();
	}

	/************* SET DATA **********************************************/

	private void setContent() {

		try {
			if (data == null) {
				data = new GetData();
			}
			String json = data.loadData();
			Log.i(TAG, json);
			JSONObject jsonObject = (JSONObject) new JSONTokener(json)
					.nextValue();
			String id = jsonObject.getString("id");
			String first = jsonObject.getString("firstname");
			String last = jsonObject.getString("lastname");
			final String fb = jsonObject.getString("facebook");
			final String twit = jsonObject.getString("twitter");
			final String follow = jsonObject.getString("follow");
			final String cute = jsonObject.getString("cute");

			name.setText(first + " " + last);
			if (fb.equals("")) {
				btnFb.setEnabled(false);
			}
			if (twit.equals("")) {
				btnTwitter.setEnabled(false);
			}
			if (follow.equals("")) {
				btnFollow.setEnabled(false);
			}
			if (cute.equals("")) {
				btnCute.setEnabled(false);
			}

			btnFb.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					loadUrl(fb);
				}
			});
			btnTwitter.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					loadUrl(twit);
				}
			});
			btnFollow.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					alert(follow + " Followers");
				}
			});
			btnCute.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					alert(cute + " persons think her cute");
				}
			});

			Log.i(TAG, id);
			String urlImage = "http://updaterus.com/images/users/" + id
					+ "/1.jpg";

			if (image == null) {
				image = new DownloadImage();
			}

			Bitmap bitmap = image.downloadImage(urlImage);
			img.setImageBitmap(bitmap);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/************* MENU **********************************************/

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	public boolean onOptionSelectedItem(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			onRestart();
			break;
		case R.id.menu_about:
			about();
			break;
		}
		return true;
	}

	/************* ACTION **********************************************/

	private void loadUrl(String url) {
		WebView webView = new WebView(this);
		webView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		webView.loadUrl(url);

	}

	private void alert(String message) {
		Toast.makeText(this.getApplication(), message, Toast.LENGTH_LONG)
				.show();
	}

	private void about() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("About");
		builder.setMessage(
				"Thanks to updaterus.com. \nCreate by @laabroo and @cemindil.")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

					}
				});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();

	}

}