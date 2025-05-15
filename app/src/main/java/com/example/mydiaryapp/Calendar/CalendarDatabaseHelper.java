package com.example.mydiaryapp.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalendarDatabaseHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1 ;
    public static final String DATABASE_NAME = "calendar_db" ; // 데이터베이스 이름 선언만 해두면 알아서 헬퍼가 관리함

    public static final String TABLE_CALENDARS = "CALENDARS";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_ACCOUNT_NAME = "ACCOUNT_NAME";

    // CREATE TABLE IF NOT EXISTS CALENDARS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT NOT NULL, ACCOUNT_NAME TEXT NOT NULL)
    public static final String SQL_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_CALENDARS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_ACCOUNT_NAME + " TEXT NOT NULL )";

    // DROP TABLE IF EXISTS CALENDARS
    public static final String SQL_TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_CALENDARS;

    // SELECT * FROM CALENDARS
    public static final String SQL_TABLE_SELECT = "SELECT * FROM " + TABLE_CALENDARS;

    // INSERT OR REPLACE INTO CALENDARS (ID, NAME, ACCOUNT_NAME, COLOR) VALUES (x, x, x)
    public static final String SQL_TABLE_INSERT =
            "INSERT OR REPLACE INTO " +
                    TABLE_CALENDARS + " (" +
                    COLUMN_ID + ", " +
                    COLUMN_NAME + ", " +
                    COLUMN_ACCOUNT_NAME + ") VALUES (?, ?, ?)";

    // DELETE FROM CALENDARS
    public static final String SQL_TABLE_DELETE = "DELETE FROM " + TABLE_CALENDARS;

    public CalendarDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 사용 중인 DB가 없을 때 호출
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_TABLE_CREATE) ;
    }

    // 사용 중인 코드의 버전이 바뀐 경우 호출
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_TABLE_DELETE);
        onCreate(sqLiteDatabase) ;
    }

    // 캘린더 이름으로 검색하는 메서드
    public Cursor getCalendarByName(SQLiteDatabase sqLiteDatabase, String calendarName) {
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + TABLE_CALENDARS + " WHERE " + COLUMN_NAME + " LIKE ?";
            cursor = sqLiteDatabase.rawQuery(query, new String[]{"%" + calendarName + "%"});
        } catch (SQLException e) {
            e.printStackTrace();  // 예외 처리 로직
        }
        return cursor;
    }
    // 모든 캘린더를 가져오는 메서드
    public Cursor getAllCalendars(SQLiteDatabase sqLiteDatabase) {
        // SELECT * FROM CALENDARS
        return sqLiteDatabase.rawQuery(SQL_TABLE_SELECT, null);
    }


}
