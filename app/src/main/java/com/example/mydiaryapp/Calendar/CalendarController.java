package com.example.mydiaryapp.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CalendarController {
    private static SQLiteDatabase database;
    private static CalendarDatabaseHelper dbHelper;

    // 데이터베이스 초기화
    public static void initDatabase(Context context) {
        dbHelper = new CalendarDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // 모든 캘린더 가져오기
    public static List<Calendar> getAllCalendars() {
        List<Calendar> calendarList = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.query(CalendarDatabaseHelper.TABLE_CALENDARS,
                    new String[]{
                            CalendarDatabaseHelper.COLUMN_ID,
                            CalendarDatabaseHelper.COLUMN_NAME,
                            CalendarDatabaseHelper.COLUMN_ACCOUNT_NAME,
                    },
                    null, null, null, null, null);

            while (cursor != null && cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(CalendarDatabaseHelper.COLUMN_ID));
                String calendarName = cursor.getString(cursor.getColumnIndexOrThrow(CalendarDatabaseHelper.COLUMN_NAME));
                String accountName = cursor.getString(cursor.getColumnIndexOrThrow(CalendarDatabaseHelper.COLUMN_ACCOUNT_NAME));

                calendarList.add(new Calendar(id, calendarName, accountName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return calendarList;
    }

    // 캘린더 추가
    public static boolean addCalendar(String calendarName, String accountName, String color) {
        ContentValues values = new ContentValues();
        values.put(CalendarDatabaseHelper.COLUMN_NAME, calendarName);
        values.put(CalendarDatabaseHelper.COLUMN_ACCOUNT_NAME, accountName);
        try {
            database.insert(CalendarDatabaseHelper.TABLE_CALENDARS, null, values);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 캘린더 수정
    public static boolean modifyCalendar(int calendarId, String newCalendarName, String newColor) {
        ContentValues values = new ContentValues();
        values.put(CalendarDatabaseHelper.COLUMN_NAME, newCalendarName);

        try {
            int rowsUpdated = database.update(CalendarDatabaseHelper.TABLE_CALENDARS,
                    values,
                    CalendarDatabaseHelper.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(calendarId)});
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 캘린더 삭제
    public static boolean deleteCalendar(int calendarId) {
        try {
            int rowsDeleted = database.delete(CalendarDatabaseHelper.TABLE_CALENDARS,
                    CalendarDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(calendarId)});
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}