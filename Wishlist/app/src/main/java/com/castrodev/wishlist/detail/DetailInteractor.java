package com.castrodev.wishlist.detail;

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

        void onSuccess();
    }

    void save(Wish wish, OnSaveFinishedListener listener);

}