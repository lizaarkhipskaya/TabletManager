package com.product.tabletmanager.model;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DependsOnFoodConverter {
    @TypeConverter
    public String fromDependsOnFood(List<Drug.DEPENDS_ON_FOOD> mDependsOnFood) {
        if (mDependsOnFood!= null)
            return mDependsOnFood.stream().map(Enum::toString)
                .collect( Collectors.joining( "," ) );
        return null;
    }

    @TypeConverter
    public List<Drug.DEPENDS_ON_FOOD> toDependsOnFood(String data) {
        if (data!= null)
            return Arrays.stream(data.split(",")).map(Drug.DEPENDS_ON_FOOD::valueOf)
                .collect(Collectors.toList());
        return null;
    }
}