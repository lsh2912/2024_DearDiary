package com.example.mydiaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mydiaryapp.Calendar.CalendarActivity;
import com.example.mydiaryapp.Day.TodayActivity;
import com.example.mydiaryapp.Event.EventsActivity;
import com.example.mydiaryapp.Search.EventSearchActivity;
import com.example.mydiaryapp.Setting.SettingsActivity;
import com.example.mydiaryapp.View.ViewSelectionActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // 상단 툴바 설정
        Toolbar topToolbar = findViewById(R.id.top_toolbar);
        setSupportActionBar(topToolbar);

        // 네비게이션 컨트롤러 설정
        //NavController navController = Navigation.findNavController(this, R.id.fragment_container_main);
        // NavHostFragment와 NavController 연결
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container_main);
        NavController navController = null;
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        // 하단 툴바 설정
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_main);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_calendar) { // 캘린더 관리 페이지로 이동
                startActivity(new Intent(this, CalendarActivity.class));
                return true;
            } else if (id == R.id.nav_settings) {  // 설정 페이지로 이동
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            } else if (id == R.id.nav_events) { // 이벤트 페이지로 이동
                startActivity(new Intent( this, EventsActivity.class));
                return true;
            } else if (id == R.id.nav_calendar_selection) { // 달력 선택 페이지로 이동
                startActivity(new Intent(this, ViewSelectionActivity.class));
                return true;
            }
            return false;
        });
    }

    // 상단바
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)// 메뉴 아이템 클릭 처리
    {
        int id = item.getItemId();
        if (id == R.id.action_search) { // 검색 페이지로 이동
            startActivity(new Intent(this, EventSearchActivity.class));
            return true;
        } else if (id == R.id.action_today) {  // 오늘의 일정 페이지로 이동
            startActivity(new Intent(this, TodayActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}