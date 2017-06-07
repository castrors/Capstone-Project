
package com.castrodev.wishlist.model;

import com.google.gson.annotations.SerializedName;

public class Offer {

    @SerializedName("description")
    private String mDescription;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("url")
    private String mUrl;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
