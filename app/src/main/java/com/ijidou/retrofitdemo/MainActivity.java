package com.ijidou.retrofitdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    private String BASE_URL = "https://api.douban.com/v2/movie/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofiltMethod();

        retrofitAndRxjavaMethod();

        RetrofitAndRxjavaAndOKHttpMethod();

    }

    private void RetrofitAndRxjavaAndOKHttpMethod() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5000, TimeUnit.MILLISECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        movieService.getTopMovie1(0,10)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted ----");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError ----" + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext ---- " + s);
                    }
                });
    }

    private void retrofitAndRxjavaMethod() {
        Retrofit retrofit1 = new Retrofit.Builder()    //创建一个retorfit实例    负责处理请求数据和请求的结果
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        MovieService movieService1 = retrofit1.create(MovieService.class);
        movieService1.getTopMovie1(0,10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError " + e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext   = " + s);
                    }
                });
    }

    private void retrofiltMethod() {
        Retrofit retrofit = new Retrofit.Builder()    //创建一个retorfit实例    负责处理请求数据和请求的结果
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        //获取网络接口实例
        MovieService movieService = retrofit.create(MovieService.class);
//        调用方法得到一个Call
        Call<String> call = movieService.getTopMovie(0, 10);
        //进行网络请求
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i(TAG, "response = " + response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.i(TAG, "onFailure  = " + t.toString());
            }
        });
    }
}
