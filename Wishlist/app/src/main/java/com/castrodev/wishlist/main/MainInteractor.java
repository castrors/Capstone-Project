package com.castrodev.wishlist.main;

import com.castrodev.wishlist.model.Wish;

import java.util.List;

/**
 * Created by rodrigocastro on 28/04/17.
 */

public interface MainInteractor {

    interface OnFinishedListener {
        void onSuccess(List<Wish> items);
        void onError();

    }

    void fetchWishes(OnFinishedListener listener);
}
