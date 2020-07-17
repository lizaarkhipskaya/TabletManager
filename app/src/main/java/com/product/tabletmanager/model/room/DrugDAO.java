package com.product.tabletmanager.model.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.product.tabletmanager.model.Drug;

import java.util.List;

@Dao
public interface DrugDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Drug drug);

    @Delete
    void clearDrugs (Drug...drugs);

    @Query("SELECT * FROM drug_table")
    LiveData<List<Drug>> getAllDrugs();
}
