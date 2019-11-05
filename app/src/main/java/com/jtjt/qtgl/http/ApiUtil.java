package com.jtjt.qtgl.http;

import android.util.Log;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Created by 董凯强 on 2018/3/9 16 ${MTNUTE}.
 */

public class ApiUtil {

    private static Retrofit retrofit = null;
    private static ApiService apiService = null;
    private static OkHttpClient okHttpClient = null;

    public static ApiService getApiService() {

        if (apiService != null) {
            return apiService;
        } else {

            HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BODY;
            //新建log拦截器
            HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.e("OkHttpClient","网络请求路径OkHttpMessage:"+message);
                }
            });
            loggingInterceptor.setLevel(level);



            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .addInterceptor(new LogInterceptor(MyApp.getApp()))
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                    .build();






            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiService.BASE_URL)
                    .client(okHttpClient)
//                    .addConverterFactory(ScalarsConverterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addConverterFactory(JacksonConverterFactory.create())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        /*    retrofit = new Retrofit.Builder()
                    .baseUrl(ApiService.BASE_URL)
//                    .client(okHttpClient)
//                    .addConverterFactory(ScalarsConverterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addConverterFactory(JacksonConverterFactory.create())
                    .addConverterFactory(FastJsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                    .build();*/




            apiService = retrofit.create(ApiService.class);

            return getApiService();
        }
    }
}
