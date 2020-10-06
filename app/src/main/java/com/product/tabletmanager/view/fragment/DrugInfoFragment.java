package com.product.tabletmanager.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.product.tabletmanager.R;
import com.product.tabletmanager.model.Drug;
import com.product.tabletmanager.model.TimeInfoAdapter;
import com.product.tabletmanager.util.CommonUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DrugInfoFragment extends Fragment {

    static final String KEY = "DRUG";
    private Drug drug;
    private RecyclerView mTimeRV;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            drug = (Drug) args.getParcelable(KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drug_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((TextView) view.findViewById(R.id.drug_info_name)).setText(drug.getName());
        // ((ImageView) view.findViewById(R.id.drug_info_form)).setText(drug.getForm().name()); todo
        //  //((TextView) view.findViewById(R.id.drug_info_user_name)).setText(drug.getUserName());
        ((TextView) view.findViewById(R.id.drug_info_dosage)).setText(String.valueOf(drug.getDosage()));
        ((TextView) view.findViewById(R.id.drug_info_start_date)).setText(
                CommonUtils.getInstance().getDateString(drug.getStartDate()));
        ((TextView) view.findViewById(R.id.drug_info_due_date)).setText(
                CommonUtils.getInstance().getDateString(drug.getEndDate()));
        mTimeRV = view.findViewById(R.id.time_recycler_view);
        mTimeRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        setupAdapter();
    }

    private void setupAdapter() {
        List<Calendar> timeList = new ArrayList<>(drug.getDayTime());
        Collections.sort(timeList); //todo: add comparator
        mTimeRV.setAdapter(new TimeInfoAdapter(
                timeList.stream().map(
                        t -> CommonUtils.getInstance().getTimeString(t))
                        .collect(Collectors.toList())));
    }
}
