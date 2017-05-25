package com.castrodev.wishlist.detail;

import android.content.Context;
import android.net.Uri;

import com.castrodev.wishlist.model.Location;
import com.castrodev.wishlist.model.Wish;

/**
 * Created by rodrigocastro on 07/04/17.
 */

public interface DetailInteractor {

    interface OnSaveFinishedListener {
        void onWhatError();

        void onWhenError();

        void onWhyError();

        void onWhereError();

        void onHowMuchError();

        void onValidationSuccess(Wish wish);
    }

    interface OnUploadFinishedListener {
        void onUploadSuccess(String photoUrl);
        void onUploadError();
    }

    void validate(String what, String when, String why, Location where, String howMuch, String observation, String photoUrl, String photoPath, OnSaveFinishedListener detailPresenter);

    void uploadPhoto(Context context, Uri photoUri, OnUploadFinishedListener uploadListener);
}