package com.example.vmm408.calendarmar_29.adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;

public class Adapter extends BaseAdapter implements View.OnClickListener {
    private List<NoteModel> noteModelList = new ArrayList<>();
    private DBHelper dbHelper;
    private Context context;
    private LayoutInflater layoutInflater;

    private int year;
    private int month;
    private int dayOfMonth;
    private AlertDialog alertDialog;


    public Adapter(Context context, List<NoteModel> noteModelList, DBHelper dbHelper, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;

        this.dbHelper = dbHelper;
        this.noteModelList = noteModelList;
        this.context = context;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (noteModelList.size() == 0)
            for (int i = 0; i < 24; i++) {
                noteModelList.add(new NoteModel("note" + String.valueOf(i), i, false));
            }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
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
//        alert(position);
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.style_item, parent, false);
        ((TextView) convertView.findViewById(R.id.time_tv)).setText(String.valueOf(noteModelList.get(position).getTime()));
        ((TextView) convertView.findViewById(R.id.note_tv)).setText(noteModelList.get(position).getDescription());

        convertView.findViewById(R.id.notification_iv).setVisibility((noteModelList.get(position).isNotify() ? View.VISIBLE : View.GONE));
        convertView.setOnClickListener(this);
        return convertView;
    }
    @Override
    public void onClick(View v) {
//        System.out.println(position);
        View alertView = LayoutInflater.from(context).inflate(R.layout.style_new_alert, null);
        final EditText description = ButterKnife.findById(alertView, R.id.note_et);
        final CheckBox isNotify = ButterKnife.findById(alertView, R.id.is_notify_cb);

        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(context)
                    .setView(alertView)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!TextUtils.isEmpty(description.getText())) {

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, month, dayOfMonth);

                                ContentValues contentValues = new ContentValues();

                                contentValues.put("description", description.getText().toString());
                                contentValues.put("is_notify", (isNotify.isChecked()));

//                                contentValues.put("event_time", position);

                                contentValues.put("year", calendar.get(Calendar.YEAR));
                                contentValues.put("month", calendar.get(Calendar.MONTH));
                                contentValues.put("day_of_month", calendar.get(Calendar.DAY_OF_MONTH));

                                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
                                sqLiteDatabase.insert("events", null, contentValues);
//                                noteModelList.add(new NoteModel(description.getText().toString(), position, (isNotify.isChecked())));
                                notifyDataSetChanged();
                            }
                            dialog.dismiss();

                            new ListItemFragment();
                        }
                    }).create();
            alertDialog.show();
        }
    }

}
