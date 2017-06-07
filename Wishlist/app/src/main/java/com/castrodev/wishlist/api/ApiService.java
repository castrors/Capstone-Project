package com.castrodev.wishlist.api;

import com.castrodev.wishlist.model.Offer;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by rodrigocastro on 07/06/17.
 */

public interface ApiService {

    @GET("/castrors/Capstone-Project/master/api.json")
    Call<Offer> fetchOffer();

}
