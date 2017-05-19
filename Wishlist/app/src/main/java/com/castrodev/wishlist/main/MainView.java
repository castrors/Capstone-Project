package com.castrodev.wishlist.main;

import com.castrodev.wishlist.model.Wish;
import com.google.firebase.database.Query;

/**
 * Created by rodrigocastro on 28/04/17.
 */

public interface MainView {
    void showProgress();
    void hideProgress();
    void setItems(Query wishesQuery);
    void goToDetailActivity(Wish wish);
    void showDefaultError();
    void showNetworkError();
    boolean isConnected();
}

