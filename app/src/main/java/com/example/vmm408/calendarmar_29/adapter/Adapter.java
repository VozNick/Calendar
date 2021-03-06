package com.example.vmm408.calendarmar_29.adapter;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vmm408.calendarmar_29.R;
import com.example.vmm408.calendarmar_29.dataBaseHelper.DBHelper;
import com.example.vmm408.calendarmar_29.fragments.ListItemFragment;
import com.example.vmm408.calendarmar_29.models.NoteModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

public class Adapter extends BaseAdapter {
    private Context context;
    private DBHelper dbHelper;
    private List<NoteModel> noteModelList;
    private int year;
    private int month;
    private int dayOfMonth;
    private LayoutInflater layoutInflater;
    private EditText descriptionAlert;
    private CheckBox isNotifyAlert;
    private Calendar calendar;
    private int positionOnClick;

    public Adapter(Context context, DBHelper dbHelper, List<NoteModel> noteModelList,
                   int year, int month, int dayOfMonth) {
        this.context = context;
        this.dbHelper = dbHelper;
        this.noteModelList = noteModelList;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        fillList();
    }

    private void fillList() {
        if (noteModelList.size() == 0) {
            for (int i = 0; i < 24; i++) {
                noteModelList.add(new NoteModel("note" + String.valueOf(i), i, false));
            }
        }
    }

    @Override
    public int getCount() {
        return noteModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.style_item, parent, false);
        setToWidgets(convertView, position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positionOnClick = position;
                createAlert();
            }
        });
        return convertView;
    }

    private void setToWidgets(View convertView, int position) {
        ((TextView) convertView.findViewById(R.id.time_tv))
                .setText(String.valueOf(noteModelList.get(position).getTime()));
        ((TextView) convertView.findViewById(R.id.note_tv))
                .setText(noteModelList.get(position).getDescription());
        convertView.findViewById(R.id.notification_iv)
                .setVisibility((noteModelList.get(position)
                        .isNotify() ? View.VISIBLE : View.GONE));
    }

    private void createAlert() {
        new AlertDialog.Builder(context)
                .setView(alertView())
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        positiveAlertClick();
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private View alertView() {
        View view = LayoutInflater.from(context).inflate(R.layout.style_new_alert, null);
        descriptionAlert = ButterKnife.findById(view, R.id.note_et);
        isNotifyAlert = ButterKnife.findById(view, R.id.is_notify_cb);
        return view;
    }

    private void positiveAlertClick() {
        if (!TextUtils.isEmpty(descriptionAlert.getText())) {
            calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            addToBaseAndUpdateList();
            initAlarmManager();
        }
    }

    private void addToBaseAndUpdateList() {
        dbHelper.addData(descriptionAlert, isNotifyAlert, positionOnClick, calendar);
        noteModelList.add(new NoteModel(descriptionAlert.getText().toString(),
                positionOnClick, (isNotifyAlert.isChecked())));
        notifyDataSetChanged();
    }

    private void initAlarmManager() {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.add(Calendar.MINUTE, 1);
        alarmManager.set(AlarmManager.RTC_WAKEUP, 0, initPendingIntent());
    }

    private PendingIntent initPendingIntent() {
        return PendingIntent.getBroadcast(context, 101, initIntent(), PendingIntent.FLAG_ONE_SHOT);
    }

    private Intent initIntent() {
        return new Intent("wakeUp").putExtra("description", descriptionAlert.getText().toString());
    }

}
