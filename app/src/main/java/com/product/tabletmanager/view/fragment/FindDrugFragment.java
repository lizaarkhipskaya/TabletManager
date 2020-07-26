package com.product.tabletmanager.view.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import java.util.Calendar;
import java.util.Date;

public class FindDrugFragment extends Fragment {
    private static final String LOG_TAG = FindDrugFragment.class.getSimpleName();

    private DrugViewModel mDrugViewModel;
    private LinearLayout mTimeRootView;
    private Date mChosenTime;

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
                Log.d(LOG_TAG, "drug saved successfully" + saved.toString());
                Toast.makeText(getContext(), "Drug saved successfully", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack();
            } else {
                Log.d(LOG_TAG, "error is save" + saved.toString());
                Toast.makeText(getContext(), "Error is save", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showTimePickerDialog(View v) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(),
                (timePicker, selectedHour, selectedMinute) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(SystemClock.currentThreadTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
            calendar.set(Calendar.MINUTE, selectedMinute);

            mChosenTime = Calendar.getInstance().getTime();
            Log.d(LOG_TAG, "showTimePickerDialog: chosen time " + mChosenTime);
            mDrugViewModel.setDayTime(Calendar.getInstance());
                    updateTimeView();
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void updateTimeView() {
        Log.d(LOG_TAG, "add new time view " + mChosenTime);
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(mChosenTime.toString());
        mTimeRootView.addView(textView, Math.max(0, mTimeRootView.getChildCount() - 2));
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
                            mDrugViewModel.selectForm(Drug.FORM.LIQUID);
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

        view.findViewById(R.id.drug_add_time).setOnClickListener(this::showTimePickerDialog);
        mTimeRootView = view.findViewById(R.id.drug_time_container);
    }
}

