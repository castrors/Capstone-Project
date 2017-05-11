package com.castrodev.wishlist.main;

import com.castrodev.wishlist.model.Wish;

import java.util.List;

/**
 * Created by rodrigocastro on 10/05/17.
 */

public class MainPresenterImpl implements MainPresenter, MainInteractor.OnFinishedListener {

    private MainView mainView;
    private MainInteractor mainInteractor;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        this.mainInteractor = new MainInteractorImpl();
    }

    @Override
    public void onResume() {
        if (mainView != null && mainInteractor != null) {
            if(mainView.isConnected()){
                mainView.showProgress();
                mainInteractor.fetchWishes(this);
            } else {
                mainView.showNetworkError();
            }
        }
    }

    @Override
    public void onItemClicked(Wish wish) {
        if (mainView != null) {
            mainView.goToDetailActivity(wish);
        }
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onSuccess(List<Wish> items) {
        if (mainView != null) {
            mainView.hideProgress();
            mainView.setItems(items);
        }
    }

    @Override
    public void onError() {
        if (mainView != null) {
            mainView.hideProgress();
            mainView.showDefaultError();
        }
    }
}
