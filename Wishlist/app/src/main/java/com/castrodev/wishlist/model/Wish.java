package com.castrodev.wishlist.model;


import java.util.Date;

/**
 * Created by rodrigocastro on 07/04/17.
 */

public class Wish {

    private String name;
    private Date dueDate;
    private Integer priority;
    private Location location;
    private Double value;
    private String observation;
    private String photoUrl;
    private String photoPath;

    public Wish(){

    }

    public Wish(String name, Date dueDate, Integer priority, Location location, Double value, String observation, String photoUrl, String photoPath) {
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
        this.location = location;
        this.value = value;
        this.observation = observation;
        this.photoUrl = photoUrl;
        this.photoPath = photoPath;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
