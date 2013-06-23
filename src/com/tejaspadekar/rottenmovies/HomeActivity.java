package com.tejaspadekar.rottenmovies;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group5.rottenmovies.R;
import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.LoaderSettings;
import com.novoda.imageloader.core.LoaderSettings.SettingsBuilder;
import com.novoda.imageloader.core.cache.LruBitmapCache;

public class HomeActivity extends Activity {

	public static ImageManager imageManager;

	public static final ImageManager getImageManager() {
		return imageManager;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		int PERCENTAGE_OF_CACHE = 50;
		LoaderSettings settings = new SettingsBuilder()
				.withCacheManager(new LruBitmapCache(this, PERCENTAGE_OF_CACHE))
				.withDisconnectOnEveryCall(true).build(this);
		imageManager = new ImageManager(this, settings);
		LinearLayout layout = (LinearLayout) findViewById(R.id.home_layout);
		renderSections(layout);
		layout.setVisibility(View.VISIBLE);

	}

	public void renderSections(LinearLayout layout) {

		for (String section : AppConfiguration.SECTIONS) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View carousel = inflater.inflate(R.layout.card_carousel, null);
			TomatoesAPIClient.makeBackendCall(section, null,
					new MovieCarouselResponseHandler(this, carousel));
			TextView title = (TextView) carousel
					.findViewById(R.id.carousel_title);
			title.setText(AppConfiguration.SECTION_TITLES.get(section));
			layout.addView(carousel);

		}
	}

	public static String convertStreamToString(InputStream is) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\n");
		}
		return sb.toString();
	}

	public static String getStringFromFile(AssetManager am, String filePath)
			throws Exception {
		InputStream is = am.open(filePath);
		String ret = convertStreamToString(is);
		// Make sure you close all streams.
		is.close();
		return ret;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	/**
	 * Recursively sets a {@link Typeface} to all {@link TextView}s in a
	 * {@link ViewGroup}.
	 */
	public static final void setAppFont(ViewGroup mContainer, Typeface mFont) {
		if (mContainer == null || mFont == null)
			return;

		final int mCount = mContainer.getChildCount();

		// Loop through all of the children.
		for (int i = 0; i < mCount; ++i) {
			final View mChild = mContainer.getChildAt(i);
			if (mChild instanceof TextView) {
				// Set the font if it is a TextView.
				((TextView) mChild).setTypeface(mFont);
			} else if (mChild instanceof ViewGroup) {
				// Recursively attempt another ViewGroup.
				setAppFont((ViewGroup) mChild, mFont);
			}
		}
	}

}
