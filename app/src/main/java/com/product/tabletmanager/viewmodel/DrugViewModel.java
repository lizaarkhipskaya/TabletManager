package com.product.tabletmanager.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.product.tabletmanager.model.Drug;
import com.product.tabletmanager.model.DrugRepository;
import com.product.tabletmanager.model.room.RoomRepository;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class DrugViewModel extends ViewModel {

    private MutableLiveData<Drug> mDrugLiveDate = new MutableLiveData<>();
    private MutableLiveData<Boolean> saveLiveData = new MutableLiveData<>();
    private Drug mDrug;
    private DrugRepository mRepository;

    private ObservableField<Drug.FORM> mForm = new ObservableField<>();
    private ObservableField<String> mName = new ObservableField<>();
    private String mUserName;
    private int mDosage;
    private Set<Calendar> mDayTime = new HashSet<>();
    private ObservableField<Calendar> mStartDate = new ObservableField<>();
    private ObservableField<Calendar> mDueDate = new ObservableField<>();

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
            Date startDate = null;
            Date dueDate = null;
            if (mStartDate.get() != null)
                startDate = new Date(mStartDate.get().getTimeInMillis());
            if (mDueDate.get() != null)
                dueDate = new Date(mDueDate.get().getTimeInMillis());
            mDrug = new Drug(mForm.get(), mName.get(), mUserName, startDate,
                    dueDate, mDosage, mDayTime);
            mDrugLiveDate.postValue(mDrug);
        }
    }

    public ObservableField<Calendar> getStartDateLiveData() {
        return mStartDate;
    }

    public ObservableField<Calendar> getDueDateLiveData() {
        return mDueDate;
    }

    public void selectForm(Drug.FORM form) {
        mForm.set(form);
        updateDrug();
    }

    public void selectName(String name) {
        mName.set(name);
        updateDrug();
    }

    public void selectStartDate(Calendar startDate) {
        mStartDate.set(startDate);
        for (Calendar time : mDayTime) {
            setStartDateForTime(time);
        }
        updateDrug();
    }

    private void setStartDateForTime(Calendar time) {
        if (mStartDate.get() != null) {
            Calendar startDate = mStartDate.get();
            time.set(Calendar.DAY_OF_MONTH, startDate.get(Calendar.DAY_OF_MONTH));
            time.set(Calendar.MONTH, startDate.get(Calendar.MONTH));
            time.set(Calendar.YEAR, startDate.get(Calendar.YEAR));
        }
    }

    public void selectDueDate(Calendar dueDate) {
        mDueDate.set(dueDate);
        updateDrug();
    }

    public void setDayTime(Calendar calendar) {
        setStartDateForTime(calendar);
        mDayTime.add(calendar);
        updateDrug();
    }

    public void selectDosage(int dosage) {
        mDosage = dosage;
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

    public String getDateString(ObservableField<Calendar> date) {
        Calendar dateCalendar = date.get();
        if (dateCalendar == null) {
            if (date.equals(mStartDate))
                return "Start date";
            return "Due date";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M\\d", Locale.getDefault());
        return simpleDateFormat.format(dateCalendar.getTime());
    }
}
