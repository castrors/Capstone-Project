package com.castrodev.wishlist;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by rodrigocastro on 25/05/17.
 */

public class WishListApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
    }
}
