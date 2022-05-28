package com.rgg.assignment.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.rgg.assignment.repository.TopicRepository;
import com.rgg.assignment.viewmodel.MainViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final TopicRepository mRepository;

    public ViewModelFactory(TopicRepository mRepository) {
        this.mRepository = mRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(MainViewModel.class))
            return (T) new MainViewModel(mRepository);
        throw new IllegalArgumentException("Unknown ViewModel class");
    }


}

