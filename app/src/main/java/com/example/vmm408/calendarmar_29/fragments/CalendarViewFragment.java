package com.example.vmm408.calendarmar_29.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.vmm408.calendarmar_29.R;

import butterknife.BindView;

public class CalendarViewFragment extends BaseFragment
        implements CalendarView.OnDateChangeListener {
    @BindView(R.id.calendar_view)
    CalendarView calendarView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarView.setOnDateChangeListener(this);
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view,
                                    int year, int month, int dayOfMonth) {
        ListItemFragment listItemFragment = new ListItemFragment();
        listItemFragment.setArguments(initBundle(year, month + 1, dayOfMonth));
        initNewFragment(listItemFragment, "list_f");
    }

    private Bundle initBundle(int year, int month, int dayOfMonth) {
        Bundle bundle = new Bundle();
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        bundle.putInt("dayOfMonth", dayOfMonth);
        return bundle;
    }
}
