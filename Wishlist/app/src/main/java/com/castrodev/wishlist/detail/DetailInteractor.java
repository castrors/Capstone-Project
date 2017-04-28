package com.castrodev.wishlist.detail;

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

    void save(String what, String when, String why, String where, String howMuch, String observation, OnSaveFinishedListener listener);

}