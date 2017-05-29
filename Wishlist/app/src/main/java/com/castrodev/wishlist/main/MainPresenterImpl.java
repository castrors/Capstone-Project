package com.castrodev.wishlist.main;

import com.castrodev.wishlist.model.Wish;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
    public void onItemClicked(final DatabaseReference databaseReference) {
        if (mainView != null) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Wish wish = dataSnapshot.getValue(Wish.class);
                    mainView.goToDetailActivity(wish, databaseReference);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onSuccess(Query items) {
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
