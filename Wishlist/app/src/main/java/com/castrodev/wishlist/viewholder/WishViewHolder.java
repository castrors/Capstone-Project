package com.castrodev.wishlist.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.castrodev.wishlist.R;
import com.castrodev.wishlist.model.Wish;
import com.squareup.picasso.Picasso;


/**
 * Created by rodrigocastro on 19/05/17.
 */

public class WishViewHolder extends RecyclerView.ViewHolder {

    public TextView tvWishName;
    public TextView tvWishDue;
    private ImageView ivWishPhoto;

    public WishViewHolder(View itemView) {
        super(itemView);

        tvWishName = (TextView) itemView.findViewById(R.id.tv_wish_name);
        tvWishDue = (TextView) itemView.findViewById(R.id.tv_wish_due);
        ivWishPhoto = (ImageView) itemView.findViewById(R.id.iv_wish_photo);
    }

    public void bindToWish(Wish wish) {
        tvWishName.setText(wish.getName());
        tvWishDue.setText(wish.getDueDate().toString());
        Context context = ivWishPhoto.getContext();

        Picasso.with(context)
                .load(wish.getPhotoUrl())
                .error(R.drawable.ic_photo_camera)
                .fit()
                .centerCrop()
                .into(ivWishPhoto);
    }
}

