package com.rgg.assignment.repository;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;


import com.rgg.assignment.room.TopicDao;
import com.rgg.assignment.room.TopicDatabase;
import com.rgg.assignment.model.Topic;
import com.rgg.assignment.service.TopicService;

import java.util.List;

public class TopicRepository {
    private static TopicDao topicDao;
    private final android.app.Application application;
    private static String LOG_TAG = TopicRepository.class.getSimpleName();


    public TopicRepository(android.app.Application application, TopicService topicService) {
        this.application = application;
        TopicDatabase db = TopicDatabase.getDatabase(application);
        topicDao = db.topicDao();
    }

    public LiveData<List<Topic>> getTopics() {
        return topicDao.getTopics();
    }

    public android.app.Application getApplication() {
        return application;
    }


}
