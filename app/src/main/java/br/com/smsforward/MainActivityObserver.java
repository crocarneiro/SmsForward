package br.com.smsforward;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

public class MainActivityObserver implements LifecycleEventObserver {
    private AppCompatActivity activity;

    public MainActivityObserver(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {

    }
}
