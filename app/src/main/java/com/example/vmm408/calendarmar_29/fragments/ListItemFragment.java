package com.example.vmm408.calendarmar_29.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vmm408.calendarmar_29.R;
import com.example.vmm408.calendarmar_29.adapter.Adapter;
import com.example.vmm408.calendarmar_29.dataBaseHelper.DBHelper;
import com.example.vmm408.calendarmar_29.models.NoteModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ListItemFragment extends BaseFragment {
    @BindView(R.id.item_container_lv)
    ListView item_container_lv;
    private List<NoteModel> noteModelList = new ArrayList<>();
    private DBHelper dbHelper;
    private Cursor cursor;
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
        getNoteModel(year, month, dayOfMonth);
        item_container_lv.setAdapter(new Adapter(getActivity(), noteModelList, dbHelper,
                year, month, dayOfMonth));
    }

    private void getArgs() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            year = bundle.getInt("year");
            month = bundle.getInt("month");
            dayOfMonth = bundle.getInt("dayOfMonth");
        }
    }

    private void getNoteModel(int year, int month, int dayOfMonth) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        cursor = sqLiteDatabase.query(
                "events",
                new String[]{"description", "is_notify", "event_time"},
                "year=? AND month=? AND day_of_month=?",
                new String[]{String.valueOf(year),
                        String.valueOf(month),
                        String.valueOf(dayOfMonth)},
                null, null, null);
        if (cursor.moveToFirst()) getFromCursor();
        cursor.close();
    }

    private void getFromCursor() {
        do {
            noteModelList.add(new NoteModel(
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getInt(cursor.getColumnIndex("event_time")),
                    cursor.getInt(cursor.getColumnIndex("is_notify")) == 1));
        } while (cursor.moveToNext());
    }

    @Override
    public void onDestroyView() {
        dbHelper.close();
        super.onDestroyView();
    }
}
