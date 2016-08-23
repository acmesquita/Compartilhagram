package com.catharina.compartilhagram;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by catharina on 22/08/16.
 */
public class CompartilhagramApplication extends Application {

    private static CompartilhagramApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext())
                .name("compartilhagram.realm")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public static CompartilhagramApplication getInstance() {
        return context;
    }
}
