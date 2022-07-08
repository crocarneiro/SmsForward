package br.com.smsforward.data;

import androidx.room.TypeConverter;
import java.time.LocalDateTime;

public class TimestampConverter {
    @TypeConverter
    public static LocalDateTime fromTimestamp(String value) {
        if (value != null) {
            return LocalDateTime.parse(value);
        } else {
            return null;
        }
    }

    @TypeConverter
    public static String toTimestamp(LocalDateTime dateTime) {
        if(dateTime != null) {
            return dateTime.toString();
        } else {
            return null;
        }
    }
}