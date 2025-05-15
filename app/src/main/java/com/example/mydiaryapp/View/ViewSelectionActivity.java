package com.example.mydiaryapp.View;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mydiaryapp.R;

public class ViewSelectionActivity extends AppCompatActivity {
    // 달력 모드를 전환할 수 있는 버튼 처리 (월간/주간/일간)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
