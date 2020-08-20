package com.product.tabletmanager.model.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.product.tabletmanager.model.Drug;

@Database(entities = Drug.class, version = 4, exportSchema = false)
public abstract class DrugDatabase extends RoomDatabase {
    public abstract DrugDAO drugDao();
}
