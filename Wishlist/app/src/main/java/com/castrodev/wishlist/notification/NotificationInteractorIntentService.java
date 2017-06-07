package com.castrodev.wishlist.notification;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.castrodev.wishlist.api.ApiClient;
import com.castrodev.wishlist.api.ApiService;
import com.castrodev.wishlist.model.Offer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rodrigocastro on 07/06/17.
 */

public class NotificationInteractorIntentService extends IntentService {


    public NotificationInteractorIntentService() {
        super("NotificationInteractorIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ApiService apiService =
                ApiClient.getClient().create(ApiService.class);

        Call<Offer> call = apiService.fetchOffer();
        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, Response<Offer> response) {
                NotificationFactory.create(NotificationInteractorIntentService.this, response.body());
            }

            @Override
            public void onFailure(Call<Offer> call, Throwable t) {
                //TODO
            }
        });
    }

}
