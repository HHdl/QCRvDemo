package com.qdcares.qcrvdemo.http;


import com.qdcares.qcrvdemo.enity.MovieEnity;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by handaolin on 2017/3/13.
 */

public interface AppService {

    @GET("top250")
    Observable<MovieEnity> getThemesInfo(@Query("start") int start, @Query("count") int count);
}
