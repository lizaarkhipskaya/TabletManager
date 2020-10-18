package com.product.tabletmanager;

import android.util.Log;

import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.MediatorLiveData;

import com.product.tabletmanager.model.Drug;
import com.product.tabletmanager.model.room.RoomRepository;
import com.product.tabletmanager.util.AlarmHelper;

import java.util.List;

public class AlarmScheduleService extends LifecycleService {
    private static final String TAG = AlarmScheduleService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        MediatorLiveData<List<Drug>> mediatorLiveData = new MediatorLiveData<>();
        mediatorLiveData.addSource(RoomRepository.getInstance().getAllDrugs(), mediatorLiveData::setValue);
        mediatorLiveData.observe(this, list ->
                AlarmHelper.getInstance().setAlarm(getApplicationContext(), list));
        stopSelf();
    }
}
