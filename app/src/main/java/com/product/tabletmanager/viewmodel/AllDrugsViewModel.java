package com.product.tabletmanager.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.product.tabletmanager.model.Drug;
import com.product.tabletmanager.model.room.RoomRepository;

import java.util.ArrayList;
import java.util.List;

public class AllDrugsViewModel extends ViewModel {
    private static final String TAG = AllDrugsViewModel.class.getSimpleName();
    private LiveData<List<Drug>> mDrugsLiveDate;
    private MediatorLiveData<List<Drug>> mDrugMediatorLiveData;
    private List<Drug> mDrugs;
    private RoomRepository repository;
    private Drug mDrugForClear;

    public AllDrugsViewModel() {
        repository = RoomRepository.getInstance();
        mDrugsLiveDate = repository.getAllDrugs();
        mDrugMediatorLiveData = new MediatorLiveData<>();
        mDrugMediatorLiveData.addSource(mDrugsLiveDate, list -> {
            mDrugMediatorLiveData.setValue(list);
        });

        mDrugs = new ArrayList<>();
    }

    public MediatorLiveData<List<Drug>> getDrugsMediatorLiveDate() {
        return mDrugMediatorLiveData;
    }

    private void updateDrugList() {
        mDrugMediatorLiveData.postValue(mDrugs);
    }

    public void addNewDrug(Drug drug) {
        mDrugs.add(drug);
        updateDrugList();
    }

    public void clearAllDrugs() {
        repository.clearAllDrugs();
    }

    public void clearDrug(Drug drug) {
        repository.clearDrug(drug);
    }

    public void clearDrug() {
        if (mDrugForClear == null) {
            Log.d(TAG, "clearDrug(), mDrugForClear == null");
        }
        repository.clearDrug(mDrugForClear);
    }

    public Drug getDrugForClear() {
        return mDrugForClear;
    }

    public void rememberDrugForClear(Drug drug) {
        mDrugForClear = drug;
    }

    public LiveData<List<Drug>> getLiveData() {
        return mDrugsLiveDate;
    }
}
