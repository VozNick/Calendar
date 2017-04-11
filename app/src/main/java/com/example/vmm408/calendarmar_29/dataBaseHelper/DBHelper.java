package com.example.vmm408.calendarmar_29.dataBaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "calendar", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table events ("
                + "id integer primary key autoincrement,"
                + "description text,"
                + "is_notify integer,"
                + "year integer,"
                + "month integer,"
                + "day_of_month integer,"
                + "event_time float);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
