package com.product.tabletmanager;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
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
        Log.d(TAG, "onCreate:");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(TAG, "onStartCommand: "+intent+flags);
        MediatorLiveData<List<Drug>> mediatorLiveData = new MediatorLiveData<>();
        mediatorLiveData.addSource(RoomRepository.getInstance().getAllDrugs(), mediatorLiveData::setValue);
        mediatorLiveData.observe(this, list ->
                AlarmHelper.getInstance().setAlarm(getApplicationContext(), list));
        stopSelf();
        return START_REDELIVER_INTENT;
    }
}
