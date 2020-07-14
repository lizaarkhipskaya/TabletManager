package com.product.tabletmanager.view.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.product.tabletmanager.R;
import com.product.tabletmanager.model.Drug;
import com.product.tabletmanager.viewmodel.DrugViewModel;

public class FindDrugFragment extends Fragment {

    private DrugViewModel mDrugViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrugViewModel = ViewModelProviders.of(this).get(DrugViewModel.class);
        configureViewModelObservers();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.product.tabletmanager.databinding.FragmentFindDrugBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_find_drug, container, false);
        binding.setViewmodel(mDrugViewModel);
        return binding.getRoot();
    }

    private void configureViewModelObservers() {
        mDrugViewModel.getSaveLiveData().observe(this, saved -> {
            if (saved) {
                Toast.makeText(getContext(), "Drug saved successfully", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack();
            } else {
                Toast.makeText(getContext(), "Error is save", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RadioGroup radioGroup = view.findViewById(R.id.drug_form);
        radioGroup.setOnCheckedChangeListener(
                (group, checkedId) -> {
                    switch (checkedId) {
                        case R.id.form_pill:
                            mDrugViewModel.selectForm(Drug.FORM.PILL);
                            break;
                        case R.id.form_mixture:
                            mDrugViewModel.selectForm(Drug.FORM.MIXTURE);
                            break;
                        case R.id.form_capsule:
                            mDrugViewModel.selectForm(Drug.FORM.CAPSULE);
                            break;

                        default:
                            break;
                    }
                }
        );

        EditText text = view.findViewById(R.id.drug_name);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDrugViewModel.selectName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                mDrugViewModel.selectName(s.toString());
            }
        });
    }
}
