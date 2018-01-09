package com.qdcares.qcrvdemo.http;


import com.qdcares.qcrvdemo.enity.MovieEnity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by handaolin on 2017/3/13.
 */

public class HttpMethod {
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    private static final int TIMEOUT = 5;
    private Retrofit retrofit;
    private AppService appService;

    //私有的无参构造方法
    private HttpMethod() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        appService = retrofit.create(AppService.class);
    }

    //单例
    private static class SingleHolder {
        private static final HttpMethod INSTANCE = new HttpMethod();
    }

    public static HttpMethod getInstance() {
        return SingleHolder.INSTANCE;
    }


    /**
     * 获取最新日报列表
     */
    public void getlastest(Subscriber<MovieEnity> subscriber, int count, int start) {
        appService.getThemesInfo(start, count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
