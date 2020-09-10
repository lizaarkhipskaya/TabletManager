package com.product.tabletmanager.model;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DaysConverter {
    @TypeConverter
    public String fromDayTime(Set<Calendar> mDayTime) {
        if (mDayTime != null)
            return mDayTime.stream().map(Calendar::getTimeInMillis).map(Object::toString)
                    .collect(Collectors.joining(","));
        return null;
    }

    @TypeConverter
    public Set<Calendar> toDayTime(String data) {
        if (data != null)
            return Arrays.stream(data.split(",")).map(s -> {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis((Long.valueOf(s).longValue()));
                        return calendar;
                    }).collect(Collectors.toSet());
        return null;
    }
}
