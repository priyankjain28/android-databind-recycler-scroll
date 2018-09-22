package com.peeru.labs.task.ui.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;

import com.peeru.labs.task.databinding.ActivityMainBinding;
import com.peeru.labs.task.ui.util.ReadCsvFile;
import com.peeru.labs.task.ui.viewmodal.MainViewModel;

/**
 * Created by Priyank Jain on 21-09-2018.
 */
public class MainComponent implements LifecycleObserver {
    private static final String TAG = "MainComponent";
    private final ActivityMainBinding mainBinding;
    private MainViewModel mainViewModel;
    private Context context;
    private ReadCsvFile readCsvFile;
    private CustomLifeCycleOwner customLifeCycleOwner;

    public MainComponent(Context context, ActivityMainBinding mainBinding,MainViewModel mainViewModel) {
        this.context = context;
        this.mainBinding = mainBinding;
        this.mainViewModel = mainViewModel;
        customLifeCycleOwner = new CustomLifeCycleOwner();
    }

    //region Lifecycle Component
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        initializeView();
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
    }
    //end region

    //region Helper Methods to load data, setup data
    private void initializeView() {
        readCsvFile = new ReadCsvFile(context, mainViewModel);
        initializeRecycleView();
        loadData();
        setUpData();
    }
    private void initializeRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(customLifeCycleOwner);
        mainBinding.recyclerView.setLayoutManager(layoutManager);
    }

    //endregion

    //region Data Load from CSV File
    private void loadData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if (!prefs.getBoolean("firstTime", false)) {
            readCsvFile.dataSetup();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
    }
    //endregion

    //region Data Set Up
    private void setUpData() {
        mainViewModel.getSongList().observe(customLifeCycleOwner, songs -> {
                mainViewModel.setListData(songs,0,0);
                mainViewModel.notifyChange();

        });
    }
    //end region
}
