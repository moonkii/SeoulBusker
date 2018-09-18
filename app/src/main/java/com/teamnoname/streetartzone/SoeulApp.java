package com.teamnoname.streetartzone;

import android.app.Application;

import io.realm.Realm;

public class SoeulApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
