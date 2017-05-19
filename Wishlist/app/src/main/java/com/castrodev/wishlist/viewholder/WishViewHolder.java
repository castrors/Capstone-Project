package com.castrodev.wishlist.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.castrodev.wishlist.R;
import com.castrodev.wishlist.model.Wish;

/**
 * Created by rodrigocastro on 19/05/17.
 */

public class WishViewHolder extends RecyclerView.ViewHolder {

    public TextView tvWishName;
    public TextView tvWishDue;

    public WishViewHolder(View itemView) {
        super(itemView);

        tvWishName = (TextView) itemView.findViewById(R.id.tv_wish_name);
        tvWishDue = (TextView) itemView.findViewById(R.id.tv_wish_due);
    }

    public void bindToWish(Wish wish) {
        tvWishName.setText(wish.getName());
        tvWishDue.setText(wish.getDueDate().toString());
    }
}

