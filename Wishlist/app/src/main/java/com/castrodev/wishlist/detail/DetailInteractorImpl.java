package com.castrodev.wishlist.detail;

import android.text.TextUtils;


/**
 * Created by rodrigocastro on 07/04/17.
 */

public class DetailInteractorImpl implements DetailInteractor {

    @Override
    public void save(String what, String when, String why, String where, String howMuch, String observation, OnSaveFinishedListener listener) {
        boolean error = false;
        if (TextUtils.isEmpty(what)) {
            listener.onWhatError();
            error = true;
            return;
        }
        if (TextUtils.isEmpty(when)) {
            listener.onWhenError();
            error = true;
            return;
        }

        if (TextUtils.isEmpty(why)) {
            listener.onWhyError();
            error = true;
            return;
        }

        if (TextUtils.isEmpty(where)) {
            listener.onWhereError();
            error = true;
            return;
        }

        if (TextUtils.isEmpty(howMuch)) {
            listener.onHowMuchError();
            error = true;
            return;
        }

        if (!error) {
            listener.onSuccess();
        }
    }
}