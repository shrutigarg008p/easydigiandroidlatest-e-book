package com.EsyDigi.esyDigi.api;


import com.EsyDigi.esyDigi.utility.Constants;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;


import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
public class BasicBuilder {

    private static Retrofit retrofit = null;
    private static Dispatcher dispatcher = null;


    public static Retrofit getClient() {
        if (retrofit==null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.readTimeout(5, TimeUnit.MINUTES);
            httpClient.connectTimeout(5, TimeUnit.MINUTES);
            httpClient.writeTimeout(5, TimeUnit.MINUTES);
            OkHttpClient client = httpClient.addInterceptor(interceptor).build();

            dispatcher=new Dispatcher();
            dispatcher.setMaxRequests(150);
            httpClient.dispatcher(dispatcher);



            Retrofit.Builder builder =
                    new Retrofit.Builder()
                            .baseUrl(Constants.HOST_LINK)
                            .client(client)
                            .addConverterFactory(JacksonConverterFactory.create());



            retrofit = builder.build();
           // Service servicee = retrofit.create(serviceClass);

        }

        return retrofit;
    }

    public static Dispatcher getDispatcher() {
        return dispatcher;
    }

    public static void cancelAllTask()
    {
        if (dispatcher!=null)
        {
            dispatcher.cancelAll();
        }
    }

    public static Message parseError(Response<?> response) {
        Converter<ResponseBody, Message> converter =

                retrofit.responseBodyConverter(Message.class, new Annotation[0]);

        Message error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new Message();
        }

        return error;
    }

}
