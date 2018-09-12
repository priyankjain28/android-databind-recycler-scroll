package com.peeru.labs.task.data;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.peeru.labs.task.data.dao.SongDao;
import com.peeru.labs.task.data.model.Song;

@android.arch.persistence.room.Database(
        entities = {Song.class},
        version = 1
)

public abstract class Database extends RoomDatabase {

    private static Database INSTANCE;

    public static Database getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), Database.class, "jetpack_db")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public abstract SongDao songDao();
}