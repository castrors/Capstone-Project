package com.castrodev.wishlist.detail;

import android.content.Context;
import android.net.Uri;

import com.castrodev.wishlist.model.Location;

/**
 * Created by rodrigocastro on 07/04/17.
 */

public interface DetailPresenter {
    void validateData(String what, String when, String why, Location where, String howMuch, String observation, String photoUrl, String photoPath);

    void uploadPhoto(Context context, Uri photoUri);

    void onDestroy();
}
