/*******************************************************************************
 * Copyright 2016 Christopher Rucinski
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package io.github.ciscorucinski.nanodegree.android.capstone.service;

import java.io.IOException;

import io.github.ciscorucinski.nanodegree.android.capstone.model.LikedVideosResponse;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;


public class ServiceGenerator {

    public static final String API_BASE_URL = "https://www.googleapis.com/youtube/v3/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S create(Class<S> serviceClass) {

        return create(serviceClass, null);

    }

    public static <S> S create(Class<S> serviceClass, final AccessToken token) {

        if (token != null) {

            httpClient.addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Authorization", token.getAccessToken())
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);

                }

            });

        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public interface YouTubeClient {

        // TODO can I remove the API KEY text?
        @GET("videos?part=snippet&myRating=like&key={YOUR_API_KEY}")
        Call<LikedVideosResponse> currentUsersLikedVideos();

        @GET("videos?part=snippet&myRating=like&pageToken={token}&key={YOUR_API_KEY}")
        Call<LikedVideosResponse> currentUsersLikedVideosNextPage(String token);

    }
}
//	 https://www.googleapis.com/youtube/v3/videos?part=snippet&myRating=like&key={YOUR_API_KEY}
//	 https://www.googleapis.com/youtube/v3/videos?part=snippet&myRating=like&pageToken=pageToken&key={YOUR_API_KEY}
