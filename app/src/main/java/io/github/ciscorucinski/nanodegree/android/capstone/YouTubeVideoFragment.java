/*******************************************************************************
 * Copyright 2016 Christopher Rucinski
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except
 * in compliance with the License. You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express
 * or implied. See the License for the specific language governing permissions and limitations
 * under
 * the License.
 ******************************************************************************/

package io.github.ciscorucinski.nanodegree.android.capstone;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;


/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment must implement the
 * {@link YouTubeVideoFragment.YouTubeVideoFragmentListener} interface to handle interaction
 * events. Use the {@link YouTubeVideoFragment#newTestInstance} factory method to create an
 * instance of
 * this fragment.
 */
public class YouTubeVideoFragment extends YouTubeFailureRecoveryFragment {

    public static final String TAG = "video_fragment";
    private static final String ARG_YOUTUBE_VIDEO_ID = "video_id";

    private String mYouTubeVideoID;
    private YouTubeVideoFragmentListener mListener;
    private YouTubePlayer youTubePlayer;

    public YouTubeVideoFragment() {
    }

    /**
     * Use this factory method to create a new instance of this fragment.
     *
     * @return A new instance of fragment YouTubeVideoFragment.
     */
    public static YouTubeVideoFragment newTestInstance() {

        YouTubeVideoFragment fragment = new YouTubeVideoFragment();
        Bundle args = new Bundle();

        args.putString(ARG_YOUTUBE_VIDEO_ID, "nCgQDjiotG0");
        fragment.setArguments(args);

        return fragment;

    }

    /**
     * Use this factory method to create a new instance of this fragment using the provided
     * parameters.
     *
     * @param youTubeVideoID YouTube video ID
     * @return A new instance of fragment YouTubeVideoFragment.
     */
    public static YouTubeVideoFragment newInstance(@NonNull String youTubeVideoID) {

        YouTubeVideoFragment fragment = new YouTubeVideoFragment();
        Bundle args = new Bundle();

        args.putString(ARG_YOUTUBE_VIDEO_ID, youTubeVideoID);
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer, boolean b) {

        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        this.youTubePlayer = youTubePlayer;

        playCurrentVideo();

    }

    private void playCurrentVideo() {

        this.youTubePlayer.cueVideo(mYouTubeVideoID);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mYouTubeVideoID = getArguments().getString(ARG_YOUTUBE_VIDEO_ID);
        }

        initialize(Developer.YOUTUBE_KEY, this);

    }

    public void updateVideo(@NonNull String videoID) {

        Bundle args = new Bundle();

        args.putString(ARG_YOUTUBE_VIDEO_ID, videoID);
        setArguments(args);

        this.mYouTubeVideoID = videoID;

    }

    public void onButtonPressed(Uri uri) {

        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        if (context instanceof YouTubeVideoFragmentListener) {
            mListener = (YouTubeVideoFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement YouTubeVideoFragmentListener");
        }

        mListener.onReveiveYouTubeProvider(getYouTubePlayerProvider(), this);

    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {

        return (YouTubePlayerSupportFragment) getFragmentManager().findFragmentByTag(TAG);

    }

    @Override
    public void onDetach() {

        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this fragment to allow an
     * interaction in this fragment to be communicated to the activity and potentially other
     * fragments contained in that activity.
     * <p/>
     * See the Android Training lesson <a href= "http://developer.android
     * .com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface YouTubeVideoFragmentListener {

        void onReveiveYouTubeProvider(YouTubePlayer.Provider provider,
                                      YouTubePlayer.OnInitializedListener initializer);

        //		void onReceiveYouTubeInitializerListener(YouTubePlayer.OnInitializedListener
        // listener);
        void onFragmentInteraction(Uri uri);

    }
}
