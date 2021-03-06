package net.almitchellmobile.eggwise20.util;

import java.util.Date;

import androidx.room.TypeConverter;

/**
 * Created by Pavneet_Singh on 12/31/17.
 */

public class DateRoomConverter {

    /*@TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();
    }*/

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
