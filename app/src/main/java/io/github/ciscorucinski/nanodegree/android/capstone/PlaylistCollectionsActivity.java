/*******************************************************************************
 * Copyright 2016 Christopher Rucinski
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package io.github.ciscorucinski.nanodegree.android.capstone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PlaylistCollectionsActivity extends AppCompatNavigationDrawerActivity {

	public static Intent createIntent(Context context) {
		return new Intent(context, PlaylistCollectionsActivity.class);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// setContentView(R.layout.collection_all_drawer);
		// TODO: 1/18/2016
		setContentView(R.layout.collection_all_drawer);

		Authenticator.log(this);

		/**
		 * The {@link android.support.v4.view.PagerAdapter} that will provide
		 * fragments for each of the sections. We use a
		 * {@link FragmentPagerAdapter} derivative, which will keep every
		 * loaded fragment in memory. If this becomes too memory intensive, it
		 * may be best to switch to a
		 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
		 */
		CollectionsPagerAdapter mCollectionsPagerAdapter;

		/**
		 * The {@link ViewPager} that will host the section contents.
		 */
		ViewPager mViewPager;

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mCollectionsPagerAdapter = new CollectionsPagerAdapter(getSupportFragmentManager());

		setupToolbarAndNavigation();

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mCollectionsPagerAdapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(mViewPager);
		tabLayout.setSelectedTabIndicatorColor(Color.WHITE);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_toolbar_collections_all, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == LoginActivity.REQUEST_CODE) {

			Log.e("Login Attempt", "Is user logged in? " + Authenticator.isUserSignedIn(this));

			Authenticator.log(this);

		}

	}

	@Override
	protected void onStart() {

		super.onStart();

		if (!Authenticator.isUserSignedIn(this)) {

			startActivityForResult(
					LoginActivity.createIntent(this),
					LoginActivity.REQUEST_CODE);

		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		//		if (toggle.onOptionsItemSelected(item)) {
		//
		//			drawer.setScrimColor(DEFAULT_LEFT_SCRIM_COLOR);
		//			return true;
		//
		//		}

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();


		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class CollectionsFragment extends Fragment {

		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		private PlaylistsRecyclerAdapter adapter;

		public CollectionsFragment() { }

		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 */
		public static CollectionsFragment newInstance(int sectionNumber) {

			CollectionsFragment fragment = new CollectionsFragment();
			Bundle args = new Bundle();

			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);

			return fragment;

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		                         Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.collection_all_content_fragment, container,
			                                 false);

			//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
			//            textView.setText(getString(R.string.section_format, getArguments()
			// .getInt(ARG_SECTION_NUMBER)));

			adapter = new PlaylistsRecyclerAdapter(getActivity(), new ArrayList<>());

			RecyclerView collectionsList;

			collectionsList = (RecyclerView) rootView.findViewById(R.id.playlist_collection_list);
			collectionsList.setAdapter(adapter);
			collectionsList.setItemAnimator(new DefaultItemAnimator());
			collectionsList.setClickable(true);

			return rootView;

		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class CollectionsPagerAdapter extends FragmentPagerAdapter {

		public CollectionsPagerAdapter(FragmentManager fm) {

			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			// getItem is called to instantiate the fragment for the given page.
			// Return a CollectionsFragment (defined as a static inner class below).
			return CollectionsFragment.newInstance(position + 1);

		}

		@Override
		public int getCount() { return 2; }

		@Override
		public CharSequence getPageTitle(int position) {

			switch (position) {
				case 0:
					return "Liked YouTube Videos";
				case 1:
					return "Personal";

			}

			return null;

		}
	}

}
