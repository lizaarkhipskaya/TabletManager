package com.product.tabletmanager.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.product.tabletmanager.R;
import com.product.tabletmanager.model.Drug;

public class DrugInfoFragment extends Fragment {

    static final String KEY = "DRUG";
    private Drug drug;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            drug = (Drug) args.getSerializable(KEY);
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
        ((TextView) view.findViewById(R.id.drug_info_form)).setText(drug.getForm().name());
        ((TextView) view.findViewById(R.id.drug_info_user_name)).setText(drug.getUserName());
        ((TextView) view.findViewById(R.id.drug_info_dosage)).setText(String.valueOf(drug.getDosage()));
    }
}
