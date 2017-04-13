package com.castrodev.wishlist.detail;


import com.castrodev.wishlist.model.Wish;
import com.castrodev.wishlist.utils.WishUtils;

/**
 * Created by rodrigocastro on 07/04/17.
 */

public class DetailInteractorImpl implements DetailInteractor {

    @Override
    public void save(Wish wish, OnSaveFinishedListener listener) {
        boolean error = false;
        if (WishUtils.isEmpty(wish.getName())) {
            listener.onWhatError();
            error = true;
            return;
        }
        if (!WishUtils.isValidDate(wish.getDueDate())) {
            listener.onWhenError();
            error = true;
            return;
        }

        if (!WishUtils.isPriorityValid(wish.getPriority())) {
            listener.onWhyError();
            error = true;
            return;
        }

        if (!WishUtils.isLocationValid(wish.getLocation())) {
            listener.onWhereError();
            error = true;
            return;
        }

        if (!WishUtils.isValueValid(wish.getValue())) {
            listener.onHowMuchError();
            error = true;
            return;
        }

        if (!error) {
            listener.onSuccess();
        }
    }


}