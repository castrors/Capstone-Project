package com.castrodev.wishlist.widget;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RemoteViews;

import com.castrodev.wishlist.R;
import com.castrodev.wishlist.main.MainActivity;
import com.castrodev.wishlist.main.MainInteractor;
import com.castrodev.wishlist.main.MainInteractorImpl;
import com.castrodev.wishlist.model.MonthResume;
import com.castrodev.wishlist.model.Wish;
import com.castrodev.wishlist.utils.DateUtils;
import com.castrodev.wishlist.utils.WishUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by rodrigocastro on 01/06/17.
 */
public class CurrentMonthWidgetIntentService extends IntentService {

    public CurrentMonthWidgetIntentService() {
        super("CurrentMonthWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        providesMonthResume();
    }

    private void setDataToLayout(MonthResume monthResume) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                CurrentMonthWidgetProvider.class));

        for (int appWidgetId : appWidgetIds) {
            int widgetWidth = getWidgetWidth(appWidgetManager, appWidgetId);
            int defaultWidth = getResources().getDimensionPixelSize(R.dimen.widget_current_month_default_width);
            int largeWidth = getResources().getDimensionPixelSize(R.dimen.widget_current_month_large_width);
            int layoutId;
            if (widgetWidth >= largeWidth) {
                layoutId = R.layout.widget_current_month_large;
            } else if (widgetWidth >= defaultWidth) {
                layoutId = R.layout.widget_current_month;
            } else {
                layoutId = R.layout.widget_current_month_small;
            }
            RemoteViews views = new RemoteViews(getPackageName(), layoutId);

            views.setImageViewResource(R.id.widget_icon, R.drawable.ic_photo_camera);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                setRemoteContentDescription(views, monthResume.getMonth());
            }
            views.setTextViewText(R.id.widget_current_month, monthResume.getMonth());
            views.setTextViewText(R.id.widget_wishes_count, String.format(getResources().getString(R.string.widget_current_month_count), monthResume.getCount()));
            views.setTextViewText(R.id.widget_wishes_value, WishUtils.getMonetaryValue(monthResume.getTotalValue()));

            Intent launchIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private void providesMonthResume() {

        MainInteractorImpl mainInteractor = new MainInteractorImpl();
        mainInteractor.fetchWishes(new MainInteractor.OnFinishedListener() {
            @Override
            public void onSuccess(Query itemsQuery) {
                itemsQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        setDataToLayout(getMonthResume(dataSnapshot));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        setDataToLayout(new MonthResume(DateUtils.getCurrentMonthName(), 0, 0.0));
                    }
                });
            }

            @Override
            public void onError() {
                setDataToLayout(new MonthResume(DateUtils.getCurrentMonthName(), 0, 0.0));
            }
        });
    }

    @NonNull
    private MonthResume getMonthResume(DataSnapshot dataSnapshot) {
        MonthResume monthResume = new MonthResume(DateUtils.getCurrentMonthName(), 0, 0.0);
        for (DataSnapshot wishSnapshot : dataSnapshot.getChildren()) {
            Wish wish = wishSnapshot.getValue(Wish.class);
            Log.d("Wish", wish.toString());
            if (DateUtils.isInThisMonth(wish.getDueDate())) {
                monthResume.setCount(monthResume.getCount() + 1);
                monthResume.setTotalValue(monthResume.getTotalValue() + wish.getValue());
            }
        }
        return monthResume;
    }

    private int getWidgetWidth(AppWidgetManager appWidgetManager, int appWidgetId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return getResources().getDimensionPixelSize(R.dimen.widget_current_month_default_width);
        }
        return getWidgetWidthFromOptions(appWidgetManager, appWidgetId);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private int getWidgetWidthFromOptions(AppWidgetManager appWidgetManager, int appWidgetId) {
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        if (options.containsKey(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)) {
            int minWidthDp = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, minWidthDp,
                    displayMetrics);
        }
        return getResources().getDimensionPixelSize(R.dimen.widget_current_month_default_width);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void setRemoteContentDescription(RemoteViews views, String description) {
        views.setContentDescription(R.id.widget_icon, description);
    }

}
