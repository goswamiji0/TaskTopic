package com.rgg.assignment.worker;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.rgg.assignment.App;
import com.rgg.assignment.R;
import com.rgg.assignment.room.TopicDao;
import com.rgg.assignment.model.Topic;
import com.rgg.assignment.service.TopicService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class SyncDataWorker extends Worker {
    private TopicService topicService;
    private TopicDao topicDao;

    public SyncDataWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
        topicService = App.get().getTopicService();
        topicDao = App.get().getTopicDao();
    }

    @NonNull
    @Override
    public Result doWork() {

        Context applicationContext = getApplicationContext();
        WorkerUtils.sleep();

        try {
            Call<List<Topic>> call = topicService.fetchTopics();
            Response<List<Topic>> response = call.execute();

            if (response.isSuccessful() && response.body() != null && !response.body().isEmpty() && response.body().size() > 0) {
                topicDao.deleteTopics();
                topicDao.saveTopics(response.body());
                Toast.makeText(applicationContext, "Update new data", Toast.LENGTH_SHORT).show();
                return Result.success();
            } else {
                return Result.retry();
            }


        } catch (Throwable e) {
            e.printStackTrace();
            return Result.failure();
        }
    }
}
