package com.product.tabletmanager.model;

/**
 * This class is used to pass {@link Drug} into {@link android.os.Bundle}
 * when there is no need to put {@link Drug} with all fields.
 */
@Deprecated
public class DrugBundleDao {
    private String mName;
    private String mUserName;
    private String mForm;
    private int mDosage;
    private long mStartDate;
    private long mDueDate;
    private long mTime;

    public DrugBundleDao(Drug drug, long time) {
        this.mName = drug.getName();
        this.mUserName = drug.getUserName();
        this.mForm = drug.getForm().toString();
        this.mDosage = drug.getDosage();
        this.mStartDate = drug.getStartDate().getTime();
        this.mDueDate = drug.getEndDate().getTime();
        this.mTime = time;
    }
}
