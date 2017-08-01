package com.ijidou.retrofitdemo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/7/29.
 */

public interface MovieService {
    //获取豆瓣Top250 榜单
    @GET("top250")
    Call<String> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Observable<String> getTopMovie1(@Query("start") int start, @Query("count") int count);
}
