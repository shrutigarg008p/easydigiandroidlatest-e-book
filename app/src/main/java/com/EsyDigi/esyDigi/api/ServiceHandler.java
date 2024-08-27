package com.EsyDigi.esyDigi.api;



import com.EsyDigi.esyDigi.utility.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceHandler {
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_LINK)
            .addConverterFactory(GsonConverterFactory.create());

    // Method of createService rest of Api
    public static <S> S createService(Class<S> serviceClass, boolean timeout) {
        OkHttpClient.Builder httpClient;
        if (timeout) {
            httpClient = new OkHttpClient.Builder().connectTimeout(150, TimeUnit.SECONDS).readTimeout(150, TimeUnit.SECONDS);
        } else {
            httpClient = new OkHttpClient.Builder().connectTimeout(150, TimeUnit.SECONDS).readTimeout(150, TimeUnit.SECONDS);
        }

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                builder.header("Accept", "application/json");
                builder.header("PLATFORM","ANDROID");
                builder.method(original.method(), original.body());
                Request request = builder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}