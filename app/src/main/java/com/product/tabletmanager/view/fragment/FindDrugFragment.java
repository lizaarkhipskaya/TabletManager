package com.product.tabletmanager.view.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.product.tabletmanager.databinding.FragmentFindDrugBinding;
import com.product.tabletmanager.model.Drug;
import com.product.tabletmanager.util.AlarmHelper;
import com.product.tabletmanager.viewmodel.DrugViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class FindDrugFragment extends Fragment implements TimeClickDialogFragment.OnPositiveButtonClick {
    private static final String LOG_TAG = FindDrugFragment.class.getSimpleName();

    private DrugViewModel mDrugViewModel;
    private LinearLayout mTimeRootView;
    private Date mChosenTime;

    private View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrugViewModel = ViewModelProviders.of(this).get(DrugViewModel.class);
        configureViewModelObservers();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentFindDrugBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_find_drug, container, false);
        binding.setViewmodel(mDrugViewModel);
        return binding.getRoot();
    }

    private void configureViewModelObservers() {
        mDrugViewModel.getSaveLiveData().observe(this, saved -> {
            if (saved) {
                Log.d(LOG_TAG, "drug saved successfully" + saved.toString());
                AlarmHelper.setAlarm(getContext(), mDrugViewModel.getDrugLiveDate().getValue());
                Toast.makeText(getContext(), "Drug saved successfully", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).popBackStack();
            } else {
                Log.d(LOG_TAG, "error in save" + saved.toString());
                Toast.makeText(getContext(), "Error in save", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showTimePickerDialog(View v) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(),
                (timePicker, selectedHour, selectedMinute) -> {
                    Calendar selectedTime = Calendar.getInstance();
                    selectedTime.setTimeInMillis(SystemClock.currentThreadTimeMillis());
                    selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                    selectedTime.set(Calendar.MINUTE, selectedMinute);

                    mChosenTime = selectedTime.getTime();
                    Log.d(LOG_TAG, "showTimePickerDialog: chosen time " + mChosenTime);
                    mDrugViewModel.setDayTime(selectedTime);
                    updateTimeView();
                }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private String getTimeStringByDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    private void updateTimeView() {
        Log.d(LOG_TAG, "add new time view " + mChosenTime);
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(getTimeStringByDate(mChosenTime));
        textView.setOnClickListener(v -> {
            TimeClickDialogFragment fragment = TimeClickDialogFragment.newInstance(
                    textView, this);
            fragment.show(getChildFragmentManager(), TimeClickDialogFragment.class.getSimpleName());
        });
        mTimeRootView.addView(textView, getTimeChildViewIndex(mTimeRootView));
    }

    private int getTimeChildViewIndex(ViewGroup parentView) {
        return Math.max(0, parentView.getChildCount() - 1);
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
                    hideKeyboard();
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

        ((EditText) view.findViewById(R.id.drug_dosage)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDrugViewModel.selectDosage(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                mDrugViewModel.selectDosage(Integer.parseInt(s.toString()));
            }
        });


        view.findViewById(R.id.drug_add_time).setOnClickListener(this::showTimePickerDialog);
        view.findViewById(R.id.drug_start_date).setOnClickListener(v ->
                showDatePickerDialog(R.string.date_picker_dialog_title_start_day));
        view.findViewById(R.id.drug_due_date).setOnClickListener(v ->
                showDatePickerDialog(R.string.date_picker_dialog_title_due_day));
        mTimeRootView = view.findViewById(R.id.drug_time_container);
        mRootView = view.findViewById(R.id.root_view);
        mRootView.setOnClickListener(v -> hideKeyboard());
    }

    private void showDatePickerDialog(int titleId) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog mDatePicker = new DatePickerDialog(getContext(), (view1, year, month, dayOfMonth) -> {
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year, month, dayOfMonth);
            if (titleId == R.string.date_picker_dialog_title_start_day) {
                mDrugViewModel.selectStartDate(selectedDate);
            } else {
                mDrugViewModel.selectDueDate(selectedDate);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        mDatePicker.setTitle(titleId);
        mDatePicker.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootView.setOnClickListener(null);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public void onClick(View childView) {
        mTimeRootView.removeView(childView);
    }
}

