package com.product.tabletmanager.model;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface DrugRepository {
    void saveDrug(Drug drug);

    LiveData<List<Drug>> getAllDrugs();

    void clearAllDrugs();

    void clearDrug(Drug drug);
}
