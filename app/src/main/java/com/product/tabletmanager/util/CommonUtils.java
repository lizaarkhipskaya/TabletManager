package com.product.tabletmanager.util;

import com.product.tabletmanager.model.Drug;

import java.util.Calendar;

public class CommonUtils {
    private static CommonUtils mInstance;
    private CommonUtils() { }

    public static CommonUtils getInstance() {
        if (mInstance == null) {
            mInstance = new CommonUtils();
        }
        return mInstance;
    }

    /** Method is used to distinguish separate instance
     * of time for given drug.
     * @return unique number for Pair(Drug,Calendar)
     */
    public int getIdentifier(Drug drug, Calendar time) {
        return drug.getName().hashCode()+time.hashCode();
    }
}
