package com.castrodev.wishlist.main;

import com.google.firebase.database.Query;

/**
 * Created by rodrigocastro on 28/04/17.
 */

public interface MainInteractor {

    interface OnFinishedListener {
        void onSuccess(Query itemsQuery);
        void onError();

    }

    void fetchWishes(OnFinishedListener listener);
}
