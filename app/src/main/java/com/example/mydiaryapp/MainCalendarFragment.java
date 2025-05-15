package com.example.mydiaryapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import com.example.mydiaryapp.R;

public class MainCalendarFragment extends Fragment {

    public MainCalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_month_calendar, container, false);

        // CalendarView 설정
        CalendarView calendarView = rootView.findViewById(R.id.yearlyCalendar);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // 날짜 선택 시 처리할 로직
        });

        return rootView;
    }
}
