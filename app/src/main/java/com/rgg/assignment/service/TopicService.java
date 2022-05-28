package com.rgg.assignment.service;

import androidx.lifecycle.LiveData;

import com.rgg.assignment.model.Topic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TopicService {

    @GET("getBooks")
    LiveData<ApiResponse<List<Topic>>> getTopics();


    @GET("getBooks")
    Call<List<Topic>> fetchTopics();
}
