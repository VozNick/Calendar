package com.example.vmm408.calendarmar_29.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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


        item_container_lv.setAdapter(new Adapter(getActivity(),
                getNoteModel(year, month + 1, dayOfMonth), dbHelper, year, month, dayOfMonth));
    }

    @Override
    public void onDestroyView() {
        dbHelper.close();
        super.onDestroyView();
    }

    private void getArgs() {
        try {
            year = getArguments().getInt("year");
            month = getArguments().getInt("month");
            dayOfMonth = getArguments().getInt("dayOfMonth");
        } catch (NullPointerException ignored) {
        }
    }

    private List<NoteModel> getNoteModel(int year, int month, int dayOfMonth) {
        List<NoteModel> noteModelList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("events", new String[]{"description", "is_notify", "event_time"},
                "day_of_month=?", new String[]{String.valueOf(dayOfMonth)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                NoteModel noteModel = new NoteModel();
                noteModel.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                noteModel.setNotify((cursor.getInt(cursor.getColumnIndex("is_notify")) == 1));
                noteModel.setTime(cursor.getFloat(cursor.getColumnIndex("event_time")));
                noteModelList.add(noteModel);
            } while (cursor.moveToNext());
        }
        return noteModelList;
    }

}
