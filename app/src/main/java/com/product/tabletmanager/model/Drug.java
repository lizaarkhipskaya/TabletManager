package com.product.tabletmanager.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

@Entity(tableName = "drug_table")
public class Drug implements Parcelable {

    public static Creator<Drug> CREATOR = new Creator<Drug>() {

        @Override
        public Drug createFromParcel(Parcel source) {
            return new Drug(source);
        }

        @Override
        public Drug[] newArray(int size) {
            return new Drug[size];
        }

    };

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

    public Drug(Parcel in) {
        mForm = FORM.valueOf(in.readString());
        mName = in.readString();
        mUserName = in.readString();
        mDosage = in.readInt();
        mStartDate = new Date(in.readLong());
        mEndDate = new Date(in.readLong());
        mDayTime = new ArraySet<>();
/*        IntStream.range(0, in.readInt()).forEach(
                i -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(in.readLong());
                    mDayTime.add(calendar);
                });*/
    }

    public void setDependsOnFood(List<DEPENDS_ON_FOOD> mDependsOnFood) {
        this.mDependsOnFood = mDependsOnFood;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mForm.toString());
        dest.writeString(this.mName);
        dest.writeString(this.mUserName);
        dest.writeInt(this.mDosage);
        dest.writeLong(this.mStartDate.getTime());
        dest.writeLong(this.mEndDate.getTime());/*
        dest.writeInt(mDayTime.size() - 1);
        mDayTime.forEach(calendar ->
                dest.writeLong(calendar.getTimeInMillis()));*/
    }

    @Override
    public String toString() {
        return "Drug{" +
                "mForm=" + mForm +
                ", mName='" + mName + '\'' +
                ", mUserName='" + mUserName + '\'' +
                ", mDosage=" + mDosage +
                ", mStartDate=" + mStartDate.getTime() +
                ", mEndDate=" + mEndDate.getTime() +
                ", mDependsOnFood=" + mDependsOnFood +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drug)) return false;
        Drug drug = (Drug) o;
        return mDosage == drug.mDosage &&
                mForm == drug.mForm &&
                mName.equals(drug.mName) &&
                Objects.equals(mUserName, drug.mUserName) &&
                Objects.equals(mStartDate, drug.mStartDate) &&
                Objects.equals(mEndDate, drug.mEndDate) &&
                Objects.equals(mDayTime, drug.mDayTime) &&
                Objects.equals(mDependsOnFood, drug.mDependsOnFood);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mForm, mName, mUserName, mDosage, mStartDate, mEndDate, mDayTime, mDependsOnFood);
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
