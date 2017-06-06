package com.castrodev.wishlist.detail;

import android.content.Context;
import android.net.Uri;

import com.castrodev.wishlist.model.Wish;

/**
 * Created by rodrigocastro on 07/04/17.
 */

public interface DetailPresenter {
    void validateData(Wish wish, String wishKey);

    void uploadPhoto(Context context, Uri photoUri);

    void onDestroy();

    void deleteWish(String wishKey);
}
