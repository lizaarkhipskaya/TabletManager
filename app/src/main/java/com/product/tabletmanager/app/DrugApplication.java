package com.product.tabletmanager.app;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.product.tabletmanager.AlarmReceiver;
import com.product.tabletmanager.model.Drug;
import com.product.tabletmanager.model.room.DrugDatabase;
import com.product.tabletmanager.model.room.RoomRepository;

import java.util.Calendar;
import java.util.List;

public class DrugApplication extends Application {

    public static DrugDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        database = Room.databaseBuilder(this, DrugDatabase.class, "drug_table")
                .addMigrations(mMigration_2_3)
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

    private Migration mMigration_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE drug_database ADD COLUMN mStartDate INTEGER DEFAULT 0 NOT NULL");
            database.execSQL("ALTER TABLE drug_database ADD COLUMN mEndDate INTEGER DEFAULT 0 NOT NULL");
        }
    };
}