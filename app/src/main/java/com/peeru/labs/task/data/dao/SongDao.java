package com.peeru.labs.task.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.peeru.labs.task.data.model.Song;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Priyank Jain on 09-09-2018.
 */

@Dao
public interface SongDao {
    //Insert Song
    @Insert(onConflict = REPLACE)
    void addSong(Song song);

    //Get All Song
    @Query("SELECT * FROM Song")
    LiveData<List<Song>> getAllSongs();
}