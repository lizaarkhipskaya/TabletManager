package com.product.tabletmanager.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.product.tabletmanager.model.Drug;
import com.product.tabletmanager.model.DrugRepository;
import com.product.tabletmanager.model.room.RoomRepository;
import com.product.tabletmanager.util.AlarmHelper;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DrugViewModel extends ViewModel {

    private MutableLiveData<Drug> mDrugLiveDate = new MutableLiveData<>();
    private MutableLiveData<Boolean> saveLiveData = new MutableLiveData<>();
    private Drug mDrug;
    private DrugRepository mRepository;

    public ObservableField<Drug.FORM> mForm = new ObservableField<>();
    public ObservableField<String> mName = new ObservableField<>();
    public String mUserName;
    public int mDosage;
    public Set<Calendar> mDayTime = new HashSet<>();
    public List<Drug.DEPENDS_ON_FOOD> mDependsOnFood;

    public DrugViewModel() {
        mRepository = RoomRepository.getInstance();
    }

    public LiveData<Drug> getDrugLiveDate() {
        return mDrugLiveDate;
    }

    public LiveData<Boolean> getSaveLiveData() {
        return saveLiveData;
    }

    private void updateDrug() {
        if (mName.get() != null) {
            mDrug = new Drug(mForm.get(), mName.get(), mUserName, null, null, mDosage, mDayTime);
            mDrugLiveDate.postValue(mDrug);
        }
    }

    public void selectForm(Drug.FORM form) {
        mForm.set(form);
        updateDrug();
    }

    public void selectName(String name) {
        mName.set(name);
        updateDrug();
    }

    public void setDayTime(Calendar calendar) {
        mDayTime.add(calendar);
        updateDrug();
    }

    public void saveDrug() {
        boolean canSave = canSaveDrug();
        if (!canSave) {
            saveLiveData.postValue(false);
            return;
        }
        mRepository.saveDrug(mDrug);
        saveLiveData.postValue(true);
    }

    boolean canSaveDrug() {
        return mForm.get() != null && mName.get() != null && !mName.get().isEmpty();
    }
}
