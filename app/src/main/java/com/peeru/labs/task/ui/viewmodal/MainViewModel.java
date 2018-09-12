package com.peeru.labs.task.ui.viewmodal;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.peeru.labs.task.data.Database;
import com.peeru.labs.task.data.model.Song;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private Database appDatabase;
    private final LiveData<List<Song>> songList;

    public MainViewModel(@NonNull Application application) {
        super(application);
        appDatabase = Database.getDatabase(this.getApplication());
        //Initialize User List
        songList = appDatabase.songDao().getAllSongs();
    }

    //region Add Song
    public void addSong(Song song) {
        appDatabase.songDao().addSong(song);
        //new addAsyncTask(appDatabase).execute(song);
    }
    //endregion

    //region Get Live Song List
    public LiveData<List<Song>> getSongList() {
        return songList;
    }
    //endregion

}
