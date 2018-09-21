package com.teamnoname.streetartzone;

import android.support.multidex.MultiDexApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SoeulApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
//        Realm.getInstance(new RealmConfiguration.Builder()
//        .deleteRealmIfMigrationNeeded()
//        .build());
    }
}
