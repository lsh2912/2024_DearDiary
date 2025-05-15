package com.example.mydiaryapp.Calendar;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

//import com.example.mydiaryapp.Search.EventDatabaseHelper;
import com.example.mydiaryapp.R;

public class CalendarEditFragment extends Fragment
{
    private static final String ARG_CALENDAR_ID = "calendar_id";
    private int currentCalendarId;


    private EditText calendarNameEditText, accountNameEditText;
    private TextView eventCountText;
    private Button saveButton, deleteButton;

    private CalendarDatabaseHelper dbHelper;
    //private EventDatabaseHelper eventDatabaseHelper;

    // 새로운 인스턴스를 생성하고, 캘린더 ID를 인수로 전달
    public static CalendarEditFragment newInstance(int calendarId) {
        CalendarEditFragment fragment = new CalendarEditFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CALENDAR_ID, calendarId);
        fragment.setArguments(args);
        return fragment;
    }

    // 기존 데이터 로드, 프래그먼트가 생성될 때 호출
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentCalendarId = getArguments().getInt(ARG_CALENDAR_ID);
        }
    }

    // 프래그먼트의 뷰를 생성할 때 호출
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_edit_calendar, container, false);

        calendarNameEditText = rootView.findViewById(R.id.calendarName);
        accountNameEditText = rootView.findViewById(R.id.accountNameEditText);
        //eventCountText = rootView.findViewById(R.id.eventCount);
        saveButton = rootView.findViewById(R.id.saveButton);
        deleteButton = rootView.findViewById(R.id.deleteButton);

        // db 초기화
        dbHelper = new CalendarDatabaseHelper(getContext());

       /* // 이벤트 개수를 클릭하면 이벤트 화면으로 이동하는 기능
        eventCountText.setOnClickListener(v -> {
            // 이벤트 개수 화면으로 이동하는 코드 추가 (현재는 Toast 메시지만 출력)
            Toast.makeText(getContext(), "Event count feature not implemented yet", Toast.LENGTH_SHORT).show();
        });*/

        // 저장 버튼 클릭 리스너
        saveButton.setOnClickListener(v -> saveCalendarData());

        // 삭제 버튼 클릭 리스너
        deleteButton.setOnClickListener(v -> deleteCalendarData());

        // 캘린더 데이터 불러오기
        loadCalendarData(currentCalendarId);

        rootView.findViewById(R.id.saveButton).setOnClickListener(v -> saveCalendarData());

        return rootView;
    }

    // 캘린더 데이터를 데이터베이스에서 불러오는 메서드
    private void loadCalendarData(int calendarId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // 읽기 전용 데이터베이스 객체 생성

        String[] columns = {CalendarDatabaseHelper.COLUMN_NAME, CalendarDatabaseHelper.COLUMN_ACCOUNT_NAME}; // 가져올 컬럼들
        String selection = CalendarDatabaseHelper.COLUMN_ID + " = ?"; // 조건 (캘린더 ID)
        String[] selectionArgs = {String.valueOf(calendarId)}; // 선택 인수

        // 쿼리 실행
        Cursor cursor = db.query(CalendarDatabaseHelper.TABLE_CALENDARS, columns, selection, selectionArgs, null, null, null);

        // 쿼리 결과가 있으면 데이터를 가져옴
        if (cursor != null && cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(CalendarDatabaseHelper.COLUMN_NAME);
            int accountNameColumnIndex = cursor.getColumnIndex(CalendarDatabaseHelper.COLUMN_ACCOUNT_NAME);

            // 컬럼 인덱스가 유효한지 확인
            if (nameColumnIndex != -1 && accountNameColumnIndex != -1) {
                String name = cursor.getString(nameColumnIndex);
                String accountName = cursor.getString(accountNameColumnIndex);

                // EditText에 데이터 설정
                calendarNameEditText.setText(name);
                accountNameEditText.setText(accountName);

                // 이벤트 개수 업데이트
                /*int eventCount = getEventCount(calendarId);
                eventCountText.setText("Event Count: " + eventCount);*/
            } else {
                Toast.makeText(getContext(), "Failed to load calendar data.", Toast.LENGTH_SHORT).show();
            }

            cursor.close(); // 커서 닫기
        } else {
            Toast.makeText(getContext(), "Calendar not found.", Toast.LENGTH_SHORT).show();
        }
    }

    // 특정 캘린더에 대한 이벤트 개수를 반환하는 메서드
    /*private int getEventCount(int calendarId) {
        SQLiteDatabase db = eventDatabaseHelper.getReadableDatabase(); // 읽기 전용 데이터베이스 객체 생성
        String countQuery = "SELECT COUNT(*) FROM " + EventDatabaseHelper.TABLE_EVENTS + " WHERE " + CalendarDatabaseHelper.COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(countQuery, new String[]{String.valueOf(calendarId)});

        try {
            if (cursor.moveToFirst()) {
                return cursor.getInt(0); // 첫 번째 컬럼 값이 이벤트 개수
            }
        } catch (SQLiteException e) {
            Log.e("CalendarEditFragment", "Database error: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return 0; // 이벤트 개수 반환
    }*/

    // 캘린더 수정된 정보를 저장하는 메서드
    private void saveCalendarData() {
        String updatedCalendarName = calendarNameEditText.getText().toString().trim();
        String updatedAccountName = accountNameEditText.getText().toString().trim();

        // 필수 입력 값이 비어있으면 Toast 메시지 출력
        if (TextUtils.isEmpty(updatedCalendarName)) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 수정할 캘린더 데이터
        ContentValues values = new ContentValues();
        values.put(CalendarDatabaseHelper.COLUMN_NAME, updatedCalendarName);
        values.put(CalendarDatabaseHelper.COLUMN_ACCOUNT_NAME, updatedAccountName);

        // 업데이트할 캘린더 조건 (ID 기준)
        String selection = CalendarDatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(currentCalendarId)};

        // 데이터베이스에서 해당 캘린더 업데이트
        try {
            int rowsUpdated = db.update(CalendarDatabaseHelper.TABLE_CALENDARS, values, selection, selectionArgs);
            if (rowsUpdated > 0) {
                Toast.makeText(getContext(), "캘린더가 수정되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "수정 실패", Toast.LENGTH_SHORT).show();
            }
        } finally {
            db.close();
        }
        // 이전 화면으로 돌아가기
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    // 캘린더 데이터를 삭제하는 메서드
    private void deleteCalendarData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 삭제할 캘린더 조건 (ID 기준)
        String selection = CalendarDatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(currentCalendarId)};

        try {
            // 데이터베이스에서 캘린더 삭제
            int rowsDeleted = db.delete(CalendarDatabaseHelper.TABLE_CALENDARS, selection, selectionArgs);
            // 결과에 따른 Toast 메시지 출력
            if (rowsDeleted > 0) {
                Toast.makeText(getContext(), "Calendar deleted successfully", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();  // 삭제 후 이전 화면으로 돌아가기
                // requireActivity().getSupportFragmentManager().popBackStack(); // 이전 화면으로 돌아감

            } else {
                Toast.makeText(getContext(), "Error deleting calendar", Toast.LENGTH_SHORT).show();
            }
        } finally {
            db.close();  // 작업 후 반드시 close() 호출
        }
    }
}
