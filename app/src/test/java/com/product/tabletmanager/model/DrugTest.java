package com.product.tabletmanager.model;

import android.os.Parcel;
import android.util.ArraySet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.sql.Date;
import java.util.Calendar;
import java.util.Set;

import static java.util.Calendar.getInstance;
import static org.junit.Assert.assertEquals;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml", packageName = "com.product.tabletmanager")
public class DrugTest {
    @Test
    public void testDrugParcelable() {
        Date startDate = new Date(System.currentTimeMillis());
        Date dueDate = new Date(System.currentTimeMillis());

        Calendar time1 = getInstance();
        time1.setTimeInMillis(System.currentTimeMillis() + 100);
        Calendar time2 = getInstance();
        Set<Calendar> times = new ArraySet<>();
        times.add(time1);
        times.add(time2);

        Drug drug = new Drug(Drug.FORM.CAPSULE,"name", "userName", startDate,
                dueDate,1, times);

        Parcel parcel = Parcel.obtain();
        drug.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        Drug drugFromParcel = Drug.CREATOR.createFromParcel(parcel);

        assertEquals(drug, drugFromParcel);
    }
}