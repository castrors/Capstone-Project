package com.castrodev.wishlist.widget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.castrodev.wishlist.R;
import com.castrodev.wishlist.detail.DetailActivity;
import com.castrodev.wishlist.main.MainInteractor;
import com.castrodev.wishlist.main.MainInteractorImpl;
import com.castrodev.wishlist.model.Wish;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.castrodev.wishlist.main.MainActivity.WISH_KEY;
import static com.castrodev.wishlist.main.MainActivity.WISH_OBJECT;

/**
 * RemoteViewsService controlling the data being shown in the scrollable weather detail widget
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DetailWidgetRemoteViewsService extends RemoteViewsService {
    public final String LOG_TAG = DetailWidgetRemoteViewsService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private List<Wish> data = null;
            private List<DataSnapshot> dataSnapshotList = null;

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {

                MainInteractorImpl mainInteractor = new MainInteractorImpl();
                mainInteractor.fetchWishes(
                        new MainInteractor.OnFinishedListener() {
                            @Override
                            public void onSuccess(Query itemsQuery) {
                                itemsQuery.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        data = new ArrayList<Wish>();
                                        dataSnapshotList = new ArrayList<DataSnapshot>();
                                        for (DataSnapshot wishSnapshot : dataSnapshot.getChildren()) {
                                            Wish wish = wishSnapshot.getValue(Wish.class);
                                            dataSnapshotList.add(wishSnapshot);
                                            data.add(wish);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        data = new ArrayList<Wish>();
                                        dataSnapshotList = new ArrayList<DataSnapshot>();
                                    }
                                });

                            }

                            @Override
                            public void onError() {
                                data = new ArrayList<Wish>();
                                dataSnapshotList = new ArrayList<DataSnapshot>();
                            }
                        }
                );
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.size();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_detail_list_item);

                Wish wish = data.get(position);
                DataSnapshot snapshot = dataSnapshotList.get(position);
                Bitmap bitmap = null;
                try {
                    bitmap = Picasso.with(DetailWidgetRemoteViewsService.this)
                            .load(wish.getPhotoUrl())
                            .error(R.drawable.ic_photo_camera)
                            .get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (bitmap != null) {
                    views.setImageViewBitmap(R.id.widget_icon, bitmap);
                } else {
                    views.setImageViewResource(R.id.widget_icon, R.drawable.ic_photo_camera);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                    setRemoteContentDescription(views, wish.getName());
                }
                views.setTextViewText(R.id.widget_name, wish.getName());
                views.setTextViewText(R.id.widget_description, wish.getDueDate().toString());

                final Intent fillInIntent = new Intent(DetailWidgetRemoteViewsService.this, DetailActivity.class);
                fillInIntent.putExtra(WISH_OBJECT, wish);
                fillInIntent.putExtra(WISH_KEY, snapshot.getKey());
                views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);
                return views;
            }

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            private void setRemoteContentDescription(RemoteViews views, String description) {
                views.setContentDescription(R.id.widget_icon, description);
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_detail_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
