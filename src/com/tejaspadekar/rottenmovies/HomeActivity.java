package com.tejaspadekar.rottenmovies;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.LoaderSettings;
import com.novoda.imageloader.core.LoaderSettings.SettingsBuilder;
import com.novoda.imageloader.core.cache.LruBitmapCache;

public class HomeActivity extends FragmentActivity {

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
				.withDisconnectOnEveryCall(false).build(this);
		imageManager = new ImageManager(this, settings);
		if (savedInstanceState == null) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.replace(R.id.movies_container, new MoviesLists());
			ft.commit();
		}
		

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
