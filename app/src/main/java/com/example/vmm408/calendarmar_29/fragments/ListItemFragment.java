package com.example.vmm408.calendarmar_29.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vmm408.calendarmar_29.R;
import com.example.vmm408.calendarmar_29.adapter.Adapter;
import com.example.vmm408.calendarmar_29.dataBaseHelper.DBHelper;

import butterknife.BindView;

public class ListItemFragment extends BaseFragment {
    @BindView(R.id.item_container_lv)
    ListView itemContainerLv;
    private DBHelper dbHelper;
    private int year;
    private int month;
    private int dayOfMonth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DBHelper(getContext());
        getArgs();
        itemContainerLv.setAdapter(new Adapter(getActivity(),
                dbHelper, dbHelper.getData(year, month, dayOfMonth), year, month, dayOfMonth));
    }

    @Override
    public void onDestroyView() {
        dbHelper.close();
        super.onDestroyView();
    }

    private void getArgs() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            year = bundle.getInt("year");
            month = bundle.getInt("month");
            dayOfMonth = bundle.getInt("dayOfMonth");
        }
    }
}
