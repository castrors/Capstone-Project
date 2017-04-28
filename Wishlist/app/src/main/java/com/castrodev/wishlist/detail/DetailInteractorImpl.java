package com.castrodev.wishlist.detail;


import com.castrodev.wishlist.model.Location;
import com.castrodev.wishlist.model.Wish;
import com.castrodev.wishlist.utils.DateUtils;
import com.castrodev.wishlist.utils.WishUtils;

import static com.castrodev.wishlist.detail.DetailActivity.FORMAT;

/**
 * Created by rodrigocastro on 07/04/17.
 */

public class DetailInteractorImpl implements DetailInteractor {

    @Override
    public void validate(String what, String when, String why, Location where, String howMuch, String observation, OnSaveFinishedListener listener) {
        Wish wish = new Wish(what, DateUtils.getDate(when, FORMAT), WishUtils.getPriority(why), where, WishUtils.getValue(howMuch), observation);

        if (WishUtils.isEmpty(wish.getName())) {
            listener.onWhatError();
            return;
        }
        if (!WishUtils.isValidDate(wish.getDueDate())) {
            listener.onWhenError();
            return;
        }

        if (!WishUtils.isPriorityValid(wish.getPriority())) {
            listener.onWhyError();
            return;
        }

        if (!WishUtils.isLocationValid(wish.getLocation())) {
            listener.onWhereError();
            return;
        }

        if (!WishUtils.isValueValid(wish.getValue())) {
            listener.onHowMuchError();
            return;
        }

        listener.onValidationSuccess(wish);
    }
}