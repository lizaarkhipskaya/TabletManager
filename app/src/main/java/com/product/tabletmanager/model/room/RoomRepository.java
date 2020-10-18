package com.product.tabletmanager.model.room;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.product.tabletmanager.app.DrugApplication;
import com.product.tabletmanager.model.Drug;
import com.product.tabletmanager.model.DrugRepository;

import java.util.List;

public class RoomRepository implements DrugRepository {

    private final DrugDAO drugDao = DrugApplication.getDatabase().drugDao();
    private LiveData<List<Drug>> allDrugs = drugDao.getAllDrugs();
    private static RoomRepository instance;

    private RoomRepository() {

    }

    public static RoomRepository getInstance() {
        if (instance == null) {
            instance = new RoomRepository();
        }
        return instance;
    }

    @Override
    public void saveDrug(Drug drug) {
        new InsertAsyncTask(drugDao).execute(drug);
    }

    @Override
    public LiveData<List<Drug>> getAllDrugs() {
        return allDrugs;
    }

    @Override
    public void clearAllDrugs() {
        new DeleteAsyncTask(drugDao).execute(allDrugs.getValue().toArray(new Drug[0]));
    }

    @Override
    public void clearDrug(Drug drug) {
        new DeleteAsyncTask(drugDao).execute(drug);
    }

    private static class InsertAsyncTask extends AsyncTask<Drug, Void, Void> {
        private DrugDAO mDrugDao;

        InsertAsyncTask(DrugDAO drugDAO) {
            super();
            mDrugDao = drugDAO;
        }

        @Override
        protected Void doInBackground(Drug... drugs) {
            mDrugDao.insert(drugs[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Drug, Void, Void> {
        private DrugDAO mDrugDao;

        DeleteAsyncTask(DrugDAO drugDAO) {
            super();
            mDrugDao = drugDAO;
        }

        @Override
        protected Void doInBackground(Drug... drugs) {
            mDrugDao.clearDrugs(drugs);
            return null;
        }
    }
}
