package com.castrodev.wishlist.detail;

import com.castrodev.wishlist.model.Location;

/**
 * Created by rodrigocastro on 07/04/17.
 */

public interface DetailPresenter {
    void validateData(String what, String when, String why, Location where, String howMuch, String observation, String photoUrl);

    void onDestroy();
}
