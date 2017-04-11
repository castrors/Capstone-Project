package com.castrodev.wishlist.detail;

/**
 * Created by rodrigocastro on 07/04/17.
 */

public class DetailPresenterImpl implements DetailPresenter, DetailInteractor.OnSaveFinishedListener {

    private DetailView detailView;
    private DetailInteractor detailInteractor;

    public DetailPresenterImpl(DetailView detailView) {
        this.detailView = detailView;
        this.detailInteractor = new DetailInteractorImpl();
    }

    @Override
    public void validateData(String what, String when, String why, String where, String howMuch) {
        if (detailView != null) {
            detailView.showProgress();
        }

        detailInteractor.save(what, when, why, where, howMuch, this);
    }

    @Override
    public void onDestroy() {
        detailView = null;
    }

    @Override
    public void onWhatError() {
        if (detailView != null) {
            detailView.setWhatError();
            detailView.hideProgress();
        }
    }

    @Override
    public void onWhenError() {
        if (detailView != null) {
            detailView.setWhenError();
            detailView.hideProgress();
        }
    }

    @Override
    public void onWhyError() {
        if (detailView != null) {
            detailView.setWhyError();
            detailView.hideProgress();
        }
    }

    @Override
    public void onWhereError() {
        if (detailView != null) {
            detailView.setWhereError();
            detailView.hideProgress();
        }
    }

    @Override
    public void onHowMuchError() {
        if (detailView != null) {
            detailView.setHowMuchError();
            detailView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (detailView != null) {
            detailView.navigateToHome();
        }
    }
}

