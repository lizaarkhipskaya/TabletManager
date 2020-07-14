package com.product.tabletmanager.model;

import androidx.room.TypeConverter;

public class FormConverter {
    @TypeConverter
    public String fromForm(Drug.FORM form) {
        return form.name();
    }

    @TypeConverter
    public Drug.FORM toForm(String data) {
        return Drug.FORM.valueOf(data);
    }
}
