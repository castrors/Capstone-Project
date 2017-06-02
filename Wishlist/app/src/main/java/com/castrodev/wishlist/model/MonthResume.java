package com.castrodev.wishlist.model;

/**
 * Created by rodrigocastro on 01/06/17.
 */

public class MonthResume {

    private String month;
    private int count;
    private Double totalValue;

    public MonthResume(String month, int count, Double totalValue) {
        this.month = month;
        this.count = count;
        this.totalValue = totalValue;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }
}
