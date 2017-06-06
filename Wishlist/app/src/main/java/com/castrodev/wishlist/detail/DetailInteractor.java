package com.castrodev.wishlist.detail;

import android.content.Context;
import android.net.Uri;

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

    void validate(Wish wish, OnSaveFinishedListener detailPresenter);

    void uploadPhoto(Context context, Uri photoUri, OnUploadFinishedListener uploadListener);

    void save(String wishKey, Wish wish);

    void delete(String wishKey);
}