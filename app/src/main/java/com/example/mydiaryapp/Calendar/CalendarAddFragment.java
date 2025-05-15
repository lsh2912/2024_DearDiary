package com.example.mydiaryapp.Calendar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mydiaryapp.R;

public class CalendarAddFragment extends Fragment
{
    private CalendarDatabaseHelper dbHelper;

    private static final String ARG_CALENDAR_ID = "calendar_id";
    private int currentCalendarId;

    private EditText calendarNameEditText, accountNameEditText;
    private Button addButton;
    private Button cancelButton;

    // 새로운 인스턴스를 생성하고, 캘린더 ID를 인수로 전달
    public static CalendarAddFragment newInstance(int calendarId) {
        CalendarAddFragment fragment = new CalendarAddFragment();
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

        View view = inflater.inflate(R.layout.fragment_add_calendar, container, false);

        // Database Helper 초기화
        dbHelper = new CalendarDatabaseHelper(requireContext());

        // UI 요소 참조
        calendarNameEditText = view.findViewById(R.id.calendarName);
        accountNameEditText = view.findViewById(R.id.accountNameEditText);
        addButton = view.findViewById(R.id.addButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        // "Add Calendar" 버튼 클릭 이벤트
        addButton.setOnClickListener(v -> {
            String calendarName = calendarNameEditText.getText().toString();
            String accountName = accountNameEditText.getText().toString();

            if (calendarName.isEmpty() || accountName.isEmpty()) {
                // 필드가 비어있으면 안내 메시지 표시
                Toast.makeText(requireContext(), "모든 필드를 입력하세요", Toast.LENGTH_SHORT).show();
            } else {
                // 캘린더 추가 또는 수정 로직
                if (currentCalendarId > 0) {
                    updateCalendar(calendarName, accountName);
                } else {
                    addNewCalendar(calendarName, accountName);
                }
            }
        });

        // 캘린더 데이터 불러오기
        if (currentCalendarId > 0) {
            loadCalendarData(currentCalendarId);
        }

        // "취소" 버튼 클릭 이벤트
        cancelButton.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
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
            }

            cursor.close(); // 커서 닫기
        } else {
            Toast.makeText(getContext(), "캘린더를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // 새 캘린더 추가
    private void addNewCalendar(String calendarName, String accountName) {
        // DB에 저장하기 위한 객체
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 데이터 유효성 검사
        if (TextUtils.isEmpty(calendarName) || TextUtils.isEmpty(accountName)
        ) {
            Toast.makeText(getContext(), "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 추가할 캘린더 데이터
        ContentValues values = new ContentValues();
        values.put(CalendarDatabaseHelper.COLUMN_NAME, calendarName);
        values.put(CalendarDatabaseHelper.COLUMN_ACCOUNT_NAME, accountName);

        // 데이터베이스에 캘린더 추가
        long rowId = db.insert(CalendarDatabaseHelper.TABLE_CALENDARS, null, values);

        if (rowId != -1) {
            Toast.makeText(getContext(), "캘린더가 추가되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "캘린더 추가 실패", Toast.LENGTH_SHORT).show();
        }

        db.close();

        // 이전 화면으로 돌아가기
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    // 기존 캘린더 수정
    private void updateCalendar(String calendarName, String accountName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 수정할 데이터
        ContentValues values = new ContentValues();
        values.put(CalendarDatabaseHelper.COLUMN_NAME, calendarName);
        values.put(CalendarDatabaseHelper.COLUMN_ACCOUNT_NAME, accountName);

        // 업데이트 조건 (ID 기준)
        String selection = CalendarDatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(currentCalendarId)};

        // 데이터베이스에서 해당 캘린더 업데이트
        int rowsUpdated = db.update(CalendarDatabaseHelper.TABLE_CALENDARS, values, selection, selectionArgs);

        if (rowsUpdated > 0) {
            Toast.makeText(getContext(), "캘린더가 수정되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "캘린더 수정 실패", Toast.LENGTH_SHORT).show();
        }

        db.close();

        // 이전 화면으로 돌아가기
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}