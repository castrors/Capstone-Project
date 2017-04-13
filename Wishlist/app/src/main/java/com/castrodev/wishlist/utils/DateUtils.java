package com.castrodev.wishlist.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by rodrigocastro on 13/04/17.
 */

public class DateUtils {
    public static String getDateSelectedWithFormat(int year, int month, int dayOfMonth, String format) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        Date date = new Date(calendar.getTimeInMillis());
        SimpleDateFormat simpleDate = new SimpleDateFormat(format, Locale.getDefault());

        return simpleDate.format(date);
    }
}
