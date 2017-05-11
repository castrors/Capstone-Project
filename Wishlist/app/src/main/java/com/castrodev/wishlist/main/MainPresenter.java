package com.castrodev.wishlist.main;

import com.castrodev.wishlist.model.Wish;

/**
 * Created by rodrigocastro on 28/04/17.
 */
interface MainPresenter {

    void onResume();
    void onItemClicked(Wish wish);
    void onDestroy();
}