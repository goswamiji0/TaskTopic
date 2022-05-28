package com.rgg.assignment.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkInfo;

import com.rgg.assignment.App;
import com.rgg.assignment.R;
import com.rgg.assignment.adapters.TopicAdapter;
import com.rgg.assignment.factory.ViewModelFactory;
import com.rgg.assignment.model.Topic;
import com.rgg.assignment.repository.TopicRepository;
import com.rgg.assignment.service.TopicService;
import com.rgg.assignment.viewmodel.MainViewModel;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private MainViewModel mainViewModel;
    private ProgressBar mProgressBar;
    private TopicAdapter topicAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the ViewModel
        TopicService topicService = App.get().getTopicService();
        TopicRepository mRepository = new TopicRepository(getApplication(),  topicService);
        ViewModelFactory factory = new ViewModelFactory(mRepository);
        mainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        mainViewModel.setWorkerConstraints();


        // Show work info, goes inside onCreate()
        mainViewModel.getOutputWorkInfo().observe(this, listOfWorkInfo -> {
            if (listOfWorkInfo == null || listOfWorkInfo.isEmpty()) {
                return;
            }
            WorkInfo workInfo = listOfWorkInfo.get(0);
            if (workInfo.getState() == WorkInfo.State.ENQUEUED) {
                showWorkFinished();
                mainViewModel.getTopics().observe(this, new Observer<List<Topic>>() {
                    @Override
                    public void onChanged(List<Topic> books) {
                        topicAdapter = new TopicAdapter(MainActivity.this, books);
                        recyclerView.setAdapter(topicAdapter);

                    }
                });
            } else {
                showWorkInProgress();
            }
        });

    }

    private void showWorkInProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void showWorkFinished() {
        mProgressBar.setVisibility(View.GONE);
    }


}
