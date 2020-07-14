package com.product.tabletmanager.model;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DaysConverter {
    @TypeConverter
    public String fromDayTime(List<Drug.DAY_TIME> mDayTime) {
        if(mDayTime != null)
         return mDayTime.stream().map(Enum::toString)
                .collect( Collectors.joining( "," ) );
        return null;
    }

    @TypeConverter
    public List<Drug.DAY_TIME> toDayTime(String data) {
        if(data != null)
            return Arrays.stream(data.split(",")).map(Drug.DAY_TIME::valueOf)
                .collect(Collectors.toList());
        return null;
    }
}
