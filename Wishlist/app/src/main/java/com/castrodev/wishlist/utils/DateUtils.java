package com.castrodev.wishlist.utils;

import java.text.ParseException;
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

    public static String getDateWithFormat(Date date, String format){
        SimpleDateFormat simpleDate = new SimpleDateFormat(format, Locale.getDefault());

        return simpleDate.format(date);
    }

    public static Date getDate(String when, String format) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return simpleDateFormat.parse(when);
        } catch (ParseException e) {
            return new Date(0);
        }
    }

    public static boolean isDatePast(Date date) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        return date.getTime() - currentDate.getTime() < 0;
    }
}
