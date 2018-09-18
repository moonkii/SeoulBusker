package com.teamnoname.streetartzone;

import android.support.multidex.MultiDexApplication;

import io.realm.Realm;

public class SoeulApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
