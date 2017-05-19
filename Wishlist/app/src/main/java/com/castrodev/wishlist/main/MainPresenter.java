package com.castrodev.wishlist.main;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by rodrigocastro on 28/04/17.
 */
interface MainPresenter {

    void onResume();
    void onItemClicked(DatabaseReference wish);
    void onDestroy();
}