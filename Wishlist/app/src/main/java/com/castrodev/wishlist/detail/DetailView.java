package com.castrodev.wishlist.detail;

/**
 * Created by rodrigocastro on 07/04/17.
 */

public interface DetailView {
    void showProgress();

    void hideProgress();

    void setWhatError();

    void setWhenError();

    void setWhyError();

    void setWhereError();

    void setHowMuchError();

    void navigateToHome();

    void setPhotoUrl(String photoUrl);

    void setUploadError();
}
