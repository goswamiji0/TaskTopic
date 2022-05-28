package com.rgg.assignment.viewmodel;

import static com.rgg.assignment.util.Constants.SYNC_DATA_WORK_NAME;
import static com.rgg.assignment.util.Constants.TAG_SYNC_DATA;

import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;


import com.rgg.assignment.model.Topic;
import com.rgg.assignment.repository.TopicRepository;
import com.rgg.assignment.util.Resource;
import com.rgg.assignment.worker.SyncDataWorker;
import com.rgg.assignment.worker.WorkerUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainViewModel extends AndroidViewModel {
    private TopicRepository mRepository;
    private WorkManager mWorkManager;
    private LiveData<List<WorkInfo>> mSavedWorkInfo;
    public MainViewModel(TopicRepository mRepository) {
        super(mRepository.getApplication());
        this.mRepository = mRepository;
        mWorkManager = WorkManager.getInstance(mRepository.getApplication());
        mSavedWorkInfo = mWorkManager.getWorkInfosByTagLiveData(TAG_SYNC_DATA);
    }

    public void setWorkerConstraints() {

        // Create Network constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();


        PeriodicWorkRequest periodicSyncDataWork =
                new PeriodicWorkRequest.Builder(SyncDataWorker.class, 15, TimeUnit.MINUTES)
                        .addTag(TAG_SYNC_DATA)
                        .setConstraints(constraints)
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();
        mWorkManager.enqueueUniquePeriodicWork(
                SYNC_DATA_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP, //Existing Periodic Work policy
                periodicSyncDataWork //work request
        );

    }

    public LiveData<List<Topic>> getTopics() {
        return mRepository.getTopics();
    }

    public LiveData<List<WorkInfo>> getOutputWorkInfo() {
        return mSavedWorkInfo;
    }

}
