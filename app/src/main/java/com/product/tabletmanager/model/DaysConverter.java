package com.product.tabletmanager.model;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class DaysConverter {
    @TypeConverter
    public String fromDayTime(List<Calendar> mDayTime) {
        if (mDayTime != null)
            return mDayTime.stream().map(Calendar::getTimeInMillis).map(Object::toString)
                    .collect(Collectors.joining(","));
        return null;
    }

    @TypeConverter
    public List<Calendar> toDayTime(String data) {
        if (data != null)
            return Arrays.stream(data.split(",")).map(s -> {
                        Calendar calendar = Calendar.getInstance();
                        return calendar;
                    }).collect(Collectors.toList());
        return null;
    }
}
