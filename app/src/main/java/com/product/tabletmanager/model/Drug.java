package com.product.tabletmanager.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Calendar> mDayTime;
    @TypeConverters({DependsOnFoodConverter.class})
    private List<DEPENDS_ON_FOOD> mDependsOnFood;

    public Drug(FORM form, @NonNull String name,
                String userName, Date startDate, Date endDate, int dosage,
                Set<Calendar> dayTime) {
        mForm = form;
        mName = name;
        mUserName = userName;
        mStartDate = startDate;
        mEndDate = endDate;
        mDosage = dosage;
        mDayTime = dayTime;
    }

    public void setDependsOnFood(List<DEPENDS_ON_FOOD> mDependsOnFood) {
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

    public Set<Calendar> getDayTime() {
        return mDayTime;
    }

    public List<DEPENDS_ON_FOOD> getDependsOnFood() {
        return mDependsOnFood;
    }
}
