package com.product.tabletmanager.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.product.tabletmanager.model.room.DrugDatabase;

public class DrugApplication extends Application {

    public static DrugDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        database = Room.databaseBuilder(this, DrugDatabase.class, "drug_table")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                    }
                })
                .allowMainThreadQueries()
                .build();
    }


    public static DrugDatabase getDatabase() {
        return database;
    }
}