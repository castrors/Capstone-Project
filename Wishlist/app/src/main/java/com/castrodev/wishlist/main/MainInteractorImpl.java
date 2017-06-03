package com.castrodev.wishlist.main;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by rodrigocastro on 28/04/17.
 */

public class MainInteractorImpl implements MainInteractor {
    @Override
    public void fetchWishes(OnFinishedListener listener) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user !=null){
            DatabaseReference databaseReference = firebaseDatabase.getReference().child(user.getUid()).child("wishes");
            listener.onSuccess(databaseReference);
        }

    }
}
