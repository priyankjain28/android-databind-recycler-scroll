package com.peeru.labs.task.ui.ui;


import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.peeru.labs.task.R;
import com.peeru.labs.task.databinding.ActivityMainBinding;
import com.peeru.labs.task.ui.viewmodal.MainViewModel;

public class MainActivity extends CustomLifeCycleOwner {
    ActivityMainBinding mainBinding;
    MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(getApplicationContext(),mainBinding);
        mainBinding.setFilter(mainViewModel);
        setSupportActionBar(mainBinding.toolbarTop);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getLifecycle().addObserver(new MainComponent(getApplicationContext(),mainBinding,mainViewModel));
    }
}
