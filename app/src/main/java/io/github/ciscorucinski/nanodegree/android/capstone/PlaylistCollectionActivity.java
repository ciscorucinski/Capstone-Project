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
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.youtube.player.YouTubePlayer;

public class PlaylistCollectionActivity extends AppCompatNavigationDrawerActivity
		implements YouTubeVideoFragment.YouTubeVideoFragmentListener {

	private static final int RECOVERY_DIALOG_REQUEST = 1;

	private YouTubePlayer.OnInitializedListener youTubeInitializer;
	private YouTubePlayer.Provider youTubeProvider;

	public static Intent createIntent(Context context) {
		return new Intent(context, PlaylistCollectionActivity.class);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.collection_one_drawer);

		setupToolbarAndNavigation();

		DrawerLayout drawer = getDrawer();

		if (getResources().getBoolean(R.bool.is_drawer_locked)) {

			drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, GravityCompat.END);

		} else {

			drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.END);
			drawer.closeDrawer(GravityCompat.END);

		}

		YouTubeVideoFragment youTubePlayerFragment = YouTubeVideoFragment.newTestInstance();

		// Add the fragment to the Activity
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.youtube_fragment_container, youTubePlayerFragment,
						YouTubeVideoFragment.TAG)
				.commit();

	}

	@Override
	public boolean onPrepareOptionsMenu(final Menu menu) {

		getMenuInflater().inflate(R.menu.activity_toolbar_collection_one, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.action_toolbar_open_playlist) {
			DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
			drawer.openDrawer(GravityCompat.END);
			return true;
		} else if (id == R.id.action_toolbar_video_information) {
			showVideoInformation();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void showVideoInformation() {

//		String htmlEncodedMessage = String.format                                     (
//				getString(R.string.dialog_text_template),
//				getString(R.string.dialog_text_domain), jokes.getJokeProvider(),
//				getString(R.string.dialog_text_url), jokes.getJokeProviderURL(),
//				getString(R.string.dialog_text_notes), getString(R.string.dialog_note));
//
//		new AlertDialog.Builder(this)
//				.setTitle(getString(R.string.dialog_title))
//				.setMessage(Html.fromHtml(htmlEncodedMessage))
//				.setPositiveButton(getString(R.string.dialog_button_ok), null)
//				.setNeutralButton(getString(R.string.dialog_button_copy_url),
//				                  new ClipboardListener(context, jokes.getJokeProviderURL()))
//				.show();
		new AlertDialog.Builder(this)
				.setTitle("Example Title")
				.setMessage("Example message")
				.setPositiveButton("Positive Button", null)
				.setNeutralButton("Neutral Button", null)
				.show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == RECOVERY_DIALOG_REQUEST) {
			// Retry initialization if user performed a recovery action
			youTubeProvider.initialize(Developer.YOUTUBE_KEY, youTubeInitializer);
		}

	}

	@Override
	public void onReveiveYouTubeProvider(YouTubePlayer.Provider provider,
										 YouTubePlayer.OnInitializedListener initializer) {

		this.youTubeProvider = provider;
		this.youTubeInitializer = initializer;

	}

	@Override
	public void onFragmentInteraction(Uri uri) {

	}

	public void onPlaylistPlaybackItemClick(MenuItem item) {



	}

	public void onPlaylistEditClick(MenuItem item) {


	}

	public void onPlaylistOverflowClick(View view) {

		PopupMenu popup = new PopupMenu(this, view);

		MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(R.menu.drawer_end_menu, popup.getMenu());

	}

	public void onPlaylistPlaybackControlClick(View view) {


	}
}
