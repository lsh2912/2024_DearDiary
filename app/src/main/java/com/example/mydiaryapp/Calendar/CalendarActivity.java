package com.example.mydiaryapp.Calendar;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mydiaryapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CalendarActivity extends AppCompatActivity
{
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // 네비게이션 컨트롤러 설정
        //NavController navController = Navigation.findNavController(this, R.id.fragment_container_calendar);

        // NavHostFragment와 NavController 연결
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_calendar);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            // BottomNavigationView와 NavController 연결
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_calendar); // 하단 네비게이션 뷰
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }

        // Toolbar 설정
        Toolbar toolbar = findViewById(R.id.top_toolbar);
        setSupportActionBar(toolbar);

        // 캘린더 추가 버튼 설정
        Button addCalendarButton = findViewById(R.id.AddCalendarButton);
        addCalendarButton.setOnClickListener(v -> {
            // 캘린더 추가 프래그먼트로 이동
            navController.navigate(R.id.action_to_addCalendar);
        });

        // 캘린더 데이터베이스 초기화
        CalendarController.initDatabase(this);

        // 초기 캘린더 목록 표시
        if (savedInstanceState == null) {
            showCalendarList(navController);
        }
    }

    // 캘린더 목록을 네비게이션을 통해 표시하는 메서드
    private void showCalendarList(NavController navController) {
        navController.navigate(R.id.calendarListFragment); // calendarListFragment로 이동
    }
}
