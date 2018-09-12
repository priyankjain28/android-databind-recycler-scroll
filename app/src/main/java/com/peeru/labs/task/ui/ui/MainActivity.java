package com.peeru.labs.task.ui.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.peeru.labs.task.R;
import com.peeru.labs.task.data.model.Song;
import com.peeru.labs.task.databinding.ActivityMainBinding;
import com.peeru.labs.task.ui.adapter.VerticalRecyclerAdapter;
import com.peeru.labs.task.ui.util.Constant;
import com.peeru.labs.task.ui.util.SongFilterType;
import com.peeru.labs.task.ui.viewmodal.MainViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MainViewModel mainViewModel;
    private List<Song> songList;
    SongFilterType songFilterType;
    VerticalRecyclerAdapter songAdapter;
    ActivityMainBinding mainBinding;
    String filterType = "Artist";
    Integer count = 1;
    String arrayItemsForDropdown[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        attachUI();
        loadData();
        mainViewModel.getSongList().observe(this, songs -> {
            if (MainActivity.this.songList == null) {
                setListData(songs);
            }
        });

    }

    //region Helper Methods
    private void attachUI() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initializeRecycleView();
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
   }
   //endregion

    // region Initialize View
    private void initializeRecycleView() {
        mainBinding.recyclerView.setHasFixedSize(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainBinding.recyclerView.setLayoutManager(layoutManager);
    }
    //endregion

    //region Data Process
    private void loadData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            dataSetup();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
    }
    private void saveSong(String name, String artist, String album) {
        mainViewModel.addSong(new Song(
                name,
                artist,
                album
        ));
    }
    //endregion

    //region Add data to Adapter
    public void setListData(final List<Song> songList) {
        this.songList = songList;
        if (!songList.isEmpty()) {
            songFilterType = new SongFilterType(songList);
            switch (filterType) {
                case "Artist":
                    songAdapter = new VerticalRecyclerAdapter(songFilterType.getArtistList(), count);
                    break;
                case "Album":
                    songAdapter = new VerticalRecyclerAdapter(songFilterType.getAlbumList(), count);
                    break;
            }
            mainBinding.recyclerView.setAdapter(songAdapter);
        }
    }
    //endregion

    // region Data Read from CSV and store in DB
    public void dataSetup() {
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = getAssets().open("sample_music_data.csv");
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(",");
                saveSong(row[0], row[1], row[2]);
            }

        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: " + e);
            }
        }
    }
    //endregion

    //region Configure Toolbar and Menu Options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Do something that differs the Activity's menu here
        menu.clear();
        getMenuInflater().inflate(R.menu.song_display, menu);
        MenuItem mitem = menu.findItem(R.id.item);
        Spinner spin = (Spinner) mitem.getActionView();
        setNavigationList();
        setupSpinner(spin);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public void setNavigationList() {
        arrayItemsForDropdown = getResources().getStringArray(R.array.song_filter);
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(getApplicationContext(), R.array.song_filter, R.layout.custom_spinner_item);
        list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setListNavigationCallbacks(list, new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                if (arrayItemsForDropdown[itemPosition].equals(Constant.ARTIST)) {
                    filterType = Constant.ARTIST;
                    setListData(songList);
                } else {
                    filterType = Constant.ALBUM;
                    setListData(songList);
                }
                return true;
            }
        });
    }


    public void setupSpinner(final Spinner spin) {
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.custom_spinner_item, getResources().getStringArray(R.array.song_display));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(myAdapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                count = Integer.valueOf(spin.getSelectedItem().toString());
                setListData(songList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    //endregion
}
