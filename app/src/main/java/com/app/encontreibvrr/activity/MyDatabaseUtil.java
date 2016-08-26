package com.app.encontreibvrr.activity;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by adria on 31/07/2016.
 */

public class MyDatabaseUtil {

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }

        return mDatabase;
    }

}
