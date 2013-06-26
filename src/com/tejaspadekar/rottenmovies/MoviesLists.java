/**
 * 
 */
package com.tejaspadekar.rottenmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @author padekar
 *
 */
public class MoviesLists extends Fragment {

	/**
	 * 
	 */
	public MoviesLists() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		ScrollView moviesContainer =  (ScrollView) inflater.inflate(R.layout.fragment_moviebrowser, container, false);
		LinearLayout layout = (LinearLayout) moviesContainer.findViewById(R.id.movie_browser);
		renderSections(layout, inflater);
		return moviesContainer;
	}
	
	public void renderSections(LinearLayout layout, LayoutInflater inflater) {
		for (String section : AppConfiguration.SECTIONS) {			
			View carousel = inflater.inflate(R.layout.card_carousel, null);
			TomatoesAPIClient.makeBackendCall(section, null, new MovieCarouselResponseHandler(this.getActivity(), carousel));
			TextView title = (TextView) carousel.findViewById(R.id.carousel_title);
			title.setText(AppConfiguration.SECTION_TITLES.get(section));
			layout.addView(carousel);
		}
	}

}
