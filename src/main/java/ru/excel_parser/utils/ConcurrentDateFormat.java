package ru.excel_parser.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dmitrii on 11.03.2016.
 * Thread safe DateFormat
 * code from (https://www.javacodegeeks.com/2010/07/java-best-practices-dateformat-in.html)
 *
 */
public class ConcurrentDateFormat {

    private final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {

        @Override
        public DateFormat get() {
            return super.get();
        }

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DateTimeSerializer.DATE_TIME_FORMAT);
        }

        @Override
        public void remove() {
            super.remove();
        }

        @Override
        public void set(DateFormat value) {
            super.set(value);
        }

    };

    private ConcurrentDateFormat() {
    }

    public static ConcurrentDateFormat getInstance() {
        return Holder.INSTANCE;
    }

    public final Date convertStringToDate(String dateString) throws ParseException {
        return df.get().parse(dateString);
    }

    private static class Holder {
        private final static ConcurrentDateFormat INSTANCE = new ConcurrentDateFormat();
    }
}
