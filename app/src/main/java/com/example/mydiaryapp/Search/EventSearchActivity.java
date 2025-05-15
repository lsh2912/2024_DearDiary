package com.example.mydiaryapp.Search;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.mydiaryapp.Calendar.CalendarDatabaseHelper;
import com.example.mydiaryapp.Event.Event;
import com.example.mydiaryapp.R;

import java.util.ArrayList;
import java.util.List;

public class EventSearchActivity extends AppCompatActivity {

    /*private EventDatabaseHelper eventDbHelper;
    private CalendarDatabaseHelper calendarDbHelper;
    private SQLiteDatabase eventDatabase;
    private SQLiteDatabase calendarDatabase;*/
    private EditText searchInput;
    private ImageView searchIcon;
    private ImageView calendarSearchIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_search);

        // DB 헬퍼 초기화
        /*eventDbHelper = new EventDatabaseHelper(this);
        eventDatabase = eventDbHelper.getWritableDatabase();

        calendarDbHelper = new CalendarDatabaseHelper(this);
        calendarDatabase = calendarDbHelper.getWritableDatabase();
        */

        searchInput = findViewById(R.id.searchInput);
        searchIcon = findViewById(R.id.searchIcon);
        calendarSearchIcon = findViewById(R.id.calendarSearchIcon);

        // 검색 버튼 클릭 이벤트
        /*searchIcon.setOnClickListener(v -> {
            String query = searchInput.getText().toString().trim();
            if (query.isEmpty()) {
                Toast.makeText(this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            searchInput.setText(""); // 검색창 초기화
            Toast.makeText(this, "\"" + query + "\"로 검색을 실행합니다.", Toast.LENGTH_SHORT).show();
            showSearchResults(query); // 텍스트박스에 입력된 값으로 검색
        });*/

        // 캘린더 검색 아이콘 클릭 이벤트
        /*calendarSearchIcon.setOnClickListener(v -> {
            // 캘린더 목록을 가져와서 다이얼로그에 표시
            Cursor cursor = null;
            try {
                cursor = calendarDbHelper.getAllCalendars(calendarDatabase); // 모든 캘린더 가져오기
                if (cursor != null && cursor.moveToFirst()) {
                    List<String> calendarNames = new ArrayList<>();
                    // 캘린더 이름을 리스트에 추가
                    do {
                        String calendarName = cursor.getString(cursor.getColumnIndexOrThrow(CalendarDatabaseHelper.COLUMN_NAME));
                        calendarNames.add(calendarName);
                    } while (cursor.moveToNext());

                    // 캘린더 목록을 보여주는 다이얼로그 생성
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("캘린더 선택")
                            .setItems(calendarNames.toArray(new String[0]), (dialog, which) -> {
                                String selectedCalendar = calendarNames.get(which); // 사용자가 선택한 캘린더 이름
                                Toast.makeText(this, "\"" + selectedCalendar + "\"로 검색을 실행합니다.", Toast.LENGTH_SHORT).show();
                                showSearchResultsByCalendar(selectedCalendar); // 선택된 캘린더로 검색 실행
                            })
                            .setNegativeButton("취소", null)
                            .show();
                } else {
                    Toast.makeText(this, "검색할 캘린더가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Toast.makeText(this, "캘린더 목록을 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        });*/

    }

    // 이벤트 검색 (이벤트 이름 및 태그로 검색)
    /*private void showSearchResults(String query) {
        List<Event> events = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = eventDbHelper.searchEvents(eventDatabase, query);
            while (cursor != null && cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(EventDatabaseHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(EventDatabaseHelper.COLUMN_NAME));
                String tag = cursor.getString(cursor.getColumnIndexOrThrow(EventDatabaseHelper.COLUMN_TAG));
                // 필요한 정보 추가
                events.add(new Event(id, title, null, null));
            }

            if (!events.isEmpty()) {
                EventSearchBottomSheetFragment bottomSheet = EventSearchBottomSheetFragment.newInstance(events);
                bottomSheet.show(getSupportFragmentManager(), "SearchResults");
            } else {
                Toast.makeText(this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "검색 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }*/
    // 캘린더 태그로 이벤트 검색
   /* private void showSearchResultsByCalendar(String calendarQuery) {
        List<Event> events = new ArrayList<>();
        Cursor cursor = null;
        Cursor eventCursor = null;

        try {
            // 캘린더 이름으로 검색
            cursor = calendarDbHelper.getCalendarByName(calendarDatabase, calendarQuery);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String calendarName = cursor.getString(cursor.getColumnIndexOrThrow(CalendarDatabaseHelper.COLUMN_NAME));
                    // 캘린더 이름에 해당하는 이벤트 검색
                    eventCursor = eventDbHelper.searchEvents(eventDatabase, calendarName);

                    if (eventCursor != null && eventCursor.moveToFirst()) {
                        do {
                            int id = eventCursor.getInt(eventCursor.getColumnIndexOrThrow(EventDatabaseHelper.COLUMN_ID));
                            String title = eventCursor.getString(eventCursor.getColumnIndexOrThrow(EventDatabaseHelper.COLUMN_NAME));
                            events.add(new Event(id, title, null, null)); // 예시로 null 처리
                        } while (eventCursor.moveToNext());
                    }
                } while (cursor.moveToNext());

                if (!events.isEmpty()) {
                    // 검색 결과를 BottomSheetFragment로 표시
                    EventSearchBottomSheetFragment bottomSheet = EventSearchBottomSheetFragment.newInstance(events);
                    bottomSheet.show(getSupportFragmentManager(), "SearchResults");
                } else {
                    Toast.makeText(this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "캘린더를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "검색 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        } finally {
            // 리소스 해제
            if (cursor != null) {
                cursor.close();
            }
            if (eventCursor != null) {
                eventCursor.close();
            }
        }
    }*/

}