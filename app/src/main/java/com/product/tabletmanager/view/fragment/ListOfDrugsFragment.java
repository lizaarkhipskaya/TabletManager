package com.product.tabletmanager.view.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.product.tabletmanager.AlarmReceiver;
import com.product.tabletmanager.R;
import com.product.tabletmanager.model.Drug;
import com.product.tabletmanager.model.DrugListAdapter;
import com.product.tabletmanager.viewmodel.AllDrugsViewModel;

import java.util.Calendar;
import java.util.List;

public class ListOfDrugsFragment extends Fragment implements DrugListAdapter.OnClickListener {
    private static final String LOG_TAG = ListOfDrugsFragment.class.getSimpleName();

    private DrugListAdapter drugListAdapter;
    private AllDrugsViewModel viewModel;
    private NavController mController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(AllDrugsViewModel.class);

        viewModel.getDrugsMediatorLiveDate().observe(this, list -> {
            drugListAdapter.setNewData(list);
            scheduleAlarm(list);
        });
    }

    private void scheduleAlarm(List<Drug> allDrugs) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Log.i(LOG_TAG, "scheduleAlarm: ");
        if (allDrugs != null) {
            for (Drug drug :
                    allDrugs) {
                if(drug.getDayTime()!= null)
                for (Calendar calendar :
                        drug.getDayTime()) {
                    Intent alarmIntent = new Intent(getContext(), AlarmReceiver.class);
                    alarmIntent.putExtra(AlarmReceiver.TITLE_KEY, drug.getName());
                    alarmIntent.putExtra(AlarmReceiver.CONTENT_KEY, drug.getForm());
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), allDrugs.indexOf(drug), alarmIntent,
                            0);

                    if (pendingIntent != null) {
                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                                calendar.getTimeInMillis(), 2 * 1000, pendingIntent);
                        Log.i(LOG_TAG, "scheduleAlarm: set repeating for time " + calendar.getTime().toString());
                    } else {
                        Log.e(LOG_TAG, "scheduleAlarm: pending intent is null");
                    }
                    /*                alarmManager.cancel(pendingIntent);*/
                }
            }
        } else {
            Log.d(LOG_TAG, "scheduleAlarm: empty drug list");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_of_drugs, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drugListAdapter = new DrugListAdapter(viewModel.getLiveData().getValue());
        drugListAdapter.setOnRemoveClickListener(this);
        drugListAdapter.setOnItemClickListener(((view1, drug) -> {
            Bundle args = new Bundle();
            args.putSerializable(DrugInfoFragment.KEY, drug);
            mController.navigate(R.id.drugInfoFragment, args);
        }));

        RecyclerView mDrugListRV = view.findViewById(R.id.list_of_drugs_rv_drugs);
        mDrugListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mDrugListRV.setAdapter(drugListAdapter);

        Button addNew = view.findViewById(R.id.list_of_drugs_btn_find_drug);
        addNew.setOnClickListener(
                v -> {
                    mController.navigate(R.id.findDrugFragment);
                }
        );
    }

    @Override
    public void onClick(View view, Drug drug) {
        viewModel.clearDrug(drug);
        Toast.makeText(getContext(), "Remove drug successfully", Toast.LENGTH_SHORT).show();
    }
}
