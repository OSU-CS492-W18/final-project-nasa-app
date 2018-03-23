package com.example.cs492.nasaphotos.PictureOfDay;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by aless on 3/23/2018.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static DatePickerFragment newInstance(DatePickerFragmentListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setDatePickerListener(listener);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    private DatePickerFragmentListener datePickerListener;
    public interface DatePickerFragmentListener {
        public void onDateSet(DatePicker view, int year, int month, int day);
    }

    public DatePickerFragmentListener getDatePickerListener() {
        return this.datePickerListener;
    }

    public void setDatePickerListener(DatePickerFragmentListener listener) {
        this.datePickerListener = listener;
    }

    protected void notifyDatePickerListener(DatePicker view, int year, int month, int day) {
        if(this.datePickerListener != null) {
            this.datePickerListener.onDateSet(view,year,month,day);
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        notifyDatePickerListener(view,year,month,day);
    }
}