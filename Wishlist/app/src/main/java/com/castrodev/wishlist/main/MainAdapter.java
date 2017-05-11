package com.castrodev.wishlist.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.castrodev.wishlist.R;
import com.castrodev.wishlist.model.Wish;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rodrigocastro on 10/05/17.
 */

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private MainPresenter presenter;
    private List<Wish> wishes;


     MainAdapter(List<Wish> wishes, MainPresenter presenter) {
         this.wishes = wishes;
         this.presenter = presenter;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView = inflater.inflate(R.layout.item_list, parent, false);

        return new ViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wish wish = wishes.get(position);
        holder.name.setText(wish.getName());
    }

    @Override
    public int getItemCount() {
        return wishes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_wish_name)
        TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Wish wish = wishes.get(position);
            presenter.onItemClicked(wish);
        }
    }
}
