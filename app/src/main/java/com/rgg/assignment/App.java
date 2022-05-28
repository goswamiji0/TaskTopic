package com.rgg.assignment;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rgg.assignment.factory.LiveDataCallAdapterFactory;
import com.rgg.assignment.room.TopicDao;
import com.rgg.assignment.room.TopicDatabase;
import com.rgg.assignment.service.TopicService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();
    private static final String BASE_URL = "https://62910767665ea71fe13fd2b3.mockapi.io/";
    private TopicService mTopicService;

    private static App INSTANCE;

    public static App get() {
        return INSTANCE;
    }
    private static TopicDao topicDao;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;


        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Log.i(TAG,message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mTopicService = mRetrofit.create(TopicService.class);
        topicDao = TopicDatabase.getDatabase(getApplicationContext()).topicDao();

    }
    public TopicDao getTopicDao() {
        return topicDao;
    }
    public TopicService getTopicService() {
        return mTopicService;
    }


}
