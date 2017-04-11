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

    public Wish(String name, Date dueDate, Integer priority, Location location, Double value, String observation) {
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
        this.location = location;
        this.value = value;
        this.observation = observation;
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
}
