package com.castrodev.wishlist.detail;


/**
 * Created by rodrigocastro on 07/04/17.
 */

public class DetailInteractorImpl implements DetailInteractor {

    @Override
    public void save(String what, String when, String why, String where, String howMuch, OnSaveFinishedListener listener) {
        boolean error = false;
        if (isEmpty(what)) {
            listener.onWhatError();
            error = true;
            return;
        }
        if (isEmpty(when)) {
            listener.onWhenError();
            error = true;
            return;
        }

        if (isEmpty(why)) {
            listener.onWhyError();
            error = true;
            return;
        }

        if (isEmpty(where)) {
            listener.onWhereError();
            error = true;
            return;
        }

        if (isEmpty(howMuch)) {
            listener.onHowMuchError();
            error = true;
            return;
        }

        if (!error) {
            listener.onSuccess();
        }
    }

    private boolean isEmpty(String text) {
        return text != null && text.equals("");
    }
}