package com.castrodev.wishlist.main;

import android.view.View;

import com.castrodev.wishlist.R;
import com.castrodev.wishlist.model.Wish;
import com.castrodev.wishlist.viewholder.WishViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by rodrigocastro on 10/05/17.
 */

class MainAdapter extends FirebaseRecyclerAdapter<Wish, WishViewHolder> {

    private MainPresenter presenter;

    public MainAdapter(MainPresenter presenter, Query ref) {
        super(Wish.class, R.layout.item_list, WishViewHolder.class, ref);
        this.presenter = presenter;
    }

    @Override
    protected void populateViewHolder(WishViewHolder viewHolder, Wish model, final int position) {

        final DatabaseReference wishReference = getRef(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onItemClicked(wishReference);
            }
        });

        viewHolder.bindToWish(model);
    }
}
