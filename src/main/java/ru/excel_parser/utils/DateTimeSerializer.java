package ru.excel_parser.utils;

import com.fasterxml.jackson.databind.ser.std.DateSerializer;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * Created by Dmitrii on 11.03.2016.
 */
public final class DateTimeSerializer extends DateSerializer {

    public static final String DATE_TIME_FORMAT = "dd-M-yyyy hh:mm:ss";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);


    public DateTimeSerializer() {
        super(false, new SimpleDateFormat(DATE_TIME_FORMAT));
    }
}
