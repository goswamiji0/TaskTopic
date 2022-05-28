package com.rgg.assignment.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


import com.rgg.assignment.model.Topic;

import java.util.List;

@Dao
public interface TopicDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveTopics(List<Topic> topics);


    @Transaction
    @Query("SELECT * from topic")
    LiveData<List<Topic>> getTopics();

    //delete
    @Transaction
    @Query("DELETE FROM topic")
    void deleteTopics();
}