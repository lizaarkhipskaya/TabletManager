package com.product.tabletmanager.util;

import com.product.tabletmanager.model.Drug;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public String getDateString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M\\d", Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public String getTimeString(Calendar date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return simpleDateFormat.format(date.getTime());
    }
}
