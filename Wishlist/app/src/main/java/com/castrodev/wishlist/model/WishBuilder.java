package com.castrodev.wishlist.model;

import java.util.Date;

public class WishBuilder {
    private String name;
    private Date dueDate;
    private Integer priority;
    private Location location;
    private Double value;
    private String observation;
    private String photoUrl;
    private String photoPath;


    public WishBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public WishBuilder withDueDate(Date dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public WishBuilder withPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public WishBuilder withLocation(Location location) {
        this.location = location;
        return this;
    }

    public WishBuilder withValue(Double value) {
        this.value = value;
        return this;
    }

    public WishBuilder withObservation(String observation) {
        this.observation = observation;
        return this;
    }

    public WishBuilder withPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public WishBuilder withPhotoPath(String photoPath) {
        this.photoPath = photoPath;
        return this;
    }

    public Wish createWish() {
        return new Wish(name, dueDate, priority, location, value, observation, photoUrl, photoPath);
    }
}