# 📅 Calendar 기능 설명

## 📌 개요
사용자가 캘린더를 생성하고, 일정을 등록하거나 수정 및 삭제할 수 있는 UI와 기능을 제공합니다.

## 🧩 주요 클래스 및 역할
- **CalendarActivity.java**: 메인 캘린더 뷰를 담당
- **CalendarFragment.java**: 탭 내에서 일정 리스트를 보여주는 프래그먼트
- **CalendarAddFragment.java / CalendarEditFragment.java**: 일정 추가 및 수정 UI
- **CalendarAdapter.java**: RecyclerView에 표시될 일정 항목 처리
- **CalendarDatabaseHelper.java**: SQLite를 이용한 로컬 데이터 저장
- **CalendarController.java**: Calendar 기능 로직 제어

## 💻 코드 예시
```java
// CalendarAddFragment.java
btnSave.setOnClickListener(v -> {
    String title = editTitle.getText().toString();
    String date = editDate.getText().toString();
    calendarController.addEvent(title, date);
    dismiss();
});
