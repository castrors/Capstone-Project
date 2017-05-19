package com.castrodev.wishlist.main;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by rodrigocastro on 28/04/17.
 */

class MainInteractorImpl implements MainInteractor {
    @Override
    public void fetchWishes(OnFinishedListener listener) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        listener.onSuccess(databaseReference.child("wishes"));
    }
}
