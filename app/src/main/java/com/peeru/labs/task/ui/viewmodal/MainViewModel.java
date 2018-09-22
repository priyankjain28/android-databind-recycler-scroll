package com.peeru.labs.task.ui.viewmodal;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.widget.Spinner;

import com.peeru.labs.task.data.Database;
import com.peeru.labs.task.data.model.Song;
import com.peeru.labs.task.databinding.ActivityMainBinding;
import com.peeru.labs.task.ui.adapter.VerticalRecyclerAdapter;
import com.peeru.labs.task.ui.ui.CustomLifeCycleOwner;
import com.peeru.labs.task.ui.util.SongFilterType;

import java.util.ArrayList;
import java.util.List;

@InverseBindingMethods({
        @InverseBindingMethod(type = Spinner.class, attribute = "android:selectedItemPosition"),
})
public class MainViewModel extends BaseObservable {

    private Database appDatabase;
    private final LiveData<List<Song>> songList;
    private List<Song> listOfSong;
    private String[] songFilters;
    private String songFilter;
    private int positionFilter;
    private int positionDisplay;
    private String[] songDisplays;
    private String songDisplay;
    private SongFilterType songFilterType;
    private VerticalRecyclerAdapter songAdapter;
    private ActivityMainBinding mainBinding;
    private CustomLifeCycleOwner customLifeCycleOwner;

    public MainViewModel(Context context, ActivityMainBinding mainBinding) {
        this.mainBinding = mainBinding;
        customLifeCycleOwner = new CustomLifeCycleOwner();
        appDatabase = Database.getDatabase(context);
        songList = appDatabase.songDao().getAllSongs();
        setupSpinner();
    }

    //region Set Spinner Data
    private void setupSpinner(){
        List<String> allFilters = new ArrayList<String>();
        allFilters.add("Artist");
        allFilters.add("Album");
        songFilters = allFilters.toArray(new String[allFilters.size()]);

        List<String> counter = new ArrayList<String>();
        for (Integer i = 1; i <= 10; i++) {
            counter.add("" + i);
        }
        songDisplays = counter.toArray(new String[counter.size()]);
    }
    //end region

    //region Binding Data
    @Bindable
    public String[] getSongFilters() {
        return songFilters;
    }

    @Bindable
    public int getPositionFilter() {
        return positionFilter;
    }

    public void setPositionFilter(int positionFilter) {
        this.positionFilter = positionFilter;
        setListData(listOfSong,Integer.valueOf(positionFilter),Integer.valueOf(positionDisplay));

    }

    @Bindable
    public int getPositionDisplay() {
        return positionDisplay;
    }

    public void setPositionDisplay(int positionDisplay) {
        this.positionDisplay = positionDisplay;
        setListData(listOfSong,Integer.valueOf(positionFilter),Integer.valueOf(positionDisplay));
    }

    @Bindable
    public String getSongFilter() {
        return songFilter;
    }

    public void setSongFilter(String songFilter) {
        this.songFilter = songFilter;
    }

    @Bindable
    public String[] getSongDisplays() {
        return songDisplays;
    }

    @Bindable
    public String getSongDisplay() {
        return songDisplay;
    }

    public void setSongDisplay(String songDisplay) {
        this.songDisplay = songDisplay;
    }
    //endregion

    //region Display Data on Adapter
    public void setListData(final List<Song> songList,Integer filter, Integer display) {
        this.listOfSong = songList;
        if (!listOfSong.isEmpty()) {
            songFilterType = new SongFilterType(listOfSong);
            switch (songFilters[filter]) {
                case "Artist":
                    songAdapter = new VerticalRecyclerAdapter(songFilterType.getArtistList(), Integer.valueOf(songDisplays[display]));
                    break;
                case "Album":
                    songAdapter = new VerticalRecyclerAdapter(songFilterType.getAlbumList(),  Integer.valueOf(songDisplays[display]));
                    break;
            }
            mainBinding.recyclerView.setAdapter(songAdapter);
        }
    }
    //end region

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
