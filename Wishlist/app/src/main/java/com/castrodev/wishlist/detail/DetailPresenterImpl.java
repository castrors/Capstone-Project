package com.castrodev.wishlist.detail;

import android.content.Context;
import android.net.Uri;

import com.castrodev.wishlist.model.Wish;

/**
 * Created by rodrigocastro on 07/04/17.
 */

public class DetailPresenterImpl implements DetailPresenter, DetailInteractor.OnSaveFinishedListener, DetailInteractor.OnUploadFinishedListener {

    private DetailView detailView;
    private DetailInteractor detailInteractor;
    private String wishKey;

    public DetailPresenterImpl(DetailView detailView) {
        this.detailView = detailView;
        this.detailInteractor = new DetailInteractorImpl();
    }

    @Override
    public void validateData(Wish wish, String wishKey) {
        if (detailView != null) {
            detailView.showProgress();
        }

        this.wishKey = wishKey;
        detailInteractor.validate(wish, this);
    }

    @Override
    public void uploadPhoto(Context context, Uri photoUri) {
        if (detailView != null) {
            detailView.showProgress();
            detailInteractor.uploadPhoto(context, photoUri, this);
        }
    }

    @Override
    public void onDestroy() {
        detailView = null;
    }

    @Override
    public void deleteWish(String wishKey) {
        if (detailView != null) {
            detailInteractor.delete(wishKey);
            detailView.navigateToHome();
        }
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
    public void onValidationSuccess(Wish wish) {
        if (detailView != null) {
            detailInteractor.save(wishKey, wish);
            detailView.navigateToHome();
        }
    }


    @Override
    public void onUploadSuccess(String photoUrl) {
        if (detailView != null) {
            detailView.setPhotoUrl(photoUrl);
            detailView.hideProgress();
        }
    }

    @Override
    public void onUploadError() {
        if (detailView != null) {
            detailView.setUploadError();
        }
    }
}

