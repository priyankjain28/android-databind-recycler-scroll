package com.peeru.labs.task.ui.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class CustomLifeCycleOwner extends AppCompatActivity implements LifecycleOwner {

    private LifecycleRegistry mLifecycleRegistry;

    public CustomLifeCycleOwner() {
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    public void stopListening() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    public void startListening() {
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    public Lifecycle getLifecycle() {
        Log.i("CustomLifeCycleOwner", "Returning registry!!");
        return mLifecycleRegistry;
    }
}