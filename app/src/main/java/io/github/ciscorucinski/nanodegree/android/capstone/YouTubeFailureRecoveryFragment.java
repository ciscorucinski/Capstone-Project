package io.github.ciscorucinski.nanodegree.android.capstone;

/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * An abstract activity which deals with recovering from errors which may occur during API
 * initialization, but can be corrected through user action.
 */
abstract class YouTubeFailureRecoveryFragment extends YouTubePlayerSupportFragment
        implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {

        if (errorReason.isUserRecoverableError()) {

            errorReason.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST).show();

        } else {

            String errorMessage = String.format(getString(R.string.error_player),
                    errorReason.toString());
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();

        }
    }

    //	@Override
    //	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //
    //		if (requestCode == RECOVERY_DIALOG_REQUEST) {
    //			// Retry initialization if user performed a recovery action
    //			getYouTubePlayerProvider().initialize(Developer.YOUTUBE_KEY, this);
    //		}
    //	}

    protected abstract YouTubePlayer.Provider getYouTubePlayerProvider();

}
