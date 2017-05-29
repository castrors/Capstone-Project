package com.castrodev.wishlist.utils;

import com.castrodev.wishlist.model.Location;
import com.castrodev.wishlist.view.PriorityPickerFragment;

import java.util.Date;

/**
 * Created by rodrigocastro on 13/04/17.
 */

public class WishUtils {

    public static boolean isValueValid(Double value) {
        return value != null && value > 0;
    }

    public static boolean isLocationValid(Location location) {
        return location != null && !isEmpty(location.getName());
    }

    public static boolean isPriorityValid(Integer priority) {
        String[] priorityArray = PriorityPickerFragment.priorityArray;
        return priority != null && priority != -1 && priorityArray[priority] != null;
    }

    public static boolean isValidDate(Date date) {
        return date != null && !DateUtils.isDatePast(date);
    }

    public static boolean isEmpty(String text) {
        return text != null && text.equals("");
    }

    public static Double getValue(String howMuch) {
        return isEmpty(howMuch) ? 0 : Double.parseDouble(howMuch);
    }

    public static Integer getPriority(String why) {
        if(WishUtils.isEmpty(why)) {
            return -1;
        }
        String[] priorityArray = PriorityPickerFragment.priorityArray;
        for (int i = 0; i < priorityArray.length; i++) {
            String priority = priorityArray[i];
            if (priority.equals(why)) {
                return i;
            }
        }

        return -1;
    }

    public static String getPriority(int position){
        String[] priorityArray = PriorityPickerFragment.priorityArray;

        return priorityArray[position];
    }
}
