package com.github.piasy.perfectandroidmodel;

import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Piasy{github.com/Piasy} on 7/30/16.
 */

public interface GithubApi {
    @GET("users/{user}/following")
    Observable<List<GithubUser>> following(@Path("user") String user);
}
