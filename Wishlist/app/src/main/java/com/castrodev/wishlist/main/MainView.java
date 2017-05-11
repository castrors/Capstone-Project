package com.castrodev.wishlist.main;

import com.castrodev.wishlist.model.Wish;

import java.util.List;

/**
 * Created by rodrigocastro on 28/04/17.
 */

public interface MainView {
    void showProgress();
    void hideProgress();
    void setItems(List<Wish> wishes);
    void goToDetailActivity(Wish wish);
    void showDefaultError();
    void showNetworkError();
    boolean isConnected();
}

