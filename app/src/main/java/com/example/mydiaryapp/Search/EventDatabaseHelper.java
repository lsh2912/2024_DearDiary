/*
package com.example.mydiaryapp.Search;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDatabaseHelper extends SQLiteOpenHelper
{
    // 데이터베이스 이름과 버전
    private static final String DATABASE_NAME = "events_db";
    private static final int DATABASE_VERSION = 1;

    // 테이블 및 컬럼 이름
    public static final String TABLE_EVENTS = "EVENTS";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "EVENT_NAME";
    public static final String COLUMN_DATE = "EVENT_DATE";
    public static final String COLUMN_EXPAIN = "EVENT_EXPAIN";
    public static final String COLUMN_TAG = "EVENT_TAG";

    // SQL 쿼리 (테이블 생성)
    private static final String SQL_TABLE_CREATE_EVENTS =
            "CREATE TABLE " + TABLE_EVENTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_TAG + " TEXT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_EXPAIN + " TEXT" +
                    ");";

    // DELETE FROM EVENTS
    public static final String SQL_TABLE_DELETE_EVENTS = "DELETE FROM " + TABLE_EVENTS;
    // SELECT * FROM CALENDARS
    public static final String SQL_TABLE_SELECT_EVENTS = "SELECT * FROM " + TABLE_EVENTS;

    // DROP TABLE
    public static final String SQL_TABLE_DROP_EVENTS = "DROP TABLE IF EXISTS " + TABLE_EVENTS;

    public EventDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 데이터베이스가 처음 생성될 때 호출되는 메소드
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_TABLE_CREATE_EVENTS);
    }

    // 데이터베이스 버전이 변경될 때 호출되는 메소드
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 기존 테이블 삭제 후 새로 생성
        db.execSQL(SQL_TABLE_DROP_EVENTS);
        onCreate(db);
    }

    // 모든 일정 조회
    public void searchAllEvents(SQLiteDatabase database) {
        database.execSQL(SQL_TABLE_SELECT_EVENTS);
    }

    // 특정 일정 검색
    */
/*public Cursor searchEvents(SQLiteDatabase database, String query) {
        String searchQuery = "SELECT * FROM " + TABLE_EVENTS + " WHERE " +
                COLUMN_NAME + " LIKE '%" + query + "%' OR " +
                COLUMN_TAG + " LIKE '%" + query + "%'";
        return database.rawQuery(searchQuery, null);
    }*//*

    public Cursor searchEvents(SQLiteDatabase database, String query) {
        String selection = COLUMN_NAME + " LIKE ? OR " + COLUMN_TAG + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + query + "%", "%" + query + "%"};
        return database.query(TABLE_EVENTS, null, selection, selectionArgs, null, null, null);
    }

    // 이벤트 삭제
    public void deleteEvent(SQLiteDatabase database, int eventId) {
        database.delete(TABLE_EVENTS, COLUMN_ID + " = ?", new String[]{String.valueOf(eventId)});
    }
}
*/
