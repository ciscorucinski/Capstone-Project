/*******************************************************************************
 * Copyright 2016 Christopher Rucinski
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 ******************************************************************************/

package io.github.ciscorucinski.nanodegree.android.capstone;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PlaylistCollectionsActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	private static final int DEFAULT_LEFT_SCRIM_COLOR = 0x99000000;
	private static final int DEFAULT_RIGHT_SCRIM_COLOR = Color.TRANSPARENT;

	private int currentSlidingDrawer;

	private DrawerLayout drawer;
	private ActionBarDrawerToggle toggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// setContentView(R.layout.collection_all_drawer);
		// TODO: 1/18/2016
		setContentView(R.layout.collection_all_drawer);

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

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mCollectionsPagerAdapter = new CollectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mCollectionsPagerAdapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(mViewPager);
		tabLayout.setSelectedTabIndicatorColor(Color.WHITE);

		//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		//        fab.setOnClickListener(new View.OnClickListener() {
		//            @Override
		//            public void onClick(View view) {
		//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
		//                        .setAction("Action", null).show();
		//            }
		//        });

		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open,
				R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		NavigationView navigationSettingsView = (NavigationView) findViewById(R.id.nav_setting);
		NavigationView playlistView = (NavigationView) findViewById(R.id.playlist_view);

		navigationSettingsView.setScrollY(100);
		navigationView.setNavigationItemSelectedListener(this);
		navigationSettingsView.setNavigationItemSelectedListener(this);

	}

	@Override
	public void onBackPressed() {

		if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
		else super.onBackPressed();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.collections_all, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (toggle.onOptionsItemSelected(item)) {

			drawer.setScrimColor(DEFAULT_LEFT_SCRIM_COLOR);
			return true;

		}

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

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		//        if (id == R.id.nav_camera) {
		//            // Handle the camera action
		//        } else if (id == R.id.nav_gallery) {
		//
		//        } else if (id == R.id.nav_slideshow) {
		//
		//        } else if (id == R.id.nav_manage) {
		//
		//        } else if (id == R.id.nav_share) {
		//
		//        } else if (id == R.id.nav_send) {
		//
		//        }

		drawer.closeDrawer(GravityCompat.START);

		return true;
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

		//        @Override
		//        public void startUpdate(ViewGroup container) {
		//            super.startUpdate(container);
		//
		//            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		//            fab.show();
		//        }
		//
		//        @Override
		//        public void finishUpdate(ViewGroup container) {
		//            super.finishUpdate(container);
		//
		//            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		//            fab.hide();
		//        }

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
