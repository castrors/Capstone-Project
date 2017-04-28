package com.castrodev.wishlist.detail;

import com.castrodev.wishlist.model.Location;
import com.castrodev.wishlist.model.Wish;
import com.castrodev.wishlist.utils.DateUtils;
import com.castrodev.wishlist.utils.WishUtils;

import static com.castrodev.wishlist.detail.DetailActivity.FORMAT;

/**
 * Created by rodrigocastro on 07/04/17.
 */

public class DetailPresenterImpl implements DetailPresenter, DetailInteractor.OnSaveFinishedListener {

    private DetailView detailView;
    private DetailInteractor detailInteractor;
    private Wish wish;

    public DetailPresenterImpl(DetailView detailView) {
        this.detailView = detailView;
        this.detailInteractor = new DetailInteractorImpl();
    }

    @Override
    public void validateData(String what, String when, String why, Location where, String howMuch, String observation) {
        if (detailView != null) {
            detailView.showProgress();
        }
        wish = new Wish(what, DateUtils.getDate(when, FORMAT), WishUtils.getPriority(why), where, WishUtils.getValue(howMuch), observation);
        detailInteractor.save(wish, this);
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
            save();

            detailView.navigateToHome();
        }
    }

    private void save() {

//        mMessagesDatabaseReference.push().setValue(friendlyMessage);
    }
}

