package com.castrodev.wishlist.detail;

/**
 * Created by rodrigocastro on 07/04/17.
 */

public interface DetailPresenter {
    void validateData(String what, String when, String why, String where, String howMuch);

    void onDestroy();
}
