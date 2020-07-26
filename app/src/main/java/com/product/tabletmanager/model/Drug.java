package com.product.tabletmanager.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity(tableName = "drug_table")
public class Drug implements Serializable {

    @TypeConverters({FormConverter.class})
    private FORM mForm;
    @PrimaryKey
    @NonNull
    private String mName;
    private String mUserName;
    private int mDosage;
    @TypeConverters(DateConverter.class)
    private Date mStartDate;
    @TypeConverters(DateConverter.class)
    private Date mEndDate;
    @TypeConverters({DaysConverter.class})
    private List<Calendar> mDayTime;
    @TypeConverters({DependsOnFoodConverter.class})
    private List<DEPENDS_ON_FOOD> mDependsOnFood;

    public Drug(FORM mForm, @NonNull String mName,
                String mUserName, Date startDate, Date endDate, int mDosage,
                List<Calendar> mDayTime,
                List<DEPENDS_ON_FOOD> mDependsOnFood) {
        this.mForm = mForm;
        this.mName = mName;
        this.mUserName = mUserName;
        mStartDate = startDate;
        mEndDate = endDate;
        this.mDosage = mDosage;
        if(mDayTime == null) {
            this.mDayTime = new ArrayList<>();
            this.mDayTime.add(Calendar.getInstance());
        }
        this.mDependsOnFood = mDependsOnFood;
    }

    public enum FORM {PILL, LIQUID, CAPSULE}

    public enum DAY_TIME {MORNING, NOON, EVENING}

    public enum DEPENDS_ON_FOOD {NO_DEPEND, BEFORE, IN_TIME, AFTER}

    public FORM getForm() {
        return mForm;
    }

    public Date getStartDate() {
        return mStartDate;
    }

    public Date getEndDate() {
        return mEndDate;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public String getUserName() {
        return mUserName;
    }

    public int getDosage() {
        return mDosage;
    }

    public List<Calendar> getDayTime() {
        return mDayTime;
    }

    public List<DEPENDS_ON_FOOD> getDependsOnFood() {
        return mDependsOnFood;
    }
}
