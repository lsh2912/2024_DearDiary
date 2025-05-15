package com.example.mydiaryapp.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydiaryapp.R;

import java.util.List;

public class CalendarFragment extends Fragment {
    private List<Calendar> calendarList;

    private RecyclerView calendarRecyclerView;
    private CalendarAdapter calendarAdapter;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        // RecyclerView 설정
        calendarRecyclerView = rootView.findViewById(R.id.calendarRecyclerView);
        calendarRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadCalendars();

        return rootView;
    }
    // 캘린더 목록을 데이터베이스에서 불러오는 메서드
    private void loadCalendars() {
        // 데이터베이스에서 캘린더 목록을 가져오기
        calendarList = CalendarController.getAllCalendars();

        // 어댑터를 생성하여 RecyclerView에 설정
        calendarAdapter = new CalendarAdapter(calendarList);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    // 캘린더 목록을 데이터베이스에서 불러오는 메서드
//    private void loadCalendars() {
//        // 데이터베이스에서 캘린더 목록을 가져오기
//        calendarList = CalendarController.getAllCalendars();
//
//        // 어댑터를 생성하여 ListView에 설정
//        calendarAdapter = new CalendarAdapter(calendarList);
//        calendarRecyclerView.setAdapter(calendarAdapter);
//
//        // 항목 클릭 리스너
//        calendarRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Calendar selectedCalendar = calendarList.get(position);
//                Toast.makeText(getActivity(), "Selected Calendar: " + selectedCalendar.getCalendarName(), Toast.LENGTH_SHORT).show();
//
//                // 수정 아이콘 클릭 시 수정 프래그먼트로 이동
//                ImageView editIcon = view.findViewById(R.id.editIcon);
//
//                // 이미지 클릭 이벤트를 처리하는 클릭 리스너를 설정
//                editIcon.setOnClickListener(v -> {
//                    CalendarEditFragment editCalendarFragment = CalendarEditFragment.newInstance(selectedCalendar.getId());
//                    getActivity().getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.fragment_container, editCalendarFragment)
//                            .addToBackStack(null)
//                            .commit();
//                });
//            }
//        });
//    }
}