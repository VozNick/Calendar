package com.example.vmm408.calendarmar_29.dataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.vmm408.calendarmar_29.models.NoteModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Calendar";
    private final String TABLE_NAME = "events";
    private final String COLUMN_ID = "id";
    private final String COLUMN_DESCRIPTION = "description";
    private final String COLUMN_IS_NOTIFY = "is_notify";
    private final String COLUMN_YEAR = "year";
    private final String COLUMN_MONTH = "month";
    private final String COLUMN_DAY_OF_MONTH = "day_of_month";
    private final String COLUMN_EVENT_TIME = "event_time";
    private List<NoteModel> noteModelList = new ArrayList<>();

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_IS_NOTIFY + " INTEGER, "
                + COLUMN_YEAR + " INTEGER, "
                + COLUMN_MONTH + " INTEGER, "
                + COLUMN_DAY_OF_MONTH + " INTEGER, "
                + COLUMN_EVENT_TIME + " FLOAT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addData(EditText description, CheckBox isNotify, int position, Calendar calendar) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DESCRIPTION, description.getText().toString());
        contentValues.put(COLUMN_IS_NOTIFY, (isNotify.isChecked()));
        contentValues.put(COLUMN_EVENT_TIME, position);
        contentValues.put(COLUMN_YEAR, calendar.get(Calendar.YEAR));
        contentValues.put(COLUMN_MONTH, calendar.get(Calendar.MONTH));
        contentValues.put(COLUMN_DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        this.getWritableDatabase().insert(TABLE_NAME, null, contentValues);
    }

    public List<NoteModel> getData(int year, int month, int dayOfMonth) {
        Cursor cursor = this.getReadableDatabase().query(
                TABLE_NAME,
                new String[]{COLUMN_DESCRIPTION, COLUMN_IS_NOTIFY, COLUMN_EVENT_TIME},
                COLUMN_YEAR + "=? AND " + COLUMN_MONTH + "=? AND " + COLUMN_DAY_OF_MONTH + "=?",
                new String[]{String.valueOf(year),
                        String.valueOf(month),
                        String.valueOf(dayOfMonth)},
                null, null, null);
        if (cursor.moveToFirst()) getFromCursor(cursor);
        cursor.close();
        return noteModelList;
    }

    private void getFromCursor(Cursor cursor) {
        do {
            noteModelList.add(new NoteModel(
                    cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_EVENT_TIME)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_IS_NOTIFY)) == 1));
        } while (cursor.moveToNext());
    }
}
