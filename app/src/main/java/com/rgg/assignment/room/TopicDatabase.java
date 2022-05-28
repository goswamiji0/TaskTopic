package com.rgg.assignment.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rgg.assignment.model.Topic;


@Database(entities = {Topic.class}, version = 1, exportSchema = false)
public abstract class TopicDatabase extends RoomDatabase {
    private static volatile TopicDatabase INSTANCE;

    public abstract TopicDao topicDao();

    public static TopicDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TopicDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TopicDatabase.class, "topic_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}