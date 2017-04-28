package com.castrodev.wishlist.detail;

import com.castrodev.wishlist.model.Location;
import com.castrodev.wishlist.model.Wish;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    public void validateData(String what, String when, String why, Location where, String howMuch, String observation) {
        if (detailView != null) {
            detailView.showProgress();
        }

        detailInteractor.validate(what, when, why, where, howMuch, observation, this);
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
    public void onValidationSuccess(Wish wish) {
        if (detailView != null) {
            save(wish);
            detailView.navigateToHome();
        }
    }

    private void save(Wish wish) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference wishesDatabaseReference = firebaseDatabase.getReference().child("wishes");
        wishesDatabaseReference.push().setValue(wish);
    }
}

